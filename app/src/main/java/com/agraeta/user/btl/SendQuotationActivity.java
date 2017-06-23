package com.agraeta.user.btl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.model.AddLess;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendQuotationActivity extends AppCompatActivity {

    EditText edt_title,edt_toName,edt_emailTo,edt_ccEmail,edt_vat;
    TextView txt_subTotal,txt_vatLabel,txt_vatPrice,txt_grandTotalPrice;
    FloatingActionButton fab_addless;
    LinearLayout layout_addless;
    Button btn_sendQuotation;

    String subTotalValue="";
    String quotationID="";

    ArrayList<AddLess> addLessItemList=new ArrayList<>();
    private DatabaseHandler db;
    private ArrayList<Bean_User_data> user_data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_quotation);

        Intent intent=getIntent();
        subTotalValue=intent.getStringExtra("subTotal");
        quotationID=intent.getStringExtra("quotationID");

        fetchIDs();
    }

    private void fetchIDs() {
        edt_title=(EditText) findViewById(R.id.edt_title);
        edt_toName=(EditText) findViewById(R.id.edt_toName);
        edt_emailTo=(EditText) findViewById(R.id.edt_emailTo);
        edt_ccEmail=(EditText) findViewById(R.id.edt_ccEmail);
        edt_vat=(EditText) findViewById(R.id.edt_vat);

        txt_subTotal=(TextView) findViewById(R.id.txt_subTotal);
        txt_vatLabel=(TextView) findViewById(R.id.txt_vatLabel);
        txt_vatPrice=(TextView) findViewById(R.id.txt_vatPrice);
        txt_grandTotalPrice=(TextView) findViewById(R.id.txt_grandTotalPrice);
        btn_sendQuotation= (Button) findViewById(R.id.btn_sendQuotation);

        String subTotal=subTotalValue.isEmpty() ? "0" : subTotalValue;
        float subTotalF=Float.parseFloat(subTotal);

        txt_subTotal.setText(getResources().getString(R.string.Rs)+" "+formatFloat(subTotalF));

        fab_addless=(FloatingActionButton) findViewById(R.id.fab_addless);
        layout_addless=(LinearLayout) findViewById(R.id.layout_addless);

        fab_addless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLessDialog();
            }
        });

        edt_vat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String vat=edt_vat.getText().toString().trim();
                vat = vat.isEmpty() ? "0" : vat;
                float percent=Float.parseFloat(vat);
                if(percent>100){
                    Globals.Toast2(getApplicationContext(),"VAT Percent must be in 1 - 100");
                    edt_vat.setText("100");
                    return;
                }
                float subTotal=Float.parseFloat(subTotalValue.isEmpty() ? "0" : subTotalValue);

                float vatPriceValue=countPercent(subTotal,percent);

                txt_vatLabel.setText("Out Put Vat @ "+vat+"% :");
                txt_vatPrice.setText(getResources().getString(R.string.Rs)+" "+formatFloat(vatPriceValue));
                countTotalPrice();
            }
        });

        edt_vat.setText("1");

        btn_sendQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=edt_title.getText().toString().trim();
                String toName=edt_toName.getText().toString().trim();
                String email=edt_emailTo.getText().toString().trim();
                String emailCC=edt_ccEmail.getText().toString().trim();
                String vat=edt_vat.getText().toString().trim();

                vat = vat.isEmpty() ? "0" : vat;
                float vatF=Float.parseFloat(vat);

                if(title.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Title");
                }
                else if(toName.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Receiver's Name");
                }
                else if(email.isEmpty()){
                    Globals.Toast2(getApplicationContext(),"Please Enter Receiver's Email");
                }
                else if(!C.validEmail(email)){
                    Globals.Toast2(getApplicationContext(),"Please Enter Valid Receiver's Email");
                }
                else if(!emailCC.isEmpty() && !C.validEmail(emailCC)){
                    Globals.Toast2(getApplicationContext(),"Please Enter Valid CC Email");
                }
                else if(vat.isEmpty() || vatF<=1){
                    Globals.Toast2(getApplicationContext(),"Please Enter at Least vat 1%");
                }
                else {

                    JSONArray addLessArray=new JSONArray();

                    for(int i=0; i<addLessItemList.size(); i++){
                        JSONObject object=new JSONObject();

                        try {
                            object.put("add_less_type",addLessItemList.get(i).isToAdd() ? "1" : "2");
                            object.put("title",addLessItemList.get(i).getTitle());
                            object.put("amount",addLessItemList.get(i).getAmount());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        addLessArray.put(object);
                    }


                    setRefershData();

                    if(user_data.size()>0){
                        String userID="";
                        String ownerID=user_data.get(0).getUser_id();
                        String roleID=user_data.get(0).getUser_type();

                        AppPrefs app=new AppPrefs(getApplicationContext());

                        if (roleID.equals(C.ADMIN) || roleID.equals(C.COMP_SALES_PERSON) || roleID.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                            roleID = app.getSubSalesId();
                            userID = app.getSalesPersonId();
                        } else {
                            userID = ownerID;
                        }

                        final Custom_ProgressDialog dialog=new Custom_ProgressDialog(SendQuotationActivity.this,"");
                        dialog.setCancelable(false);
                        dialog.show();

                        AdminAPI adminAPI= ServiceGenerator.getAPIServiceClass();
                        Call<AppModel> modelCall=adminAPI.sendQuotationCall(userID,roleID,email,emailCC,title,toName,quotationID,ownerID,addLessArray.toString(),vat);
                        modelCall.enqueue(new Callback<AppModel>() {
                            @Override
                            public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                                dialog.dismiss();
                                AppModel model=response.body();
                                if(model!=null){
                                    Globals.Toast2(getApplicationContext(),model.getMessage());

                                    if(model.isStatus()){
                                        setResult(RESULT_OK);
                                        finish();
                                    }

                                }
                                else {
                                    Globals.defaultError(getApplicationContext());
                                }
                            }

                            @Override
                            public void onFailure(Call<AppModel> call, Throwable t) {
                                dialog.dismiss();
                                Globals.showError(t,getApplicationContext());
                            }
                        });
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),"Please Login First");
                    }
                }
            }
        });
    }

    private void setRefershData() {
        user_data.clear();
        db = new DatabaseHandler(this);
        user_data = db.Get_Contact();
        db.close();
    }

    private void countTotalPrice() {
        String subTotal=subTotalValue.isEmpty() ? "0" : subTotalValue;
        String vat=edt_vat.getText().toString().trim();
        vat=vat.isEmpty() ? "0" : vat;

        float subTotalF = Float.parseFloat(subTotal);
        float vatF= Float.parseFloat(vat);

        float vatPrice=countPercent(subTotalF,vatF);

        float totalPrice=subTotalF+vatPrice;

        for(int i=0; i<addLessItemList.size(); i++){
            String currentAmount=addLessItemList.get(i).getAmount();
            currentAmount = currentAmount.isEmpty() ? "0" : currentAmount;

            if(addLessItemList.get(i).isToAdd()){
                totalPrice += Float.parseFloat(currentAmount);
            }
            else {
                totalPrice -= Float.parseFloat(currentAmount);
            }
        }

        txt_grandTotalPrice.setText(getResources().getString(R.string.Rs)+" "+formatFloat(totalPrice));
    }

    private float countPercent(float subTotal, float percent) {

        float percentValue=(subTotal * percent) / 100;

        return percentValue;
    }

    private String formatFloat(float value){
         try{
            return String.format(Locale.getDefault(),"%.2f",value);
         }catch (Exception e){
             return "0.00";
         }
    }

    private void showAddLessDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.layout_dialog_addless,null);
        final EditText edt_title, edt_amount;
        final Spinner spn_addless;

        edt_title=(EditText) view.findViewById(R.id.edt_title);
        edt_amount=(EditText) view.findViewById(R.id.edt_amount);
        spn_addless= (Spinner) view.findViewById(R.id.spn_addless);

        builder.setView(view);
        builder.setPositiveButton("Add",null);
        builder.setNegativeButton("Cancel",null);

        final AlertDialog dialog=builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogI) {
                Button btn_ok=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button btn_cancel=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title=edt_title.getText().toString().trim();
                        String amount=edt_amount.getText().toString().trim();
                        int spnPosition=spn_addless.getSelectedItemPosition();

                        float amountF = Float.parseFloat(amount.isEmpty() ? "0" : amount);

                        if(title.isEmpty()){
                            Globals.Toast2(getApplicationContext(),"Please Enter Title");
                        }
                        else if(spnPosition==0){
                            Globals.Toast2(getApplicationContext(),"Please Select Add / Less");
                        }
                        else if(amount.isEmpty() || amountF<=0){
                            Globals.Toast2(getApplicationContext(),"Please Enter Valid Amount");
                        }
                        else {
                            AddLess addLess=new AddLess();
                            addLess.setTitle(title);
                            addLess.setAmount(amount);
                            if(spnPosition==1){
                                addLess.setToAdd(true);
                            }
                            else {
                                addLess.setToAdd(false);
                            }

                            addLessItemList.add(addLess);
                            dialog.dismiss();
                            showAddedAddLessItems();
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void showAddedAddLessItems() {
        layout_addless.removeAllViews();

        for(int i=0; i<addLessItemList.size(); i++){
            View view=getLayoutInflater().inflate(R.layout.layout_addless_item,null);

            TextView txt_title=(TextView) view.findViewById(R.id.txt_title);
            TextView txt_amount=(TextView) view.findViewById(R.id.txt_amount);
            TextView txt_removeItem=(TextView) view.findViewById(R.id.txt_removeItem);

            String txtTitle=addLessItemList.get(i).getTitle();
            String addLess = addLessItemList.get(i).isToAdd() ? "ADD" : "LESS";
            txtTitle = txtTitle +"( "+addLess+" )";

            txt_title.setText(txtTitle);
            txt_amount.setText(addLessItemList.get(i).getAmount());
            final int finalI = i;
            txt_removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addLessItemList.remove(finalI);
                    showAddedAddLessItems();
                }
            });

            layout_addless.addView(view);
        }
        countTotalPrice();

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
