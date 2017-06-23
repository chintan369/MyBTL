package com.agraeta.user.btl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Reset_Password extends AppCompatActivity {

    Button btn_reset_forgotpassword;
    EditText et_reset_confirm_newpassword,et_reset_newpassword;
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
        setContentView(R.layout.activity_rest__password);
        fetchId();

    }
    private void fetchId()
    {
        btn_reset_forgotpassword=(Button)findViewById(R.id.btn_reset_forgotpassword);
        et_reset_confirm_newpassword=(EditText)findViewById(R.id.et_reset_confirm_newpassword);
        et_reset_newpassword=(EditText)findViewById(R.id.et_reset_newpassword);


        btn_reset_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reset_Password.this,MainPage_drawer.class);
                startActivity(i);
            }
        });
    }

}
