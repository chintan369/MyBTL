package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddOtherProductForQuotation extends AppCompatActivity {

    TextView txt_productName,txt_productCode,txt_packOfType,txt_productMRP,txt_productSP,txt_productQty;
    EditText edt_productName,edt_productCode,edt_packOfType,edt_productMRP,edt_productSP,edt_productQty;
    Button btn_cancel, btn_addProduct;

    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    DatabaseHandler db;
    String quotationID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other_product_for_quotation);

        Intent intent=getIntent();
        quotationID=intent.getStringExtra("quotationID");

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        txt_productName = (TextView) findViewById(R.id.txt_productName);
        txt_productCode = (TextView) findViewById(R.id.txt_productCode);
        txt_packOfType = (TextView) findViewById(R.id.txt_packOfType);
        txt_productMRP = (TextView) findViewById(R.id.txt_productMRP);
        txt_productSP = (TextView) findViewById(R.id.txt_productSP);
        txt_productQty = (TextView) findViewById(R.id.txt_productQty);

        edt_productName = (EditText) findViewById(R.id.edt_productName);
        edt_productCode = (EditText) findViewById(R.id.edt_productCode);
        edt_packOfType = (EditText) findViewById(R.id.edt_packOfType);
        edt_productMRP = (EditText) findViewById(R.id.edt_productMRP);
        edt_productSP = (EditText) findViewById(R.id.edt_productSP);
        edt_productQty = (EditText) findViewById(R.id.edt_productQty);

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_addProduct = (Button) findViewById(R.id.btn_addProduct);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName=edt_productName.getText().toString().trim();
                String productCode=edt_productCode.getText().toString().trim();
                String packOfType=edt_packOfType.getText().toString().trim();
                String productMRP=edt_productMRP.getText().toString().trim();
                String productSP=edt_productSP.getText().toString().trim();
                String productQty=edt_productQty.getText().toString().trim();

                float productMRPF=0,productSPF=0;

                int productQtyI= Integer.parseInt(productQty.isEmpty() ? "0" : productQty);

                try{
                    if(!productMRP.isEmpty())
                    productMRPF=Float.parseFloat(productMRP);
                }catch (Exception e){
                    productMRPF=0;
                }

                try{
                    if(!productSP.isEmpty())
                        productSPF=Float.parseFloat(productSP);
                }catch (Exception e){
                    productSPF=0;
                }

                if(productName.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Product Name");
                }
                else if(productCode.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Product Code");
                }
                else if(packOfType.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Pack Of Type");
                }
                else if(productMRP.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Product MRP");
                }
                else if(productMRPF<=0){
                    Globals.Toast2(getApplicationContext(),"Product MRP must be greater than 0");
                }
                else if(productSP.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Product Selling Price");
                }
                else if(productSPF<=0){
                    Globals.Toast2(getApplicationContext(),"Product Selling Price must be greater than 0");
                }
                else if(productQty.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Product Quantity");
                }
                else if(productQtyI<=0){
                    Globals.Toast2(getApplicationContext(),"Product Quantity must be more than 0");
                }
                else{

                    setRefershData();
                    String user_id_main="";
                    String role_id="";
                    String ownerID="";
                    if (user_data.size() != 0) {
                        for (int i = 0; i < user_data.size(); i++) {

                            user_id_main = user_data.get(i).getUser_id();
                            role_id = user_data.get(i).getUser_type();
                            ownerID = user_id_main;
                            AppPrefs app;
                            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                app = new AppPrefs(getApplicationContext());
                                role_id = app.getSubSalesId();
                                user_id_main = app.getSalesPersonId();
                            }

                        }

                    } else {
                        Globals.Toast2(getApplicationContext(),"Please Login First");
                        return;
                    }

                    JSONArray arrayData=new JSONArray();
                    JSONObject object=new JSONObject();

                    try {
                        object.put("product_id","");
                        object.put("category_id","0");
                        object.put("pro_code",productCode);
                        object.put("name",productName);
                        object.put("quantity",productQty);
                        object.put("mrp",productMRP);
                        object.put("selling_price",productSP);
                        object.put("pack_of",packOfType);
                        object.put("option_id","0");
                        object.put("option_name","");
                        object.put("option_value_id","0");
                        object.put("option_value_name","");
                        float totalPrice=productSPF*productQtyI;
                        object.put("item_total",String.format(Locale.getDefault(),"%.2f",totalPrice));
                        object.put("pro_scheme"," ");
                        object.put("prod_img","");
                        object.put("user_id",user_id_main);
                        object.put("role_id",role_id);
                        object.put("owner_id",ownerID);
                        object.put("scheme_id","0");
                        object.put("scheme_title","");
                        object.put("scheme_pack_id","");
                        object.put("is_btl_product","1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    arrayData.put(object);

                    new Add_Product(arrayData.toString()).execute();

                }

            }
        });
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no = user_array_from_db.get(i).getPhone_no();
            String f_name = user_array_from_db.get(i).getF_name();
            String l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            String gender = user_array_from_db.get(i).getGender();
            String usertype = user_array_from_db.get(i).getUser_type();
            String login_with = user_array_from_db.get(i).getLogin_with();
            String str_rid = user_array_from_db.get(i).getStr_rid();
            String add1 = user_array_from_db.get(i).getAdd1();
            String add2 = user_array_from_db.get(i).getAdd2();
            String add3 = user_array_from_db.get(i).getAdd3();
            String landmark = user_array_from_db.get(i).getLandmark();
            String pincode = user_array_from_db.get(i).getPincode();
            String state_id = user_array_from_db.get(i).getState_id();
            String state_name = user_array_from_db.get(i).getState_name();
            String city_id = user_array_from_db.get(i).getCity_id();
            String city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(uid);
            contact.setUser_id(user_id);
            contact.setEmail_id(email_id);
            contact.setPhone_no(phone_no);
            contact.setF_name(f_name);
            contact.setL_name(l_name);
            contact.setPassword(password);
            contact.setGender(gender);
            contact.setUser_type(usertype);
            contact.setLogin_with(login_with);
            contact.setStr_rid(str_rid);
            contact.setAdd1(add1);
            contact.setAdd2(add2);
            contact.setAdd3(add3);
            contact.setLandmark(landmark);
            contact.setPincode(pincode);
            contact.setState_id(state_id);
            contact.setState_name(state_name);
            contact.setCity_id(city_id);
            contact.setCity_name(city_name);
            contact.setStr_response(str_response);
            user_data.add(contact);


        }
        db.close();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        img_home.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public class Add_Product extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        String data = "";
        Custom_ProgressDialog loadingView;
        private String result;
        private InputStream is;

        public Add_Product(String data) {
            this.data = data;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        AddOtherProductForQuotation.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {




                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("Quotation_Data", data));
                parameters.add(new BasicNameValuePair("quotation_id", quotationID));


                //Log.e("Cart_Array",""+jarray_cart);
                String json = new ServiceHandler().makeServiceCall(Globals.server_link + Globals.ADD_QUOTATION, ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);
                return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(getApplicationContext(), "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        onBackPressed();
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }
}
