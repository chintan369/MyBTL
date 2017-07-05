package com.agraeta.user.btl;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.area.AreaData;
import com.agraeta.user.btl.model.area.AreaItem;
import com.agraeta.user.btl.utils.FilePath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BecomeSellerActivity extends AppCompatActivity {

    private static final int REQUEST_PAN_DOC = 1;
    private static final int REQUEST_ST_DOC = 2;
    private static final int REQUEST_VAT_DOC = 3;
    WebView webView;

    EditText edt_firstName,edt_lastName,edt_emailID,edt_mobile,edt_firmName,edt_contactPerson,edt_alternateContact,edt_addrLine1,edt_addrLine2,edt_pincode,edt_pan,edt_serviceTax,edt_vat,edt_remarks,edt_reference;
    TextView txt_firstName,txt_lastName,txt_emailID,txt_mobile,txt_firmName,txt_tradeType,txt_contactPerson,txt_alternateContat,txt_addrLine1,txt_addrLine2,txt_state,txt_city,txt_pincode,txt_pan,txt_serviceTax,txt_vat;

    TextView txt_addPanDoc,txt_addServiceTaxDoc,txt_addVatDoc;

    Spinner spn_tradeType, spn_state, spn_city;
    Button btn_submit;

    AdminAPI adminAPI;
    Call<AreaData> stateData;
    Call<AreaData> cityData;

    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> cityAdapter;

    ArrayList<String> stateNames=new ArrayList<>();
    ArrayList<String> cityNames=new ArrayList<>();

    ArrayList<AreaItem> stateList=new ArrayList<>();
    ArrayList<AreaItem> cityList=new ArrayList<>();

    LinearLayout layout_panDoc,layout_serviceTaxDoc,layout_vatDoc;


    Custom_ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_seller);

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        dialog = new Custom_ProgressDialog(this, "");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);

        adminAPI= ServiceGenerator.getAPIServiceClass();

        txt_firstName=(TextView) findViewById(R.id.txt_firstName);
        txt_lastName=(TextView) findViewById(R.id.txt_lastName);
        txt_emailID=(TextView) findViewById(R.id.txt_emailID);
        txt_mobile=(TextView) findViewById(R.id.txt_mobile);
        txt_firmName=(TextView) findViewById(R.id.txt_firmName);
        txt_tradeType=(TextView) findViewById(R.id.txt_tradeType);
        txt_contactPerson=(TextView) findViewById(R.id.txt_contactPerson);
        txt_alternateContat=(TextView) findViewById(R.id.txt_alternateContat);
        txt_addrLine1=(TextView) findViewById(R.id.txt_addrLine1);
        txt_addrLine2=(TextView) findViewById(R.id.txt_addrLine2);
        txt_state=(TextView) findViewById(R.id.txt_state);
        txt_city=(TextView) findViewById(R.id.txt_city);
        txt_pincode=(TextView) findViewById(R.id.txt_pincode);
        txt_pan=(TextView) findViewById(R.id.txt_pan);
        txt_serviceTax=(TextView) findViewById(R.id.txt_serviceTax);
        txt_vat=(TextView) findViewById(R.id.txt_vat);

        txt_addPanDoc=(TextView) findViewById(R.id.txt_addPanDoc);
        txt_addServiceTaxDoc=(TextView) findViewById(R.id.txt_addServiceTaxDoc);
        txt_addVatDoc=(TextView) findViewById(R.id.txt_addVatDoc);

        C.setCompulsoryText(txt_firstName);
        C.setCompulsoryText(txt_lastName);
        C.setCompulsoryText(txt_emailID);
        C.setCompulsoryText(txt_mobile);
        C.setCompulsoryText(txt_firmName);
        C.setCompulsoryText(txt_tradeType);
        C.setCompulsoryText(txt_contactPerson);
        C.setCompulsoryText(txt_addrLine1);
        C.setCompulsoryText(txt_addrLine2);
        C.setCompulsoryText(txt_state);
        C.setCompulsoryText(txt_city);
        C.setCompulsoryText(txt_pincode);

        layout_panDoc=(LinearLayout) findViewById(R.id.layout_panDoc);
        layout_serviceTaxDoc=(LinearLayout) findViewById(R.id.layout_serviceTaxDoc);
        layout_vatDoc=(LinearLayout) findViewById(R.id.layout_vatDoc);

        txt_addPanDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*|application/*");
                startActivityForResult(intent, REQUEST_PAN_DOC);
            }
        });

        txt_addServiceTaxDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*|application/*");
                startActivityForResult(intent, REQUEST_ST_DOC);
            }
        });

        txt_addVatDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*|application/*");
                startActivityForResult(intent, REQUEST_VAT_DOC);
            }
        });

        edt_firstName=(EditText) findViewById(R.id.edt_firstName);
        edt_lastName=(EditText) findViewById(R.id.edt_lastName);
        edt_emailID=(EditText) findViewById(R.id.edt_emailID);
        edt_mobile=(EditText) findViewById(R.id.edt_mobile);
        edt_firmName=(EditText) findViewById(R.id.edt_firmName);
        edt_contactPerson=(EditText) findViewById(R.id.edt_contactPerson);
        edt_alternateContact=(EditText) findViewById(R.id.edt_alternateContact);
        edt_addrLine1=(EditText) findViewById(R.id.edt_addrLine1);
        edt_addrLine2=(EditText) findViewById(R.id.edt_addrLine2);
        edt_pincode=(EditText) findViewById(R.id.edt_pincode);
        edt_pan=(EditText) findViewById(R.id.edt_pan);
        edt_serviceTax=(EditText) findViewById(R.id.edt_serviceTax);
        edt_vat=(EditText) findViewById(R.id.edt_vat);
        edt_remarks=(EditText)findViewById(R.id.edt_remarks);
        edt_reference=(EditText)findViewById(R.id.edt_reference);

        spn_tradeType=(Spinner) findViewById(R.id.spn_tradeType);
        spn_state=(Spinner) findViewById(R.id.spn_state);
        spn_city=(Spinner) findViewById(R.id.spn_city);

        stateAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,stateNames);
        cityAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,cityNames);

        spn_state.setAdapter(stateAdapter);
        spn_city.setAdapter(cityAdapter);

        dialog.show();
        Call<AreaData> getStateCall=adminAPI.getAreasData("1",null);
        getStateCall.enqueue(new Callback<AreaData>() {
            @Override
            public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                dialog.dismiss();

                stateList.clear();
                stateNames.clear();

                stateNames.add("Select State");
                stateList.add(new AreaItem("Select State"));

                AreaData areaData=response.body();
                if(areaData.isStatus()){
                    stateNames.addAll(areaData.getAreaNames());
                    stateList.addAll(areaData.getAreaItemList());
                }
                stateAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<AreaData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });

        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    cityNames.clear();
                    cityList.clear();
                    cityAdapter.notifyDataSetChanged();
                }
                else {
                    dialog.show();
                    loadCityData(stateList.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==cityNames.size()-1){
                    showAddOtherCityDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_submit=(Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isValidated(edt_firstName,"First Name","Please Enter First Name min of 3 letter",3,false)) return;
                if(!isValidated(edt_lastName,"Last Name","Please Enter Last Name min of 3 letter",3,false)) return;
                if(!isValidated(edt_emailID,"Email ID","Please Enter Valid Email ID",0,true)) return;
                if(!isValidated(edt_mobile,"Mobile Number","Please Enter 10 Digit Mobile Number",10,false)) return;
                if(!isValidated(edt_firmName,"Firm Name","Please Enter Firm Name min of 3 letter",3,false)) return;

                if(spn_tradeType.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select Trade Type");
                    return;
                }

                if(!isValidated(edt_contactPerson,"Contact Person Name","Please Enter Contact Person Name min of 3 letter",3,false)) return;
                if(!getEnteredText(edt_alternateContact).isEmpty() && getEnteredText(edt_alternateContact).length()<10){
                    edt_alternateContact.requestFocus();
                    Globals.Toast2(getApplicationContext(),"Please Enter 10 Digit Alternate Contact No.");
                    return;
                }
                if(!isValidated(edt_addrLine1,"Address Line 1","",0,false)) return;
                if(!isValidated(edt_addrLine2,"Address Line 2","",0,false)) return;

                if(spn_state.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select State");
                    return;
                }

                if(spn_city.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select City");
                    return;
                }

                if(!isValidated(edt_pincode,"Pincode","Please Enter 6 Digit Pincode",6,false)) return;
                if(!edt_pan.getText().toString().trim().isEmpty() && !isValidated(edt_pan,"PAN No","Please Enter Valid PAN No",10,false)) return;
                //if(!isValidated(edt_serviceTax,"Service Tax No","",0,false)) return;
                //if(!isValidated(edt_vat,"VAT No","",0,false)) return;

                /*if(layout_panDoc.getChildCount()==0){
                    Globals.Toast2(getApplicationContext(),"Please Add PAN Document");
                    return;
                }

                if(layout_serviceTaxDoc.getChildCount()==0){
                    Globals.Toast2(getApplicationContext(),"Please Add Service Tax Document");
                    return;
                }

                if(layout_vatDoc.getChildCount()==0){
                    Globals.Toast2(getApplicationContext(),"Please Add VAT/TIN Document");
                    return;
                }*/

                dialog.show();

                String firstName=getEnteredText(edt_firstName);
                String lastName=getEnteredText(edt_lastName);
                String emailID=getEnteredText(edt_emailID);
                String mobile=getEnteredText(edt_mobile);
                String firmName=getEnteredText(edt_firmName);
                String contactPerson=getEnteredText(edt_contactPerson);
                String alternateContact=getEnteredText(edt_alternateContact);
                String addressLine1=getEnteredText(edt_addrLine1);
                String addressLine2=getEnteredText(edt_addrLine2);
                String pincode=getEnteredText(edt_pincode);
                String panNo=getEnteredText(edt_pan);
                String serviceTaxNo=getEnteredText(edt_serviceTax);
                String vatNo=getEnteredText(edt_vat);
                String reference=getEnteredText(edt_reference);
                String remark=getEnteredText(edt_remarks);
                String stateID=stateList.get(spn_state.getSelectedItemPosition()).getId();
                String cityID=cityList.get(spn_city.getSelectedItemPosition()).getId();

                String tradeType="";
                if(spn_tradeType.getSelectedItemPosition()==1) tradeType=C.DIRECT_DEALER;
                else if(spn_tradeType.getSelectedItemPosition()==2) tradeType=C.DISTRIBUTOR;
                else if(spn_tradeType.getSelectedItemPosition()==3) tradeType=C.PROFESSIONAL;
                else if(spn_tradeType.getSelectedItemPosition()==4) tradeType=C.CARPENTER;

                List<MultipartBody.Part> files=new ArrayList<MultipartBody.Part>();
                if(layout_panDoc.getChildCount()!=0){
                    String panDocUri=((TextView)layout_panDoc.getChildAt(0).findViewById(R.id.txt_filePath)).getText().toString();
                    files.add(C.prepareFilePart("upload_pan",panDocUri));
                }

                if(layout_serviceTaxDoc.getChildCount()!=0){
                    String STDocUri=((TextView)layout_serviceTaxDoc.getChildAt(0).findViewById(R.id.txt_filePath)).getText().toString();
                    files.add(C.prepareFilePart("upload_service_tax",STDocUri));
                }

                if(layout_vatDoc.getChildCount()!=0){
                    String vatDocUri=((TextView)layout_vatDoc.getChildAt(0).findViewById(R.id.txt_filePath)).getText().toString();
                    files.add(C.prepareFilePart("upload_vat_tin",vatDocUri));
                }


                Call<AppModel> becomeSellerCall = adminAPI.becomeDealerCall(firstName, lastName, emailID, tradeType, mobile, firmName, contactPerson, alternateContact, addressLine1, addressLine2, stateID, cityID, pincode, panNo, serviceTaxNo, vatNo, remark, reference, files);
                becomeSellerCall.enqueue(new Callback<AppModel>() {
                    @Override
                    public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                        dialog.dismiss();
                        AppModel model=response.body();
                        if(model.isStatus()){
                            Globals.Toast2(getApplicationContext(),model.getMessage());
                            onBackPressed();
                        }
                        else {
                            Globals.Toast2(getApplicationContext(),model.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<AppModel> call, Throwable t) {
                        dialog.dismiss();
                        Globals.showError(t,getApplicationContext());
                    }
                });

            }
        });

    }

    private void showAddOtherCityDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.layout_dialog_add_other_city,null);

        builder.setCancelable(false);
        TextView txt_stateName=(TextView) view.findViewById(R.id.txt_stateName);
        final EditText edt_cityName=(EditText) view.findViewById(R.id.edt_cityName);

        txt_stateName.setText(stateNames.get(spn_state.getSelectedItemPosition()));

        builder.setView(view);

        builder.setTitle("Add Other City");

        builder.setPositiveButton("Add",null);
        builder.setNegativeButton("Cancel",null);

        final AlertDialog dialogB=builder.create();

        dialogB.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogI) {
                Button btn_save= dialogB.getButton(DialogInterface.BUTTON_POSITIVE);
                Button btn_cancel= dialogB.getButton(DialogInterface.BUTTON_NEGATIVE);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cityName=edt_cityName.getText().toString().trim();
                        if(cityName.isEmpty()){
                            Globals.Toast2(getApplicationContext(),"Please Enter City Name");
                        }
                        else {

                            dialogB.dismiss();
                            dialog.show();
                            final String stateID=stateList.get(spn_state.getSelectedItemPosition()).getId();
                            Call<AppModel> cityCall=adminAPI.addOtherCity(stateID,cityName);
                            cityCall.enqueue(new Callback<AppModel>() {
                                @Override
                                public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                                    dialog.dismiss();
                                    if(response.body().isStatus()){
                                        Globals.Toast2(getApplicationContext(),response.body().getMessage());
                                        dialog.show();
                                        loadCityData(stateID);
                                    }
                                    else {
                                        Globals.Toast2(getApplicationContext(),response.body().getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(Call<AppModel> call, Throwable t) {
                                    dialog.dismiss();
                                    Globals.showError(t,getApplicationContext());
                                }
                            });
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogB.dismiss();
                        spn_city.setSelection(0);
                    }
                });
            }
        });

        dialogB.show();
    }

    private void loadCityData(String stateID) {

        Call<AreaData> cityCall=adminAPI.getCityData(stateID);
        cityCall.enqueue(new Callback<AreaData>() {
            @Override
            public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                dialog.dismiss();
                cityNames.clear();
                cityList.clear();

                cityNames.add("Select City");
                cityList.add(new AreaItem("Select City"));

                AreaData cityData=response.body();

                if(cityData.isStatus()){
                    cityNames.addAll(cityData.getAreaNames());
                    cityList.addAll(cityData.getAreaItemList());
                }
                else {
                    Globals.Toast2(getApplicationContext(),cityData.getMessage());
                }

                cityNames.add("Other City");
                cityList.add(new AreaItem("Other City"));

                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AreaData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_PAN_DOC && resultCode==RESULT_OK){
            if(data.getData()!=null){
                Uri dataUri=data.getData();

                //Log.e("URI",dataUri.toString());

                addPANAttachment(FilePath.getPath(this,dataUri));
            }
            else {
                Globals.Toast2(this,"Sorry, File not Found!");
            }
        }
        else if(requestCode==REQUEST_ST_DOC && resultCode==RESULT_OK){
            if(data.getData()!=null){
                Uri dataUri=data.getData();

                //Log.e("URI",dataUri.toString());

                addSTAttachment(FilePath.getPath(this,dataUri));
            }
            else {
                Globals.Toast2(this,"Sorry, File not Found!");
            }
        }
        else if(requestCode==REQUEST_VAT_DOC && resultCode==RESULT_OK){
            if(data.getData()!=null){
                Uri dataUri=data.getData();

                //Log.e("URI",dataUri.toString());

                addVATAttachment(FilePath.getPath(this,dataUri));
            }
            else {
                Globals.Toast2(this,"Sorry, File not Found!");
            }
        }
    }

    private void addPANAttachment(String dataUri) {

        layout_panDoc.removeAllViews();
        String fileType=dataUri.substring(dataUri.lastIndexOf(".")+1);

        Uri uri=Uri.parse(dataUri);

        Log.e("DataUri",uri.toString());

        if(!FilePath.getImageExts().contains(fileType) && !FilePath.getDocExts().contains(fileType)){
            Globals.Toast2(this,"Please Select Image, Video or Document File only!");
        }
        else {
            final View view=getLayoutInflater().inflate(R.layout.layout_attachment,null);

            ImageView img_type=(ImageView) view.findViewById(R.id.img_type);
            img_type.setVisibility(View.GONE);
            TextView txt_fileName= (TextView) view.findViewById(R.id.txt_fileName);
            ImageView img_remove=(ImageView) view.findViewById(R.id.img_remove);
            TextView txt_filePath= (TextView) view.findViewById(R.id.txt_filePath);

            File file=new File(dataUri);

            String fileName=file.getName();

            txt_fileName.setText(fileName);
            txt_filePath.setText(dataUri);

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_panDoc.removeView(view);
                }
            });

            layout_panDoc.addView(view);
        }
    }

    private void addSTAttachment(String dataUri) {

        layout_serviceTaxDoc.removeAllViews();
        String fileType=dataUri.substring(dataUri.lastIndexOf(".")+1);

        Uri uri=Uri.parse(dataUri);

        Log.e("DataUri",uri.toString());

        if(!FilePath.getImageExts().contains(fileType) && !FilePath.getDocExts().contains(fileType)){
            Globals.Toast2(this,"Please Select Image, Video or Document File only!");
        }
        else {
            final View view=getLayoutInflater().inflate(R.layout.layout_attachment,null);

            ImageView img_type=(ImageView) view.findViewById(R.id.img_type);
            img_type.setVisibility(View.GONE);
            TextView txt_fileName= (TextView) view.findViewById(R.id.txt_fileName);
            ImageView img_remove=(ImageView) view.findViewById(R.id.img_remove);
            TextView txt_filePath= (TextView) view.findViewById(R.id.txt_filePath);

            File file=new File(dataUri);

            String fileName=file.getName();

            txt_fileName.setText(fileName);
            txt_filePath.setText(dataUri);

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_serviceTaxDoc.removeView(view);
                }
            });

            layout_serviceTaxDoc.addView(view);
        }
    }

    private void addVATAttachment(String dataUri) {

        layout_vatDoc.removeAllViews();
        String fileType=dataUri.substring(dataUri.lastIndexOf(".")+1);

        Uri uri=Uri.parse(dataUri);

        Log.e("DataUri",uri.toString());

        if(!FilePath.getImageExts().contains(fileType) && !FilePath.getDocExts().contains(fileType)){
            Globals.Toast2(this,"Please Select Image, Video or Document File only!");
        }
        else {
            final View view=getLayoutInflater().inflate(R.layout.layout_attachment,null);

            ImageView img_type=(ImageView) view.findViewById(R.id.img_type);
            img_type.setVisibility(View.GONE);
            TextView txt_fileName= (TextView) view.findViewById(R.id.txt_fileName);
            ImageView img_remove=(ImageView) view.findViewById(R.id.img_remove);
            TextView txt_filePath= (TextView) view.findViewById(R.id.txt_filePath);

            File file=new File(dataUri);

            String fileName=file.getName();

            txt_fileName.setText(fileName);
            txt_filePath.setText(dataUri);

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_vatDoc.removeView(view);
                }
            });

            layout_vatDoc.addView(view);
        }
    }

    private String getEnteredText(Object object){
        String text="";

        if(object instanceof EditText){
            text=((EditText) object).getText().toString().trim();
        }
        else if(object instanceof TextView){
            text=((TextView) object).getText().toString().trim();
        }

        return text;
    }

    private boolean isValidated(EditText editText, String emptyMessage, String errorMessage, int minLenth, @Nullable boolean isEmail){
        String text=editText.getText().toString().trim();

        if(text.isEmpty()){
            editText.requestFocus();
            Globals.Toast2(this,"Please Enter "+emptyMessage);
            return false;
        }

        if(minLenth>0 && text.length()<minLenth){
            editText.requestFocus();
            Globals.Toast2(this,errorMessage);
            return false;
        }

        if(isEmail && !C.validEmail(text)){
            editText.requestFocus();
            Globals.Toast2(this,errorMessage);
            return false;
        }

        return true;
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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);

        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.GONE);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);

        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
