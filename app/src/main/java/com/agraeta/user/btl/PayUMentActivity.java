package com.agraeta.user.btl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class PayUMentActivity extends AppCompatActivity {

    WebView payuweb;
    PayuData payuData;

    Custom_ProgressDialog dialog;

    String orderID="";
    String orderAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_payu);

        Intent intent=getIntent();
        payuData=(PayuData) intent.getSerializableExtra("payUData");
        orderID=intent.getStringExtra("orderID");
        orderAmount=intent.getStringExtra("orderAmount");
        dialog=new Custom_ProgressDialog(this,"Please wait. Loading...");
        dialog.setCancelable(false);

        fetchIDs();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void fetchIDs() {
        payuweb=(WebView) findViewById(R.id.payu_web);

        payuweb.setWebViewClient(new MyBrowser());

        payuweb.getSettings().setLoadsImagesAutomatically(true);
        payuweb.getSettings().setJavaScriptEnabled(true);
        payuweb.getSettings().setAllowContentAccess(true);
        payuweb.getSettings().setAllowFileAccess(true);
        payuweb.getSettings().setAllowFileAccessFromFileURLs(true);
        payuweb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        payuweb.getSettings().setLoadWithOverviewMode(true);
        payuweb.getSettings().setDomStorageEnabled(true);
        payuweb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        payuweb.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        String post_url = "<html>" +
                "<body onload=\"document.payu_form.submit()\">"+
                "<form method=\"post\" name=\"payu_form\" id=\"payu_form\" action=\""+payuData.getAction()+"\"> " +
                "<input type=\"hidden\" name=\"key\" value=\""+payuData.getKey()+"\"/>"+
                "<input type=\"hidden\" name=\"hash\" value=\""+payuData.getHash()+"\"/>"+
                "<input type=\"hidden\" name=\"txnid\" value=\""+payuData.getTxnid()+"\"/>"+
                "<input type=\"hidden\" name=\"amount\" value=\""+payuData.getAmount()+"\"/>"+
                "<input type=\"hidden\" name=\"productinfo\" value=\""+payuData.getProductinfo()+"\"/>"+
                "<input type=\"hidden\" name=\"firstname\" value=\""+payuData.getFirstname()+"\"/>"+
                "<input type=\"hidden\" name=\"lastname\" value=\""+payuData.getLastname()+"\"/>"+
                "<input type=\"hidden\" name=\"email\" value=\""+payuData.getEmail()+"\"/>"+
                "<input type=\"hidden\" name=\"phone\" value=\""+payuData.getPhone()+"\"/>"+
                "<input type=\"hidden\" name=\"address1\" value=\""+payuData.getAddress1()+"\"/>"+
                "<input type=\"hidden\" name=\"pincode\" value=\""+payuData.getPincode()+"\"/>"+
                "<input type=\"hidden\" name=\"city\" value=\""+payuData.getCity()+"\"/>"+
                "<input type=\"hidden\" name=\"state\" value=\""+payuData.getState()+"\"/>"+
                "<input type=\"hidden\" name=\"country\" value=\""+payuData.getCountry()+"\"/>"+
                "<input type=\"hidden\" name=\"surl\" value=\""+payuData.getSurl()+"\"/>"+
                "<input type=\"hidden\" name=\"furl\" value=\""+payuData.getFurl()+"\"/>"+
                "<input type=\"hidden\" name=\"service_provider\" value=\""+payuData.getService_provider()+"\"/>"+
                "</form>" +
                "</body>" +
                "</html>";
        Log.e("html",""+post_url);

        //String post_url1="{\"status\":true,\"data\":\"Success\"}";

        payuweb.loadData(post_url,"text/html","UTF-8");
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("MyURL",url);
            payuweb.loadUrl(url);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.show();
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            Log.e(" page url",url);

            dialog.dismiss();

            if(url.equalsIgnoreCase(payuData.getSurl())) {
                payuweb.setVisibility(View.GONE);
                payuweb.loadUrl("javascript:HtmlViewer.showHTML" +
                        "(document.getElementsByTagName('html')[0].innerHTML);");
                //new GetOrderData().execute();


            }
        }
    }

    private class MyJavaScriptInterface {

        private Context ctx;
        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            Log.e("HTML",html.replace("<head></head><body>","").replace("</body>",""));

            String json=html.replace("<head></head><body>","").replace("</body>","");

            try {
                JSONObject object=new JSONObject(json);

                if(object.getBoolean("status")){
                    DatabaseHandler db = new DatabaseHandler(PayUMentActivity.this);
                    db.Delete_ALL_table();
                    db.clear_cart();
                    db.close();
                    Intent intent=new Intent(PayUMentActivity.this, Thankyou_Page.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("orderID",orderID);
                    intent.putExtra("orderAmount",orderAmount);
                    startActivity(intent);
                    finish();
                }
                else {
                    Globals.Toast2(getApplicationContext(),object.getString("message"));
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("By Clicking OK, Payment transaction will be cancelled!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Cancel",null);

        AlertDialog dialog=builder.create();

        dialog.show();
    }
}
