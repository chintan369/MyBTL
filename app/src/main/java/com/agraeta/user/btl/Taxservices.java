package com.agraeta.user.btl;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Taxservices extends AppCompatActivity {
LinearLayout  addimage;
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
        setContentView(R.layout.activity_taxservices);

        fetchID();

    }
    private void fetchID()
    {
        addimage=(LinearLayout)findViewById(R.id.addimage);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Taxservices.this);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                dialog.setCanceledOnTouchOutside(false);
                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.adddoc_dialog);







                dialog.show();
            }
        });
    }
}
