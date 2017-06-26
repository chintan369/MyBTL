package com.agraeta.user.btl;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.net.URLConnection;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Nivida new on 28-Feb-17.
 */

public class C {
    //USER ROLES
    public static final String ADMIN = "1";
    public static final String CUSTOMER = "2";
    public static final String DISTRIBUTOR = "3";
    public static final String DIRECT_DEALER = "4";
    public static final String PROFESSIONAL = "5";
    public static final String COMP_SALES_PERSON = "6";
    public static final String DISTRIBUTOR_SALES_PERSON = "7";
    public static final String THIRD_TIER_RETAILER = "8";
    public static final String DISTRIBUTOR_PROFESSIONAL = "9";
    public static final String CARPENTER = "10";

    //PRODUCT TYPES
    public static final String CATEGORY = "1";
    public static final String PRODUCT = "2";

    public static final String EXTRA_SPECIAL_SCHEME = "3";
    public static final String SPECIAL_SCHEME = "2";
    public static final String GENERAL_SCHEME = "1";
    private static final Pattern EMAIL_ADDRESS
            = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,64}" + "(" +
            "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,5}" + ")+"
    );

    public static int modOf(int value1, int value2) {
        if (value1 > value2)
            return value1 % value2;
        else
            return value2 % value1;
    }

    public static boolean validEmail(String email) {
        return !TextUtils.isEmpty(email) && C.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void showMinPackAlert(Activity activity, int minPackQty) {
        String messageToShow = "Please Enter Quantity in multiple of "+minPackQty+" or tap +/- button\n";
       String egMessage= "Eg: "+(minPackQty) +", "+ (minPackQty*2) +", "+ (minPackQty*3) +", etc..";

        View dialogView=activity.getLayoutInflater().inflate(R.layout.layout_dialog_minpack_alert,null);
        TextView txt_mainAlert=(TextView) dialogView.findViewById(R.id.txt_mainAlert);
        TextView txt_egAlert=(TextView) dialogView.findViewById(R.id.txt_egAlert);
        Button btn_ok=(Button) dialogView.findViewById(R.id.btn_ok);

        txt_mainAlert.setText(messageToShow);
        txt_egAlert.setText(egMessage);

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        final AlertDialog dialog=builder.create();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        //Log.e("mimeType",mimeType);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        //MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static void setCompulsoryText(TextView textView){
        textView.setText(Html.fromHtml(textView.getText().toString().trim() + " " + "<font color=\"#FF0000\">*</font>"));
    }

    public static String getYoutubeThumb(String urlID){
        String imgThumbURL="";

        int urlIndexOfV=urlID.indexOf("?v=");

        if(urlIndexOfV>=0){
            urlID=urlID.substring(urlIndexOfV);
            urlID=urlID.replace("?v=","");
        }

        imgThumbURL="http://i3.ytimg.com/vi/"+urlID+"/maxresdefault.jpg";

        return imgThumbURL;
    }

    public static String getYoutubeEmbedLink(String urlID){
        String embedLink="";

        int urlIndexOfV=urlID.indexOf("?v=");

        if(urlIndexOfV>=0){
            urlID=urlID.substring(urlIndexOfV);
            urlID=urlID.replace("?v=","");
        }

        embedLink = "https://www.youtube.com/embed/" + urlID + "?autoplay=1&rel=0";

        return embedLink;
    }
}
