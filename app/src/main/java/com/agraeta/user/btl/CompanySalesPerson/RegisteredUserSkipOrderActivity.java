package com.agraeta.user.btl.CompanySalesPerson;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.BTL;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.UnregisteredUserData;
import com.agraeta.user.btl.model.area.AreaData;
import com.agraeta.user.btl.model.area.AreaItem;
import com.agraeta.user.btl.model.sales.SalesSubUserData;
import com.agraeta.user.btl.utils.FilePath;
import com.kyleduo.switchbutton.SwitchButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisteredUserSkipOrderActivity extends AppCompatActivity {

    TextView txt_firmName, txt_addrLine1, txt_addrLine2, txt_addrLine3, txt_country, txt_state, txt_city, txt_area, txt_pincode, txt_contactPerson, txt_emailID, txt_mobile, txt_partyReport, txt_dealingBrand, txt_customerType, txt_comment, txt_jointVisitWith;
    EditText edt_firmName, edt_addrLine1, edt_addrLine2, edt_addrLine3, edt_pincode, edt_contactPerson, edt_emailID, edt_mobile, edt_partyReport, edt_dealingBrand, edt_comment, edt_jointVisitWith,edt_orderID;
    Spinner spn_country, spn_state, spn_city, spn_area, spn_skipReason;
    Button btn_addVisitingFront, btn_addVisitingBack, btn_submit;
    FrameLayout layout_visitngFront, layout_visitngBack;
    TextView txt_visitingFrontPath, txt_visitingBackPath, txt_add_attachment;
    ImageView img_visitingFront, img_removeVisitingFront, img_visitingBack, img_removeVisitingBack;
    LinearLayout layout_attachment;
    ScrollView layout_scroll;
    SwitchButton switch_isPastOrder;

    int predefinedReasons=0;

    long oneMB = 1024 * 1024;

    GPSTracker gpsTracker;

    AdminAPI adminAPI;
    Call<AreaData> countryData;
    Call<AreaData> stateData;
    Call<AreaData> cityData;
    Call<AreaData> areaData;
    Call<AreaData> customerTypeCall;

    Custom_ProgressDialog dialog;

    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> areaAdapter;
    ArrayAdapter<String> reasonAdapter;

    ArrayList<String> countryNames=new ArrayList<>();
    ArrayList<String> stateNames=new ArrayList<>();
    ArrayList<String> cityNames=new ArrayList<>();
    ArrayList<String> areaNames=new ArrayList<>();
    ArrayList<String> reasonList=new ArrayList<>();

    ArrayList<AreaItem> countryList=new ArrayList<>();
    ArrayList<AreaItem> stateList=new ArrayList<>();
    ArrayList<AreaItem> cityList=new ArrayList<>();
    ArrayList<AreaItem> areaList=new ArrayList<>();

    int PICK_FRONTCARD = 1, PICK_BACKCARD = 2, PICK_ATTACHMENT = 3;

    AppPrefs prefs;

    boolean isInEditMode=false;
    UnregisteredUserData.UnregisteredUser unregisteredUser;
    SalesSubUserData subUserData;

    String skipPersonID="";
    String firmName="";
    String customerTypeID= "";
    boolean isFromTour = false;
    private boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user_skip_order);

        prefs=new AppPrefs(getApplicationContext());
        adminAPI= ServiceGenerator.getAPIServiceClass();
        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);

        gpsTracker = new GPSTracker(this);

        if(!gpsTracker.canGetLocation()){
            gpsTracker.showSettingsAlert();
        }

        Intent intent=getIntent();
        firmName=intent.getStringExtra("firmName");
        skipPersonID=intent.getStringExtra("salesComapnyPerson");
        customerTypeID=intent.getStringExtra("userRoleID");
        isInEditMode=intent.getBooleanExtra("isInEditMode",false);

        if(isInEditMode){
            unregisteredUser=(UnregisteredUserData.UnregisteredUser) intent.getSerializableExtra("userData");
        } else {
            subUserData = (SalesSubUserData) intent.getSerializableExtra("userData");
        }

        setActionBar();
        fetchIDs();
        workOnIDs();
    }

    private void fetchIDs() {
        txt_firmName = (TextView) findViewById(R.id.txt_firmName);
        txt_addrLine1 = (TextView) findViewById(R.id.txt_addrLine1);
        txt_addrLine2 = (TextView) findViewById(R.id.txt_addrLine2);
        txt_addrLine3 = (TextView) findViewById(R.id.txt_addrLine3);
        txt_country = (TextView) findViewById(R.id.txt_country);
        txt_state = (TextView) findViewById(R.id.txt_state);
        txt_city = (TextView) findViewById(R.id.txt_city);
        txt_area = (TextView) findViewById(R.id.txt_area);
        txt_pincode = (TextView) findViewById(R.id.txt_pincode);
        txt_contactPerson = (TextView) findViewById(R.id.txt_contactPerson);
        txt_emailID = (TextView) findViewById(R.id.txt_emailID);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        txt_partyReport = (TextView) findViewById(R.id.txt_partyReport);
        txt_dealingBrand = (TextView) findViewById(R.id.txt_dealingBrand);
        txt_customerType = (TextView) findViewById(R.id.txt_customerType);
        txt_comment = (TextView) findViewById(R.id.txt_comment);
        txt_jointVisitWith = (TextView) findViewById(R.id.txt_jointVisitWith);




        switch_isPastOrder=(SwitchButton) findViewById(R.id.switch_isPastOrder);

        C.setCompulsoryText(txt_firmName);
        C.setCompulsoryText(txt_addrLine1);
        C.setCompulsoryText(txt_country);
        C.setCompulsoryText(txt_state);
        C.setCompulsoryText(txt_city);
        C.setCompulsoryText(txt_area);
        C.setCompulsoryText(txt_pincode);
        C.setCompulsoryText(txt_partyReport);
        C.setCompulsoryText(txt_customerType);

        txt_visitingFrontPath = (TextView) findViewById(R.id.txt_visitingFrontPath);
        txt_visitingBackPath = (TextView) findViewById(R.id.txt_visitingBackPath);
        txt_add_attachment = (TextView) findViewById(R.id.txt_add_attachment);

        edt_firmName = (EditText) findViewById(R.id.edt_firmName);
        edt_addrLine1 = (EditText) findViewById(R.id.edt_addrLine1);
        edt_addrLine2 = (EditText) findViewById(R.id.edt_addrLine2);
        edt_addrLine3 = (EditText) findViewById(R.id.edt_addrLine3);
        edt_pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_contactPerson = (EditText) findViewById(R.id.edt_contactPerson);
        edt_emailID = (EditText) findViewById(R.id.edt_emailID);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_partyReport = (EditText) findViewById(R.id.edt_partyReport);
        edt_dealingBrand = (EditText) findViewById(R.id.edt_dealingBrand);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        edt_jointVisitWith = (EditText) findViewById(R.id.edt_jointVisitWith);
        edt_orderID=(EditText) findViewById(R.id.edt_orderID);
        edt_orderID.setEnabled(false);

        edt_firmName.setEnabled(false);
        edt_addrLine1.setEnabled(false);
        edt_addrLine2.setEnabled(false);
        edt_addrLine3.setEnabled(false);
        edt_pincode.setEnabled(false);
        edt_contactPerson.setEnabled(false);
        edt_emailID.setEnabled(false);
        edt_mobile.setEnabled(false);

        spn_country.setEnabled(false);
        spn_state.setEnabled(false);
        spn_city.setEnabled(false);
        spn_area.setEnabled(false);

        edt_firmName.setText(firmName);

        if(isInEditMode){
            edt_firmName.setText(unregisteredUser.getFirm_name());
            edt_addrLine1.setText(unregisteredUser.getAddress_1());
            edt_addrLine2.setText(unregisteredUser.getAddress_2());
            edt_addrLine3.setText(unregisteredUser.getAddress_3());
            edt_pincode.setText(unregisteredUser.getPincode());
            edt_contactPerson.setText(unregisteredUser.getContact_person());
            edt_emailID.setText(unregisteredUser.getEmail());
            edt_mobile.setText(unregisteredUser.getMobile_no());
            edt_dealingBrand.setText(unregisteredUser.getDealing_in_brand());
            edt_jointVisitWith.setText(unregisteredUser.getJoint_visit_with());
        } else {
            edt_firmName.setText(subUserData.getDistributor().getFirm_name());
            if (subUserData.getAddress().size() > 0) {
                edt_addrLine1.setText(subUserData.getAddress().get(0).getAddress_1());
                edt_addrLine2.setText(subUserData.getAddress().get(0).getAddress_2());
                edt_addrLine3.setText(subUserData.getAddress().get(0).getAddress_3());
                edt_pincode.setText(subUserData.getAddress().get(0).getPincode());
            }
            edt_emailID.setText(subUserData.getUser().getEmail_id());
            edt_mobile.setText(subUserData.getUser().getPhone_no());
        }

        spn_country = (Spinner) findViewById(R.id.spn_country);
        spn_state = (Spinner) findViewById(R.id.spn_state);
        spn_city = (Spinner) findViewById(R.id.spn_city);
        spn_area = (Spinner) findViewById(R.id.spn_area);
        spn_skipReason = (Spinner) findViewById(R.id.spn_skipReason);

        countryAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.textview,countryNames);
        stateAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.textview,stateNames);
        cityAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.textview,cityNames);
        areaAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.textview,areaNames);
        reasonAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.textview,reasonList);

        spn_country.setAdapter(countryAdapter);
        spn_state.setAdapter(stateAdapter);
        spn_city.setAdapter(cityAdapter);
        spn_area.setAdapter(areaAdapter);
        spn_skipReason.setAdapter(reasonAdapter);

        btn_addVisitingFront = (Button) findViewById(R.id.btn_addVisitingFront);
        btn_addVisitingBack = (Button) findViewById(R.id.btn_addVisitingBack);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        layout_visitngFront = (FrameLayout) findViewById(R.id.layout_visitngFront);
        layout_visitngBack = (FrameLayout) findViewById(R.id.layout_visitngBack);
        layout_attachment = (LinearLayout) findViewById(R.id.layout_attachment);
        layout_scroll = (ScrollView) findViewById(R.id.layout_scroll);

        img_visitingFront = (ImageView) findViewById(R.id.img_visitingFront);
        img_removeVisitingFront = (ImageView) findViewById(R.id.img_removeVisitingFront);
        img_visitingBack = (ImageView) findViewById(R.id.img_visitingBack);
        img_removeVisitingBack = (ImageView) findViewById(R.id.img_removeVisitingBack);

        dialog.show();
        countryData=adminAPI.getCountryList();
        countryData.enqueue(new Callback<AreaData>() {
            @Override
            public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                dialog.dismiss();
                countryList.clear();
                countryNames.clear();

                countryNames.add("Select Country");
                countryList.add(new AreaItem("Select Country"));

                AreaData areaData=response.body();
                if(areaData.isStatus()){
                    countryNames.addAll(areaData.getAreaNames());
                    countryList.addAll(areaData.getAreaItemList());
                }
                countryAdapter.notifyDataSetChanged();

                if(isInEditMode && isFirstTime){
                    int pos=0;
                    for(int i=0; i<countryList.size(); i++){
                        if(countryList.get(i).getId().equals(unregisteredUser.getCountry())){
                            pos=i;
                            break;
                        }
                    }
                    if(pos==0) isFirstTime=false;
                    spn_country.setSelection(pos);
                } else if (!isInEditMode && isFirstTime) {
                    int pos = 0;
                    for (int i = 0; i < countryList.size(); i++) {
                        if (countryList.get(i).getId().equals(subUserData.getAddress().get(0).getCountry_id())) {
                            pos = i;
                            break;
                        }
                    }
                    if (pos == 0) isFirstTime = false;
                    spn_country.setSelection(pos);
                }

            }

            @Override
            public void onFailure(Call<AreaData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });

        new GetReasonList().execute();

        switch_isPastOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edt_orderID.setEnabled(true);
                }
                else {
                    edt_orderID.setEnabled(false);
                    edt_orderID.setText("");
                }
            }
        });

        spn_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    clearData(3);
                }
                else {
                    Call<AreaData> getStateCall=adminAPI.getAreasData(countryList.get(position).getId(),null);
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
                            else {
                                Globals.Toast2(getApplicationContext(),areaData.getMessage());
                            }
                            stateAdapter.notifyDataSetChanged();

                            if(isInEditMode && isFirstTime){
                                int pos=0;
                                for(int i=0; i<stateList.size(); i++){
                                    if(stateList.get(i).getId().equals(unregisteredUser.getState())){
                                        pos=i;
                                        break;
                                    }
                                }
                                if(pos==0) isFirstTime=false;
                                spn_state.setSelection(pos);
                            } else if (!isInEditMode && isFirstTime) {
                                int pos = 0;
                                for (int i = 0; i < stateList.size(); i++) {
                                    try {
                                        if (stateList.get(i).getId().equals(subUserData.getAddress().get(0).getState_id())) {
                                            pos = i;
                                            break;
                                        }
                                    } catch (Exception e) {
                                        Log.e("Exception", e.getMessage());
                                    }

                                }
                                if (pos == 0) isFirstTime = false;
                                spn_state.setSelection(pos);
                            }

                        }

                        @Override
                        public void onFailure(Call<AreaData> call, Throwable t) {
                            dialog.dismiss();
                            Globals.showError(t,getApplicationContext());
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    clearData(2);
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

                if(position==0){
                    clearData(1);
                }
                else if(position==cityNames.size()-1){
                    showAddOtherCityDialog();
                }
                else {
                    loadAreaData(cityList.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_skipReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==reasonList.size()-1){
                    showAddOtherReasonDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showAddOtherReasonDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.layout_dialog_add_other_city,null);

        LinearLayout layout_state=(LinearLayout) view.findViewById(R.id.layout_state);
        layout_state.setVisibility(View.GONE);

        builder.setCancelable(false);
        final EditText edt_skipReason=(EditText) view.findViewById(R.id.edt_cityName);
        edt_skipReason.setHint("Other Reason");

        builder.setView(view);

        builder.setTitle("Add Other Reason");

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
                        String skipReason=edt_skipReason.getText().toString().trim();
                        if(skipReason.isEmpty()){
                            Globals.Toast2(getApplicationContext(),"Please Enter Reason");
                        }
                        else {
                            reasonList.add(reasonList.size()-1,skipReason);
                            reasonAdapter.notifyDataSetChanged();
                            spn_skipReason.setSelection(reasonList.size()-2);
                            dialogB.dismiss();
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogB.dismiss();
                        spn_skipReason.setSelection(0);
                    }
                });
            }
        });

        dialogB.show();
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

                if(isInEditMode && isFirstTime){
                    int pos=0;
                    for(int i=0; i<cityList.size(); i++){
                        if(cityList.get(i).getId().equals(unregisteredUser.getCity())){
                            pos=i;
                            break;
                        }
                    }
                    if(pos==0) isFirstTime=false;
                    spn_city.setSelection(pos);
                } else if (!isInEditMode && isFirstTime) {
                    int pos = 0;
                    for (int i = 0; i < cityList.size(); i++) {
                        if (cityList.get(i).getId().equals(subUserData.getAddress().get(0).getCity_id())) {
                            pos = i;
                            break;
                        }
                    }
                    if (pos == 0) isFirstTime = false;
                    spn_city.setSelection(pos);
                }
            }

            @Override
            public void onFailure(Call<AreaData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });
    }

    private void loadAreaData(String cityID) {
        dialog.show();
        Call<AreaData> areaCall=adminAPI.getAreaData(cityID);
        areaCall.enqueue(new Callback<AreaData>() {
            @Override
            public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                dialog.dismiss();
                areaNames.clear();
                areaList.clear();

                areaNames.add("Select Area");
                areaList.add(new AreaItem("Select Area"));

                AreaData cityData=response.body();

                if(cityData.isStatus()){
                    areaNames.addAll(cityData.getAreaNames());
                    areaList.addAll(cityData.getAreaItemList());
                }
                else {
                    Globals.Toast2(getApplicationContext(),cityData.getMessage());
                }

                areaNames.add("Other Area");
                areaList.add(new AreaItem("Other Area"));

                areaAdapter.notifyDataSetChanged();

                if(isInEditMode && isFirstTime){
                    int pos=0;
                    for(int i=0; i<areaList.size(); i++){
                        if(areaList.get(i).getId().equals(unregisteredUser.getArea())){
                            pos=i;
                            break;
                        }
                    }
                    isFirstTime=false;
                    spn_area.setSelection(pos);
                } else if (!isInEditMode && isFirstTime) {
                    int pos = 0;
                    for (int i = 0; i < areaList.size(); i++) {
                        if (areaList.get(i).getId().equals(subUserData.getAddress().get(0).getArea_id())) {
                            pos = i;
                            break;
                        }
                    }
                    isFirstTime = false;
                    spn_area.setSelection(pos);
                }
            }

            @Override
            public void onFailure(Call<AreaData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });
    }

    private void clearData(int level) {
        if(level>=1){
            areaNames.clear();
            areaList.clear();
            areaNames.add("Select Area");
            areaList.add(new AreaItem("Select Area"));
            areaAdapter.notifyDataSetChanged();
        }

        if(level>=2){
            cityNames.clear();
            cityList.clear();
            cityNames.add("Select City");
            cityList.add(new AreaItem("Select City"));
            cityAdapter.notifyDataSetChanged();
        }

        if(level>=3){
            stateNames.clear();
            stateList.clear();
            stateNames.add("Select State");
            stateList.add(new AreaItem("Select State"));
            stateAdapter.notifyDataSetChanged();
        }
    }

    private void workOnIDs() {
        btn_addVisitingFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FRONTCARD);
            }
        });

        btn_addVisitingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_BACKCARD);
            }
        });

        txt_add_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalAttachment = layout_attachment.getChildCount();

                if (totalAttachment < 4) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*|application/*|text/*|video/*");
                    startActivityForResult(intent, PICK_ATTACHMENT);
                } else {
                    Globals.Toast2(getApplicationContext(), "You can attach max 4 attachment only!");
                }
            }
        });

        img_removeVisitingFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFrontCard();
            }
        });

        img_removeVisitingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBackCard();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailID=edt_emailID.getText().toString().trim();
                String mobileNo=edt_mobile.getText().toString().trim();
                String orderID=edt_orderID.getText().toString().trim();
                int orderIDInt=orderID.isEmpty() ? 0 : Integer.parseInt(orderID);

                if(!isValidated(edt_firmName,"Firm Name","Please Enter Firm name min of 3 letter",3,false)) return;
                if(!isValidated(edt_addrLine1,"Address Line 1","Please Enter Address Line 1 min of 3 letter",3,false)) return;
                if(spn_country.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select Country");
                    return;
                }
                if(spn_state.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select State");
                    return;
                }
                if(spn_city.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select City");
                    return;
                }
                if(spn_area.getSelectedItemPosition()==0){
                    Globals.Toast2(getApplicationContext(),"Please Select Area");
                    return;
                }
                if(!isValidated(edt_pincode,"Pincode","Please Enter Pincode of 6 Digit",6,false)) return;
                if(!emailID.isEmpty() && !isValidated(edt_emailID,"Email ID","Please Enter Valid Email ID",0,true)) return;
                if(!mobileNo.isEmpty() && !isValidated(edt_mobile,"Mobile No","Please Enter Valid Mobile No",10,false)) return;

                if (spn_skipReason.getSelectedItemPosition() == 0) {
                    Globals.Toast2(getApplicationContext(), "Please Select Skip Reason");
                    return;
                }

                if(!isValidated(edt_partyReport,"Party Report","Please Enter Party Report",3,false)) return;

                if (txt_visitingFrontPath.getText().toString().isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Select Visiting Card Front Image");
                    return;
                }

                if (txt_visitingBackPath.getText().toString().isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Select Visiting Card Back Image");
                    return;
                }

                if(switch_isPastOrder.isChecked() && !(orderIDInt>0)){
                    Globals.Toast2(getApplicationContext(),"Please Enter Valid Order ID");
                    edt_orderID.requestFocus();
                    return;
                }

                gpsTracker=new GPSTracker(getApplicationContext());

                if(!gpsTracker.canGetLocation()){
                    gpsTracker.showSettingsAlert();
                    return;
                }

                String latitude= String.valueOf(gpsTracker.getLatitude()); // returns latitude
                String longitude= String.valueOf(gpsTracker.getLongitude()); // returns longitude

                String firmName=edt_firmName.getText().toString().trim();
                String addressLine1=edt_addrLine1.getText().toString().trim();
                String addressLine2=edt_addrLine2.getText().toString().trim();
                String addressLine3=edt_addrLine3.getText().toString().trim();
                String pincode=edt_pincode.getText().toString().trim();
                String contactPersonName=edt_contactPerson.getText().toString().trim();
                String partyReferenceNo=edt_partyReport.getText().toString().trim();
                String dealingInBrand=edt_dealingBrand.getText().toString().trim();
                String comment=edt_comment.getText().toString().trim();
                String jointVisitWith=edt_jointVisitWith.getText().toString().trim();

                List<MultipartBody.Part> files=new ArrayList<MultipartBody.Part>();

                if(!txt_visitingFrontPath.getText().toString().trim().isEmpty()){
                    files.add(C.prepareFilePart("visiting_card1",txt_visitingFrontPath.getText().toString().trim()));
                }
                if(!txt_visitingBackPath.getText().toString().trim().isEmpty()){
                    files.add(C.prepareFilePart("visiting_card2",txt_visitingBackPath.getText().toString().trim()));
                }

                if(layout_attachment.getChildCount()>0){
                    int childCount=layout_attachment.getChildCount();
                    for(int i=0; i<childCount; i++){
                        View view=layout_attachment.getChildAt(i);

                        TextView txtPath=(TextView) view.findViewById(R.id.txt_filePath);

                        files.add(C.prepareFilePart("attachment"+(i+1),txtPath.getText().toString().trim()));
                    }
                }

                String countryID=countryList.get(spn_country.getSelectedItemPosition()).getId();
                String stateID=stateList.get(spn_state.getSelectedItemPosition()).getId();
                String cityID=cityList.get(spn_city.getSelectedItemPosition()).getId();
                String areaID=areaList.get(spn_area.getSelectedItemPosition()).getId();
                String skipReason="";
                String otherSkipReason="";

                if(spn_skipReason.getSelectedItemPosition()<(predefinedReasons+1)){
                    skipReason=spn_skipReason.getSelectedItem().toString();
                }
                else {
                    otherSkipReason=spn_skipReason.getSelectedItem().toString();
                }

                orderID=String.valueOf(orderIDInt);

                String isPastOrder=switch_isPastOrder.isChecked() ? "1" : "0";

                dialog.show();

                Call<AppModel> addRegisteredUser;

                if(files.size()>0){
                    addRegisteredUser = adminAPI.addRegisteredUser(prefs.getUserId(), skipPersonID, skipReason, otherSkipReason, latitude, longitude, firmName, addressLine1, addressLine2, addressLine3, countryID, stateID, cityID, areaID, pincode, contactPersonName, emailID, mobileNo, partyReferenceNo, dealingInBrand, customerTypeID, comment, jointVisitWith, isPastOrder, orderID, BTL.salesPersonTourID, files);
                }
                else {
                    addRegisteredUser = adminAPI.addRegisteredUser(prefs.getUserId(), skipPersonID, skipReason, otherSkipReason, latitude, longitude, firmName, addressLine1, addressLine2, addressLine3, countryID, stateID, cityID, areaID, pincode, contactPersonName, emailID, mobileNo, partyReferenceNo, dealingInBrand, customerTypeID, comment, jointVisitWith, isPastOrder, orderID, BTL.salesPersonTourID, null);
                }

                addRegisteredUser.enqueue(new Callback<AppModel>() {
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

    private boolean isValidated(EditText editText, String emptyMessage, String errorMessage, int minLenth, @Nullable boolean isEmail){
        String text=editText.getText().toString().trim();

        boolean success=true;

        if(text.isEmpty()){
            editText.requestFocus();
            Globals.Toast2(this,"Please Enter "+emptyMessage);
            success=false;
        }
        else if(minLenth>0 && text.length()<minLenth){
            editText.requestFocus();
            Globals.Toast2(this,errorMessage);
            success=false;
        }
        else if(isEmail && !C.validEmail(text)){
            editText.requestFocus();
            Globals.Toast2(this,errorMessage);
            success=false;
        }

        if(!success){
            layout_scroll.setSmoothScrollingEnabled(true);
            layout_scroll.smoothScrollTo(editText.getLeft(),editText.getTop());
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_ATTACHMENT && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                Uri dataUri = data.getData();

                Log.e("URI", dataUri.toString());

                addNewAttachment(FilePath.getPath(this, dataUri));
            } else {
                Globals.Toast2(this, "Sorry, File not Found!");
            }
        } else if (requestCode == PICK_FRONTCARD && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                Uri dataUri = data.getData();
                setFrontCard(FilePath.getPath(this, dataUri));
            }
        } else if (requestCode == PICK_BACKCARD && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                Uri dataUri = data.getData();
                setBackCard(FilePath.getPath(this, dataUri));
            }
        }
    }

    private void setBackCard(String path) {
        File file = new File(path);
        long length = file.length() / oneMB;

        if (length > 2) {
            Globals.Toast2(this, "Please Select File of Max 2 MB");
        } else {
            layout_visitngBack.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(file)
                    .into(img_visitingBack);
            txt_visitingBackPath.setText(path);
        }
    }

    private void setFrontCard(String path) {
        File file = new File(path);
        long length = file.length() / oneMB;

        if (length > 2) {
            Globals.Toast2(this, "Please Select File of Max 2 MB");
        } else {
            layout_visitngFront.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(file)
                    .into(img_visitingFront);
            txt_visitingFrontPath.setText(path);
        }
    }

    private void removeFrontCard() {
        txt_visitingFrontPath.setText("");
        layout_visitngFront.setVisibility(View.GONE);
    }

    private void removeBackCard() {
        txt_visitingBackPath.setText("");
        layout_visitngBack.setVisibility(View.GONE);
    }

    private void addNewAttachment(String dataUri) {

        String fileType = dataUri.substring(dataUri.lastIndexOf(".") + 1);

        Uri uri = Uri.parse(dataUri);

        Log.e("DataUri", uri.toString());

        if (!FilePath.getImageExts().contains(fileType) && !FilePath.getVideoExts().contains(fileType) && !FilePath.getDocExts().contains(fileType)) {
            Globals.Toast2(this, "Please Select Image, Video or Document File only!");
        } else {
            final View view = getLayoutInflater().inflate(R.layout.layout_attachment, null);

            ImageView img_type = (ImageView) view.findViewById(R.id.img_type);
            TextView txt_fileName = (TextView) view.findViewById(R.id.txt_fileName);
            ImageView img_remove = (ImageView) view.findViewById(R.id.img_remove);
            TextView txt_filePath = (TextView) view.findViewById(R.id.txt_filePath);

            File file = new File(dataUri);

            String fileName = file.getName();

            if (FilePath.getImageExts().contains(fileType)) {
                img_type.setImageResource(R.drawable.icon_type_image);
            } else if (FilePath.getVideoExts().contains(fileType)) {
                img_type.setImageResource(R.drawable.icon_type_video);
            } else if (FilePath.getDocExts().contains(fileType)) {
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
        setResult(RESULT_CANCELED);
        finish();
    }

    private class GetReasonList extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "OrderSkipReason/App_GetOrderSkipReason", ServiceHandler.POST);

            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            reasonList.clear();
            reasonList.add("Select Reason");

            if (s != null && !s.isEmpty()) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getBoolean("status")) {
                        JSONArray data = object.getJSONArray("data");

                        predefinedReasons = data.length();

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject main = data.getJSONObject(i);
                            reasonList.add(main.getString("title"));
                        }
                    }

                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            reasonList.add("Other Reason");
            reasonAdapter.notifyDataSetChanged();
        }
    }
}
