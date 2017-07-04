package com.agraeta.user.btl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.utils.FilePath;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NonMRPProductEnquiry extends AppCompatActivity implements Callback<AppModel> {

    EditText edt_firstName,edt_lastName,edt_emailAddress,edt_mobile,edt_query;
    Button btn_submit;
    LinearLayout layout_attachment;
    TextView txt_add_attachment;

    String productName="";
    String productID="";

    int FILE_PICK_CODE=1;

    Custom_ProgressDialog dialog;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_mrpproduct_enquiry);

        prefs = new AppPrefs(this);

        Intent intent=getIntent();
        productName=intent.getStringExtra("productName");
        productID=intent.getStringExtra("productID");

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {

        final AdminAPI adminAPI= ServiceGenerator.getAPIServiceClass();

        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);

        edt_firstName=(EditText) findViewById(R.id.edt_firstName); // Full Name
        edt_lastName=(EditText) findViewById(R.id.edt_lastName); // Firm Name
        edt_emailAddress=(EditText) findViewById(R.id.edt_emailAddress);
        edt_mobile=(EditText) findViewById(R.id.edt_mobile);
        edt_query=(EditText) findViewById(R.id.edt_query);
        btn_submit=(Button) findViewById(R.id.btn_submit);
        layout_attachment= (LinearLayout) findViewById(R.id.layout_attachment);
        txt_add_attachment= (TextView) findViewById(R.id.txt_add_attachment);

        if (prefs.getUser_LoginInfo().equals("1")) {
            edt_firstName.setText(BTL.user.getName());
            edt_lastName.setText(BTL.distributor.getFirm_name());
            edt_emailAddress.setText(BTL.user.getEmail_id());
            edt_mobile.setText(BTL.user.getPhone_no());
        }

        txt_add_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int totalAttachment=layout_attachment.getChildCount();

                if(totalAttachment<5){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*|application/*|text/*|video/*");
                    startActivityForResult(intent, FILE_PICK_CODE);
                }
                else {
                    Globals.Toast2(getApplicationContext(),"You can attach max 5 attachment only!");
                }


            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName=edt_firstName.getText().toString().trim();
                String lastName=edt_lastName.getText().toString().trim();
                String emailID=edt_emailAddress.getText().toString().trim();
                String mobile=edt_mobile.getText().toString().trim();
                String query=edt_query.getText().toString().trim();

                if(firstName.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Full Name");
                }
                else if(lastName.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Firm Name");
                }
                else if(emailID.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Email ID");
                }
                else if(!C.validEmail(emailID)){
                    Globals.Toast2(getApplicationContext(),"Please Enter Valid Email ID");
                } else if (!mobile.isEmpty() || mobile.length() < 10) {
                    Globals.Toast2(getApplicationContext(),"Please Enter Valid Mobile Number");
                }
                else if(query.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Write Comment");
                }
                else {
                    dialog.show();
                    int totalAttachment=layout_attachment.getChildCount();
                    Call<AppModel> productEnquiryCall;
                    if(totalAttachment==0){
                        productEnquiryCall=adminAPI.productEnquiryCall(firstName,lastName,emailID,mobile,productName,productID,query,null);
                    }
                    else {

                        List<MultipartBody.Part> files=new ArrayList<MultipartBody.Part>();

                        int imageCount=1;
                        int videoCount=1;
                        int docCount=1;

                        for(int i=0; i<totalAttachment; i++){
                            View view=layout_attachment.getChildAt(i);
                            TextView txt_filePath=(TextView) view.findViewById(R.id.txt_filePath);
                            String filePath=txt_filePath.getText().toString();

                            String fileType=filePath.substring(filePath.lastIndexOf(".")+1);
                            String keyName="";

                            if(FilePath.getImageExts().contains(fileType)){
                                keyName="image_"+imageCount;
                                imageCount++;
                            }
                            else if(FilePath.getVideoExts().contains(fileType)){
                                keyName="video_"+videoCount;
                                videoCount++;
                            }
                            else if(FilePath.getDocExts().contains(fileType)){
                                keyName="doc_"+docCount;
                                docCount++;
                            }

                            files.add(prepareFilePart(keyName,filePath));
                        }

                        productEnquiryCall=adminAPI.productEnquiryCall(firstName,lastName,emailID,mobile,productName,productID,query,files);
                    }


                    productEnquiryCall.enqueue(NonMRPProductEnquiry.this);
                }
            }
        });
    }

    @Override
    public void onResponse(Call<AppModel> call, Response<AppModel> response) {
        dialog.dismiss();

        AppModel model=response.body();

        if(model.isStatus()){
            Globals.Toast2(this,model.getMessage());
            onBackPressed();
        }
        else {
            Globals.Toast2(this,model.getMessage());
        }
    }

    @Override
    public void onFailure(Call<AppModel> call, Throwable t) {
        dialog.dismiss();
        Globals.showError(t,this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILE_PICK_CODE && resultCode==RESULT_OK){
            if(data.getData()!=null){
                Uri dataUri=data.getData();

                Log.e("URI",dataUri.toString());

                addNewAttachment(FilePath.getPath(this,dataUri));
            }
            else {
                Globals.Toast2(this,"Sorry, File not Found!");
            }
        }
    }

    private void addNewAttachment(String dataUri) {

        String fileType=dataUri.substring(dataUri.lastIndexOf(".")+1);

        Uri uri=Uri.parse(dataUri);

        Log.e("DataUri",uri.toString());

        if(!FilePath.getImageExts().contains(fileType) && !FilePath.getVideoExts().contains(fileType) && !FilePath.getDocExts().contains(fileType)){
            Globals.Toast2(this,"Please Select Image, Video or Document File only!");
        }
        else {
            final View view=getLayoutInflater().inflate(R.layout.layout_attachment,null);

            ImageView img_type=(ImageView) view.findViewById(R.id.img_type);
            TextView txt_fileName= (TextView) view.findViewById(R.id.txt_fileName);
            ImageView img_remove=(ImageView) view.findViewById(R.id.img_remove);
            TextView txt_filePath= (TextView) view.findViewById(R.id.txt_filePath);

            File file=new File(dataUri);

            String fileName=file.getName();

            if(FilePath.getImageExts().contains(fileType)){
                img_type.setImageResource(R.drawable.icon_type_image);
            }
            else if(FilePath.getVideoExts().contains(fileType)){
                img_type.setImageResource(R.drawable.icon_type_video);
            }
            else if(FilePath.getDocExts().contains(fileType)){
                img_type.setImageResource(R.drawable.icon_type_doc);
            }

            txt_fileName.setText(fileName);
            txt_filePath.setText(dataUri);

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_attachment.removeView(view);
                }
            });

            layout_attachment.addView(view);
        }
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        //Log.e("mimeType",mimeType);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        //MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);

        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        //txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        img_category.setVisibility(View.GONE);
        //String qun = app.getCart_QTy();

        //setRefershData();


        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.LogoutFilter();
                db.Delete_ALL_table();
                db.close();
                AppPrefs app = new AppPrefs(getApplicationContext());
                app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");
                Intent i = new Intent(getApplicationContext(), MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}
