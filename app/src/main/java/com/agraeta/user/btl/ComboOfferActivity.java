package com.agraeta.user.btl;

import android.content.Intent;
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
import android.widget.TextView;

import com.agraeta.user.btl.adapters.ComboProductListAdapter;
import com.agraeta.user.btl.adapters.ProductChangeListener;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.combooffer.ComboCartEdit;
import com.agraeta.user.btl.model.combooffer.ComboCartID;
import com.agraeta.user.btl.model.combooffer.ComboOfferDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboOfferActivity extends AppCompatActivity implements Callback<ComboOfferDetail>, ProductChangeListener {

    ListView list_comboOffer;
    TextView txt_totalQty, txt_requiredQty;
    Button btn_addToCart;
    AdminAPI adminAPI;
    Custom_ProgressDialog progressDialog;

    List<ComboOfferDetail.ComboProduct> comboProductList = new ArrayList<>();
    ComboProductListAdapter productListAdapter;

    String comboID = "";
    String roleID = "";
    String userID = "";
    String ownerID = "";

    boolean isEditMode = false;
    boolean isProductEdited = false;
    String comboCartID = "";

    ComboOfferDetail offerDetail;

    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_offer);

        prefs = new AppPrefs(this);

        Intent intent = getIntent();
        comboID = intent.getStringExtra("combo_id");
        isEditMode = intent.getBooleanExtra("editMode", false);
        comboCartID = intent.getStringExtra("combo_cart_id");

        progressDialog = new Custom_ProgressDialog(this, "Please Wait...");
        progressDialog.setCancelable(false);
        adminAPI = ServiceGenerator.getAPIServiceClass();

        userID = prefs.getUserId();
        roleID = prefs.getUserRoleId();
        ownerID = prefs.getUserId();

        if (roleID.equals(C.ADMIN) || roleID.equals(C.COMP_SALES_PERSON) || roleID.equals(C.DISTRIBUTOR_SALES_PERSON)) {
            roleID = prefs.getSubSalesId();
            userID = prefs.getSalesPersonId();
        }

        setActionBar();
        if (!isEditMode) {
            progressDialog.show();
            Call<ComboCartID> comboCartIDCall = adminAPI.getComboCartIdCall(comboID, userID, ownerID);
            comboCartIDCall.enqueue(new Callback<ComboCartID>() {
                @Override
                public void onResponse(Call<ComboCartID> call, Response<ComboCartID> response) {
                    progressDialog.dismiss();
                    ComboCartID comboCartIDObj = response.body();
                    if (comboCartIDObj != null && comboCartIDObj.isStatus()) {
                        isEditMode = true;
                        comboCartID = comboCartIDObj.getData();
                    }

                    fetchIDs();
                }

                @Override
                public void onFailure(Call<ComboCartID> call, Throwable t) {
                    progressDialog.dismiss();
                    fetchIDs();
                }
            });
        } else {
            fetchIDs();
        }
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

    private void fetchIDs() {
        list_comboOffer = (ListView) findViewById(R.id.list_comboOffer);
        txt_totalQty = (TextView) findViewById(R.id.txt_totalQty);
        txt_requiredQty = (TextView) findViewById(R.id.txt_requiredQty);
        btn_addToCart = (Button) findViewById(R.id.btn_addToCart);

        productListAdapter = new ComboProductListAdapter(comboProductList, this, this);
        list_comboOffer.setAdapter(productListAdapter);

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedProducts = productListAdapter.getCheckedProductCount();

                if (selectedProducts <= 0) {
                    Globals.Toast2(getApplicationContext(), "Please Select At least 1 Product");
                    return;
                }

                int totalQty = productListAdapter.getTotalQuantity();
                int requiredMinQty = Integer.parseInt(offerDetail.getComboData().getOfferItem().getMinQty());

                if (totalQty < requiredMinQty) {
                    Globals.Toast2(getApplicationContext(), "Total Quantity should be Min. " + requiredMinQty);
                    return;
                }

                int breakPoint = productListAdapter.getBreakPoint();
                if (breakPoint != -1) {
                    productListAdapter.setBreakPoint(breakPoint);
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(list_comboOffer.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                    return;
                }

                List<ComboOfferDetail.ComboProduct> selectedComboProductList = productListAdapter.getCheckedProducts();

                float totalMRP = 0, totalSellingPrice = 0;

                final JSONArray productArray = new JSONArray();

                for (int i = 0; i < selectedComboProductList.size(); i++) {
                    totalMRP += (Float.parseFloat(selectedComboProductList.get(i).getProduct().getMrpPrice())) * selectedComboProductList.get(i).getProduct().getQuantity();
                    totalSellingPrice += (Float.parseFloat(selectedComboProductList.get(i).getProduct().getSellingPrice())) * selectedComboProductList.get(i).getProduct().getQuantity();

                    Log.e("SPMRP", selectedComboProductList.get(i).getProduct().getMrpPrice() + " --> " + selectedComboProductList.get(i).getProduct().getSellingPrice());

                    //Log.e("SPMRP",totalMRP+" -> "+totalSellingPrice);

                    JSONObject object = new JSONObject();
                    try {
                        object.put("pro_id", selectedComboProductList.get(i).getProduct().getProductID());
                        object.put("pro_qty", String.valueOf(selectedComboProductList.get(i).getProduct().getQuantity()));
                        object.put("pro_cat_id",selectedComboProductList.get(i).getProduct().getCategoryID());
                        object.put("pro_code",selectedComboProductList.get(i).getProduct().getProductCode());
                        object.put("pro_name",selectedComboProductList.get(i).getProduct().getProductName());
                        object.put("pro_mrp",selectedComboProductList.get(i).getProduct().getMrpPrice());
                        object.put("pro_sellingprice",selectedComboProductList.get(i).getProduct().getSellingPrice());
                        object.put("pro_Option_id",selectedComboProductList.get(i).getProduct().getProductOption().get(0).getOption().getId());
                        object.put("pro_Option_name",selectedComboProductList.get(i).getProduct().getProductOption().get(0).getOption().getName());
                        object.put("pro_Option_value_id",selectedComboProductList.get(i).getProduct().getProductOption().get(0).getOptionValue().getId());
                        object.put("pro_Option_value_name",selectedComboProductList.get(i).getProduct().getProductOption().get(0).getOptionValue().getName());
                        float totalPrice = selectedComboProductList.get(i).getProduct().getQuantity() * Float.parseFloat(selectedComboProductList.get(i).getProduct().getSellingPrice());

                        object.put("pro_total",String.valueOf(totalPrice));
                        object.put("pro_scheme","");
                        object.put("pack_of",selectedComboProductList.get(i).getProduct().getLabel().getName());
                        object.put("combo_id",offerDetail.getComboData().getOfferItem().getOfferID());
                        object.put("combo_name",offerDetail.getComboData().getOfferItem().getOfferTitle());
                        object.put("title_in_invoice",offerDetail.getComboData().getOfferItem().getTitleInInvoice());
                        productArray.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                progressDialog.show();

                Call<AppModel> addComboCall;
                if (isEditMode) {
                    addComboCall = adminAPI.addComboCall(comboCartID, comboID, userID, roleID, ownerID, String.valueOf(selectedComboProductList.size()), String.valueOf(totalMRP), String.valueOf(totalSellingPrice), productArray.toString());
                } else {
                    addComboCall = adminAPI.addComboCall(null, comboID, userID, roleID, ownerID, String.valueOf(selectedComboProductList.size()), String.valueOf(totalMRP), String.valueOf(totalSellingPrice), productArray.toString());
                }
                addComboCall.enqueue(new Callback<AppModel>() {
                    @Override
                    public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                        progressDialog.dismiss();
                        AppModel model = response.body();

                        if (model.isStatus()) {
                            Globals.Toast2(getApplicationContext(), model.getMessage());
                            isProductEdited = true;
                            onBackPressed();
                        } else {
                            Globals.Toast2(getApplicationContext(), model.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<AppModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Globals.showError(t, getApplicationContext());
                    }
                });


            }
        });

        progressDialog.show();

        if (isEditMode) {
            btn_addToCart.setText("UPDATE COMBO PACK");
            Call<ComboCartEdit> cartEditCall = adminAPI.editComboCartDetailCall(comboCartID, comboID, roleID);
            cartEditCall.enqueue(new Callback<ComboCartEdit>() {
                @Override
                public void onResponse(Call<ComboCartEdit> call, Response<ComboCartEdit> response) {
                    progressDialog.dismiss();
                    Log.e("onResponse: ",call.request().body().toString() );
                    ComboCartEdit cartEdit = response.body();
                    if (cartEdit.isStatus()) {
                        txt_requiredQty.setText(cartEdit.getEditData().getOfferItem().getMinQty());
                        txt_totalQty.setText(cartEdit.getEditData().getComboCart().getProductQty());

                        offerDetail = new ComboOfferDetail();
                        offerDetail.initComboData();
                        offerDetail.getComboData().setOfferItem(cartEdit.getEditData().getOfferItem());

                        setEditData(cartEdit.getEditData());
                    } else {
                        Globals.Toast2(getApplicationContext(), cartEdit.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ComboCartEdit> call, Throwable t) {
                    progressDialog.dismiss();
                    Globals.showError(t, getApplicationContext());
                }
            });
        } else {
            Call<ComboOfferDetail> offerDetailCall = adminAPI.comboOfferDetailCall(roleID, comboID);
            offerDetailCall.enqueue(this);
        }
    }

    private void setEditData(ComboCartEdit.CartEditData editData) {
        comboProductList.addAll(editData.getOfferItem().getComboProductList());
        productListAdapter.notifyDataSetChanged();
        String products = editData.getComboCart().getProducts();

        try {
            JSONArray productArray = new JSONArray(products);

            for (int i = 0; i < productArray.length(); i++) {
                JSONObject object = productArray.getJSONObject(i);

                productListAdapter.updateProductQty(object.optString("pro_id"), object.optString("pro_qty"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        if (isEditMode) {
            Intent intent = new Intent();
            if (isProductEdited) {
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED, intent);
            }
        }
        finish();
    }

    @Override
    public void onResponse(Call<ComboOfferDetail> call, Response<ComboOfferDetail> response) {
        progressDialog.dismiss();
        offerDetail = response.body();

        String extraDiscount = "0.00";
        if (offerDetail.isStatus()) {
            extraDiscount = offerDetail.getComboData().getOfferItem().getDiscount_percentage();
            comboProductList.addAll(offerDetail.getComboData().getComboProductList());
            txt_requiredQty.setText(offerDetail.getComboData().getOfferItem().getMinQty());

        } else {
            Globals.Toast2(this, offerDetail.getMessage());
        }

        productListAdapter.notifyDataSetChanged();
        productListAdapter.setExtraDiscountPrice(extraDiscount);
    }

    @Override
    public void onFailure(Call<ComboOfferDetail> call, Throwable t) {
        progressDialog.dismiss();
        Globals.showError(t, this);
    }

    @Override
    public void onQunatityChanged(int quantity) {
        txt_totalQty.setText(String.valueOf(quantity));
    }
}
