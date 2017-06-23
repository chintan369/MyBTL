package com.agraeta.user.btl.CompanySalesPerson;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalesSkipOrderActivity extends AppCompatActivity implements Serializable {
    Spinner spn_reason;
    EditText edt_other_reason, edt_comment, edt_attach1, edt_attach2;
    Button btn_save, btn_cancel,btn_photo,btn_photo2;
    TextView txt_photo,txt_photo2;
    Bean_Company_Sales_User bean_company_sales_user;

    Toolbar toolbar;
    ImageView img_drawer;

    AppPrefs apps;
    DatabaseHandler db;

    public static final int SELECT_PICTURE=1;
    public static final int SELECT_PICTURE_KITKAT=2;
    String filePath="";

    GPSTracker gps;

    ArrayList<String> reasonList=new ArrayList<>();
    ArrayAdapter<String> reasonAdp;

    int selectedImageFor=0;
    String json;

    double lat, longt;

    String userSalesId;

    boolean isNotDone=true;
    String jsonData="";

    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_skip_order);

        apps=new AppPrefs(getApplicationContext());
        gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            lat= gps.getLatitude(); // returns latitude
            longt= gps.getLongitude(); // returns longitude
        }
        else {
            gps.showSettingsAlert();
        }

       Intent intent=getIntent();
       userSalesId=intent.getStringExtra("salesComapnyPerson");
        //Log.e("userid in skip order",""+userSalesId);
        //userSalesId=bean_company_sales_user.getUserSalesId();

        isNotDone=true;
        Globals.generateNoteOnSD(getApplicationContext(),isNotDone+"---");
        fetchID();
        new GetReasonList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //isNotDone=true;
        Globals.generateNoteOnSD(getApplicationContext(),isNotDone+"---"+jsonData);

        json=jsonData;


        setActionBar();
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
            img_cart.setVisibility(View.GONE);
            img_home.setVisibility(View.GONE);
            img_category.setVisibility(View.GONE);
            img_notification.setVisibility(View.GONE);

            ImageView img_add=(ImageView)mCustomView.findViewById(R.id.img_add);
            img_add.setVisibility(View.GONE);
            img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),DisSalesFormActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            ImageView img_logout=(ImageView)mCustomView.findViewById(R.id.image_logout);
            img_logout.setVisibility(View.VISIBLE);
            img_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),MainPage_drawer.class);
//                startActivity(intent);
//                finish();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            SalesSkipOrderActivity.this);
                    // Setting Dialog Title
                    alertDialog.setTitle("Logout application?");
                    // Setting Dialog Message
                    alertDialog
                            .setMessage("Are you sure you want to logout?");

                    // Setting Icon to Dialog
                    // .setIcon(R.drawable.ic_launcher);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    apps = new AppPrefs(SalesSkipOrderActivity.this);
                                    if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                                        apps.setUser_LoginInfo("");
                                        apps.setUser_PersonalInfo("");
                                        apps.setUser_SocialIdInfo("");
                                        apps.setUser_SocialFirst("");
                                        apps.setUser_Sociallast("");
                                        apps.setUser_Socialemail("");
                                        apps.setUser_SocialReInfo("");

                                        apps.setCsalesId("");
                                        apps.setSubSalesId("");



                                        db = new DatabaseHandler(SalesSkipOrderActivity.this);
                                        db.Delete_user_table();
                                        db.Clear_ALL_table();
                                        db.close();
                                        Intent i = new Intent(SalesSkipOrderActivity.this, MainPage_drawer.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        finish();

                                    }
                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // Write your code here to invoke NO event
                                    dialog.cancel();
                                }
                            });
                    // Showing Alert Message
                    alertDialog.show();
                }
            });

            FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
            frame.setVisibility(View.GONE);
            TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
//        db = new DatabaseHandler(Product_List.this);
//        bean_cart =  db.Get_Product_Cart();
//        db.close();
//        if(bean_cart.size() == 0){
//            txt.setVisibility(View.GONE);
//            txt.setText("");
//        }else{
//            txt.setVisibility(View.VISIBLE);
//            txt.setText(bean_cart.size()+"");
//        }

            image_drawer.setImageResource(R.drawable.ic_action_btl_back);

            image_drawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Intent intent=new Intent(getApplicationContext(),UserTypeActivity.class);
                    startActivity(intent);
                    finish();*/
                    onBackPressed();

                }
            });
//        img_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                Intent i = new Intent(Product_List.this, Main_CategoryPage.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        img_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                Intent i = new Intent(Product_List.this, MainPage_drawer.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        img_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, Notification.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        img_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, BTL_Cart.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

    private void fetchID() {
        spn_reason=(Spinner) findViewById(R.id.spn_reason);
        edt_other_reason=(EditText) findViewById(R.id.edt_other_reason);

        edt_comment=(EditText) findViewById(R.id.edt_comment);
       // edt_attach1=(EditText) findViewById(R.id.edt_attachment1);
        //edt_attach2=(EditText) findViewById(R.id.edt_attachment2);
        btn_photo = (Button) findViewById(R.id.btn_upload_photo);
        txt_photo = (TextView)findViewById(R.id.txt_photo);

        btn_photo2 = (Button) findViewById(R.id.btn_upload_photo2);
        txt_photo2 = (TextView)findViewById(R.id.txt_photo2);


        spn_reason.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_comment.getWindowToken(), 0);
                return false;
            }
        }) ;

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dialog.show();

//                Intent intent1 = new Intent(getApplicationContext(), FileChooser.class);
//                startActivityForResult(intent1,SELECT_PICTURE);
                selectedImageFor=1;

                if (Build.VERSION.SDK_INT <19){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_PICTURE_KITKAT);
                }
            }
        });

        btn_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dialog.show();

                selectedImageFor=2;

                if (Build.VERSION.SDK_INT <19){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_PICTURE_KITKAT);
                }
            }
        });


        btn_save=(Button) findViewById(R.id.btn_send);
        btn_cancel=(Button) findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reasonAdp=new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_status,reasonList);
        spn_reason.setAdapter(reasonAdp);

        spn_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spn_reason.getSelectedItemPosition()==(reasonList.size()-1)){
                    edt_other_reason.setVisibility(View.VISIBLE);
                }
                else {
                    edt_other_reason.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else {

                    String comment = edt_comment.getText().toString();
                    String docPath1 = txt_photo.getText().toString();
                    String docPath2 = txt_photo2.getText().toString();

                    int maxSize=6144;

                    File file1 = new File(docPath1);
                    long length1 = file1.length() / 1024;

                    File file2 = new File(docPath2);
                    long length2 = file2.length() / 1024;

                    if (spn_reason.getSelectedItemPosition() == 0) {
                        Globals.CustomToast(getApplicationContext(), "Please Select Reason", getLayoutInflater());
                        spn_reason.requestFocus();
                    } else if (spn_reason.getSelectedItemPosition() == (reasonList.size() - 1) && edt_other_reason.getText().toString().equalsIgnoreCase("")) {
                        Globals.CustomToast(getApplicationContext(), "Please Specify Other Reason", getLayoutInflater());
                        edt_other_reason.requestFocus();
                    } else if (!docPath1.equalsIgnoreCase("") && !validateImage(docPath1)) {
                        Globals.CustomToast(getApplicationContext(), "Please Select Proper Image in Attachment 1", getLayoutInflater());
                    } else if (!docPath2.equalsIgnoreCase("") && !validateImage(docPath2)) {
                        Globals.CustomToast(getApplicationContext(), "Please Select Proper Image in Attachment 2", getLayoutInflater());
                    } else if (!docPath1.equalsIgnoreCase("") && (length1 == 0 || length1 > maxSize)) {
                        //Log.e("docpath1 size", length1 + "");
                        Globals.CustomToast(getApplicationContext(), "Please Select Max 6 MB Image in Attachment 1", getLayoutInflater());
                    } else if (!docPath2.equalsIgnoreCase("") && (length2 == 0 || length2 > maxSize)) {
                        //Log.e("docpath2 size", length2 + "");
                        Globals.CustomToast(getApplicationContext(), "Please Select Max 6 MB Image in Attachment 2", getLayoutInflater());
                    } else {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                        params.add(new BasicNameValuePair("user_id", apps.getCsalesId()));
                        params.add(new BasicNameValuePair("skip_person_id", userSalesId));
                        params.add(new BasicNameValuePair("reason_title", spn_reason.getSelectedItem().toString()));

                        if (!edt_other_reason.getText().toString().trim().equalsIgnoreCase(""))
                            params.add(new BasicNameValuePair("other_reason_title", edt_other_reason.getText().toString()));
                        params.add(new BasicNameValuePair("latitude", String.valueOf(lat)));
                        params.add(new BasicNameValuePair("longitude", String.valueOf(longt)));

                        if (!docPath1.equalsIgnoreCase(""))
                            params.add(new BasicNameValuePair("attachment_1", docPath1));
                        if (!docPath2.equalsIgnoreCase(""))
                            params.add(new BasicNameValuePair("attachment_2", docPath2));
                        if (!comment.equalsIgnoreCase(""))
                            params.add(new BasicNameValuePair("comment", comment));

                        new SendReason(params).execute();
                    }

                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(data.getData()!=null){
                Uri originalUri = null;
                if (requestCode == SELECT_PICTURE) {
                    originalUri = data.getData();
                } else if (requestCode == SELECT_PICTURE_KITKAT) {
                    originalUri = data.getData();
                    getContentResolver().takePersistableUriPermission(originalUri,(Intent.FLAG_GRANT_READ_URI_PERMISSION| Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                }

                if(selectedImageFor==1)
                    txt_photo.setText(ImagePath.getPath(getApplicationContext(),originalUri));
                else if(selectedImageFor==2)
                    txt_photo2.setText(ImagePath.getPath(getApplicationContext(),originalUri));
            }
        }
    }

    private class GetReasonList extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {

            jsonData="";

            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link +"OrderSkipReason/App_GetOrderSkipReason", ServiceHandler.POST);

            Globals.generateNoteOnSD(getApplicationContext(),"json--"+jsonData);

            //isNotDone=false;

            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            isNotDone=false;

            Globals.generateNoteOnSD(getApplicationContext(),isNotDone+"--"+s);

            jsonData=s;

            updateData(jsonData);
        }
    }

    public static String Get_ReasonList(){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        final List<NameValuePair> params=new ArrayList<>();

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link +"OrderSkipReason/App_GetOrderSkipReason", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                   // System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        return json[0];
    }

    private void updateData(String json) {

        reasonList.clear();
        reasonList.add("Select Reason");

        try {
            JSONObject object=new JSONObject(json);

            if(object.getBoolean("status")){
                JSONArray data=object.getJSONArray("data");
                for(int i=0; i<data.length(); i++){
                    JSONObject subObj=data.getJSONObject(i);
                    reasonList.add(subObj.getString("title"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        reasonList.add("Other Reason");
        reasonAdp.notifyDataSetChanged();

    }

    private static final String IMAGE_PATTERN =
            "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)";

    public static boolean validateImage(final String image){
        Pattern pattern=Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

    public class SendReason extends AsyncTask<Void, Void, String> {

        Custom_ProgressDialog dialog;

        List<NameValuePair> parameters;

        public SendReason(List<NameValuePair> parameters){
            dialog=new Custom_ProgressDialog(SalesSkipOrderActivity.this,"");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();

            this.parameters=parameters;
        }

        @Override
        protected String doInBackground(Void... params) {




            String json=new ServiceHandler().makeServiceCall(Globals.server_link + "OrderSkipReason/App_AddOrderReason", ServiceHandler.POST, parameters,2);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            //Log.e("skiporder json",json);

            try {
                JSONObject object=new JSONObject(json);

                if(object.getBoolean("status")){
                    Globals.CustomToast(getApplicationContext(), object.getString("message"), getLayoutInflater());
                    onBackPressed();
                }
                else {
                    Globals.CustomToast(getApplicationContext(), object.getString("message"), getLayoutInflater());
                }

                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
