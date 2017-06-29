package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.adapters.ReOrderGridAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReOrderActivity extends AppCompatActivity implements Callback<AppModel> {

    ListView gridReOrder;
    Button btn_reOrder;
    ReOrderGridAdapter reOrderGridAdapter;
    List<Bean_Product> productList = new ArrayList<>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    String productIDs = "";

    ArrayList<String> productIDArray = new ArrayList<>();
    ArrayList<String> productQtyArray = new ArrayList<>();
    ArrayList<String> productOptionIDArray = new ArrayList<>();
    ArrayList<String> productOptionNameArray = new ArrayList<>();
    ArrayList<String> productOptionValueIDArray = new ArrayList<>();
    ArrayList<String> productOptionValueNameArray = new ArrayList<>();

    AppPrefs prefs;

    Call<AppModel> reOrderDataCall;
    Custom_ProgressDialog dialog;
    private String user_id_main = "";
    private String role_id = "";
    private String ownerID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_order);

        prefs = new AppPrefs(getApplicationContext());
        dialog = new Custom_ProgressDialog(this, "");
        dialog.setCancelable(false);

        setRefershData();
        setActionBar();
        getUserData();

        Intent intent = getIntent();
        productIDArray = intent.getStringArrayListExtra("productIDs");
        productQtyArray = intent.getStringArrayListExtra("productQtys");
        productOptionIDArray = intent.getStringArrayListExtra("productOptionIDs");
        productOptionNameArray = intent.getStringArrayListExtra("productOptionNames");
        productOptionValueIDArray = intent.getStringArrayListExtra("productOptionValueIDs");
        productOptionValueNameArray = intent.getStringArrayListExtra("productOptionValueNames");

        generateProductArray();

        fetchIDs();
    }

    private void generateProductArray() {
        JSONArray productArray = new JSONArray();
        for (int i = 0; i < productIDArray.size(); i++) {
            productArray.put(productIDArray.get(i));
        }
        productIDs = productArray.toString();
    }

    private void fetchIDs() {
        gridReOrder = (ListView) findViewById(R.id.gridReOrder);
        btn_reOrder = (Button) findViewById(R.id.btn_reOrder);
        reOrderGridAdapter = new ReOrderGridAdapter(this, productList);
        gridReOrder.setAdapter(reOrderGridAdapter);

        final AdminAPI adminAPI = ServiceGenerator.getAPIServiceClass();

        new GetProductDetails(productIDs).execute();

        btn_reOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int breakPoint = reOrderGridAdapter.brokenPoint();

                if (breakPoint == -1) {
                    //TODO WORK FOR REORDER
                    reOrderDataCall = adminAPI.reOrderDataCall(user_id_main, role_id, ownerID, reOrderGridAdapter.getProductArray());
                    Log.e("Data", reOrderDataCall.toString());
                    Log.e("Array", user_id_main + " " + role_id + " " + ownerID + reOrderGridAdapter.getProductArray());
                    reOrderDataCall.enqueue(ReOrderActivity.this);
                    dialog.show();
                } else {
                    gridReOrder.setSelection(breakPoint);
                    reOrderGridAdapter.setBreakPosition(breakPoint);
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(gridReOrder.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
    }

    private void getUserData() {
        if (user_data.size() > 0) {
            //noinspection LoopStatementThatDoesntLoop
            for(int i=0; i<user_data.size(); i++){
                user_id_main=user_data.get(i).getUser_id();
                role_id=user_data.get(i).getUser_type();
                ownerID=user_id_main;
                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                    role_id = prefs.getSubSalesId();
                    user_id_main = prefs.getSalesPersonId();
                }
                break;
            }
        } else {
            user_id_main = prefs.getUserId();
            ownerID = prefs.getUserId();
            role_id = prefs.getUserRoleId();
            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                role_id = prefs.getSubSalesId();
                user_id_main = prefs.getSalesPersonId();
            }
        }
    }

    @Override
    public void onResponse(Call<AppModel> call, Response<AppModel> response) {
        dialog.dismiss();

        AppModel model = response.body();
        if (model.isStatus()) {
            Globals.Toast2(this, model.getMessage());
            onBackPressed();
        } else {
            Globals.Toast2(this, model.getMessage());
        }
    }

    @Override
    public void onFailure(Call<AppModel> call, Throwable t) {
        dialog.dismiss();
        Globals.showError(t, this);
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
        FrameLayout unread = (FrameLayout) mCustomView.findViewById(R.id.unread);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        img_home.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        unread.setVisibility(View.GONE);

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

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        DatabaseHandler db = new DatabaseHandler(this);

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

    private class GetProductDetails extends AsyncTask<Void, Void, String> {

        Custom_ProgressDialog dialog;
        String productIDs = "";

        GetProductDetails(String productIDs) {
            this.productIDs = productIDs;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Custom_ProgressDialog(ReOrderActivity.this, "Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairList = new ArrayList<>();
            pairList.add(new BasicNameValuePair("user_id", user_id_main));
            pairList.add(new BasicNameValuePair("role_id", role_id));
            pairList.add(new BasicNameValuePair("product_ids", productIDs));

            Log.e("Params", pairList.toString());

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + Globals.REORDER_PRODUCTS, ServiceHandler.POST, pairList);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            productList.clear();
            reOrderGridAdapter.notifyDataSetChanged();

            int nonMRPProductCount=0;
            int totalProductCount=0;

            try {

                JSONObject object = new JSONObject(s);

                if (object.getBoolean("status")) {
                    JSONArray data = object.getJSONArray("data");

                    totalProductCount=data.length();

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject main = data.getJSONObject(i);

                        JSONObject Product = main.getJSONObject("Product");
                        JSONObject Label = main.getJSONObject("Label");
                        JSONArray Scheme = main.getJSONArray("Scheme");

                        float mrpPrice=Float.parseFloat(Product.getString("mrp"));

                        if(mrpPrice>0) {


                            Bean_Product product = new Bean_Product();
                            product.setPro_id(Product.getString("id"));
                            product.setPro_cat_id(Product.getString("category_id"));
                            product.setPro_code(Product.getString("product_code"));
                            product.setPro_name(Product.getString("product_name"));
                            product.setPro_mrp(Product.getString("mrp"));


                            product.setPro_sellingprice(Product.getString("selling_price"));
                            product.setPackQty(Integer.parseInt(Product.getString("pack_of_qty")));
                            product.setPro_image(Product.getString("image"));

                            int productQty = 0;
                            for (int k = 0; k < productIDArray.size(); k++) {
                                if (productIDArray.get(k).equals(product.getPro_id())) {
                                    productQty += Integer.parseInt(productQtyArray.get(k));
                                    product.setOptionID(productOptionIDArray.get(k));
                                    product.setOptionName(productOptionNameArray.get(k));
                                    product.setOptionValueID(productOptionValueIDArray.get(k));
                                    product.setOptionValueName(productOptionValueNameArray.get(k));
                                }
                            }

                            product.setPro_qty(String.valueOf(productQty));

                            product.setPro_label(Label.getString("name"));

                            ArrayList<Bean_schemeData> schemeList = new ArrayList<>();

                            for (int j = 0; j < Scheme.length(); j++) {

                                JSONObject schemeObj = Scheme.getJSONObject(j);

                                Bean_schemeData scheme = new Bean_schemeData();
                                scheme.setSchme_id(schemeObj.getString("id"));
                                scheme.setSchme_name(schemeObj.getString("scheme_name"));
                                scheme.setCategory_id(schemeObj.getString("category_id"));
                                scheme.setSchme_buy_prod_id(schemeObj.getString("buy_prod_id"));
                                scheme.setSchme_qty(schemeObj.getString("buy_prod_qty"));

                                schemeList.add(scheme);
                            }

                            product.setSchemeList(schemeList);

                            productList.add(product);

                        }
                        else {
                            nonMRPProductCount++;
                        }

                    }
                } else {
                    Globals.CustomToast(getApplicationContext(), object.getString("message"), getLayoutInflater());
                }

            } catch (JSONException j) {
                Log.e("Exception", j.getMessage());
            }

            reOrderGridAdapter.notifyDataSetChanged();

            if(totalProductCount>0 && nonMRPProductCount>0){
                String message="";
                if(totalProductCount<=1){
                    message = "Sorry, It might Product is not available at BTL Store!";
                }
                else {
                    message = "Few products are not available at BTL Store!";
                }

                Globals.Toast2(getApplicationContext(),message);

                if(totalProductCount<=1 || totalProductCount==nonMRPProductCount){
                    onBackPressed();
                }
            }
        }
    }
}
