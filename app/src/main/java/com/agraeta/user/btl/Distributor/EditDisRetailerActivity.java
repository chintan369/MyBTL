package com.agraeta.user.btl.Distributor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.HttpFileUpload;
import com.agraeta.user.btl.R;

public class EditDisRetailerActivity extends AppCompatActivity {

    EditText et_first_name_dis_sal,et_last_name_dis_sal,et_mobile_dis_sal1,et_emailid_dis_sal,et_alt_mobile_dis_sal1,et_panno_dis_sal1,et_aadhaarcardno_dis_sal1;
    Button btn_submit_sal1,btn_reset_sal;
    Bean_Dis_Retailer disRetailerList;
    Spinner spn_status;
    String[] status={"Active","Inactive"};
    ArrayAdapter<String> status_adp;

    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart;
    FrameLayout unread;
    TextView message_tv;
    String smobile = new String();
    String amobile = new String();
    String pannumber = new String();
    String profile_array="";
    Custom_ProgressDialog loadingView;
    String json = new String();
    String FilePathsend = new String();
    String FileNamesend = new String();
    String dataSalesEdit=new String();
    String wa = new String();
    HttpFileUpload httpupload;

    String firstname = new String();
    String lastname = new String();
    String email_id = new String();
    String phone_no = new String();
    String pancard_sales = new String();
    String alternate_no = new String();
    String aadhar_card_sales = new String();

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
        setContentView(R.layout.activity_edit_dis_retailer);

        Intent intent=getIntent();
        disRetailerList=(Bean_Dis_Retailer) intent.getSerializableExtra("retailerPerson");
        fetchID();
    }

    private void fetchID() {

    }
}
