package com.agraeta.user.btl;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

/**
 * Created by chaitalee on 3/14/2016.
 */
public class Globals {

    //public static String server_link ="";

    public static final String SEARCH_BY_KEYWORD = "Product/App_Get_Product_Name";
  //  public static String server_link ="http://app.nivida.in/agraeta/";
  public static final String REORDER_PRODUCTS = "Product/App_Get_Reoder_Product_List";
    public static final String USER_LIST = "User/APP_Admin_User_List";
    public static final String USER_LIST_UNDER_SALESPERSON = "User/APP_Admin_User_List_For_Sales_Person";

   //public static String server_link ="http://192.168.1.104/agraeta/";
  //  public static String server_link ="http://192.168.1.121/agraeta/";

   //public static String server_link1 ="http://192.168.1.114/agraeta/";
    public static final String ORDER_LIST_FOR_ADMIN="Order/App_Get_Orders_For_Admin";
    public static final String ORDER_DETAIL_FOR_ADMIN="Order/App_Get_Order_Details";
    public static final String ADMIN_DASHBOARD="Order/App_Get_Admin_Dashboard";
    public static final String ORDER_TRACKING="Order/App_Get_Order_Tracking";
    public static final String ORDER_INQUIRY="Order/App_Get_Order_Inquiry";
    public static final String ALL_ENQUIRY_COUNT="Order/App_Get_Enquiry_For_Admin";
    public static final String PRODUCT_ENQUIRY_LIST="ProductEnquiry/App_Get_Enquiry_For_Admin";
    public static final String PRODUCT_REGISTRATION_LIST="ProductRegistration/App_Get_Product_Registration_For_Admin";
    public static final String BUSINESS_ENQUIRY="BusinessEnquiry/App_Get_Business_Enquiry_For_Admin";
    public static final String CUSTOMER_COMPLAINTS="CustomerComplaint/App_Get_Customer_Complaint_For_Admin";
    public static final String CORP_OFFICE_ENQUIRY_LIST="CorporateOffice/App_Get_CorporateOffice_For_Admin";
    public static final String CARRER_LIST="Career/App_Get_Career_For_Admin";
    public static final String COUPON_LIST="Coupon/App_Get_Coupon_For_Admin";
    public static final String SCHEME_LIST="Scheme/App_Get_Scheme_For_Admin";
    public static final String REORDER_PRODUCT_IN_CART="CartData/App_Reorder_Product";
    public static final String NONMRP_PRODUCT_ENQUIRY="CustomProduct/App_Add_Custom_ProductEnquiry";
    public static final String TALLY_BILLS="User/APP_Party_Outstanding_Balance";
    public static final String BECOME_SELLER="User/App_Seller_Registration";
    public static final String ADD_UNREGISTERED_USER="UnRegisteredUser/App_Add_UnRegisteredUser";
    public static final String ADD_REGISTERED_USER="OrderSkipReason/App_AddOrderReason";
    public static final String GET_UNREGISTERED_USER="UnRegisteredUser/App_GetUnRegisteredUser";
    public static final String GET_REGISTERED_USER_DSR="OrderSkipReason/App_GetRegisteredUserDSR";
    public static final String GET_REGISTERED_SELLER="Seller/App_GetSeller";
    public static final String COMBO_LIST="ComboPack/App_Get_ComboPacks";
    public static final String COMBO_DETAIL="ComboPack/App_Get_ComboPacks_Details";
    public static final String ADD_COMBO="ComboCart/App_AddComboCart";
    public static final String GET_COMBO_CART_ID = "ComboCart/App_Get_Combo_Cart_ID";
    public static final String DELETE_COMBO="ComboCart/App_Delete_ComboCart";
    public static final String EDIT_COMBO_DETAIL="ComboCart/App_Get_ComboCart";
    public static final String COUNTRY_LIST = "Country/App_GetCountry";
    public static final String STATE_LIST = "State/App_GetState";
    public static final String CITY_LIST = "City/App_GetCity";
    public static final String AREA_LIST = "Area/App_GetArea";
    public static final String USER_ROLES = "Role/App_Get_User_Type";
    public static final String ADD_OTHER_CITY = "City/App_Add_City";
    public static final String ADD_OTHER_AREA = "Area/App_Add_Area";
    public static final String CREATE_QUOTATION="Quotation/App_Create_Quotation";
    public static final String DELETE_QUOTATION="QuotationCart/App_Delete_Quotation";
    public static final String DELETE_QUOTATION_PRODUCT="QuotationCart/App_Delete_Quotation_Product";
    public static final String EDIT_QUOTATION="QuotationCart/App_Edit_Quotation_Data";
    public static final String ADD_QUOTATION="QuotationCart/App_Add_Quotation_Data";
    public static final String GET_QUOTATION_DATA="QuotationCart/App_Get_Quotation_Data";
    public static final String SEND_QUOTATION="Quotation/App_Add_Quotation";
    public static final String SUPPORT_INFO="User/App_Get_Call_info";
    public static final String VIDEO_GALLERY="VideoGallery/App_Get_Video_Gallery";
    public static final String PAGE_DOWNLOAD="InformationPage/App_GetInformationPageContent";
    public static final String GET_NOTIFICATION_LIST="inbox/App_Get_Inbox";
    public static final String CLEAR_NOTIFICATIONS="inbox/App_Remove_Inbox";
    public static final String SET_READ_NOTIFICATIONS="inbox/App_Set_Read_Inbox";
    public static final String SET_READ_MESSAGE="inbox/App_Set_Read_Message";
    public static final String GET_UNREAD_NOTIFICATION_COUNT="inbox/App_Get_UnRead_Inbox_Count";
    public static final String GET_TOUR="Tour/App_Get_Tour";
    public static final String USER_SALES_STATEWISE="User/APP_Sales_Person_StateWise";
    public static final String PRODUCT_STOCK_REPORT = "Product/App_Tally_Product_Stock";
    public static final String GET_QUOTATION_LIST = "Quotation/App_GetQuotation";
    public static final String CATALOG_LIST = "Catalog/App_Get_Download_Catalog";
    public static final String USER_INFO = "User/APP_User_Info";

    public static final String GET_CART_ITEM_QTY = "CartData/App_GetItemQty";
    public static final String GET_SCHEME_DETAILS = "Scheme/App_Get_Scheme_Details";
    public static final String GET_ALL_PRODUCT = "Product/App_Get_Product_Details";

    public static String share = "https://play.google.com/store/apps/details?id=com.agraeta.user.btl&hl=en";
    // public static String server_link ="http://www.btlkart.com/";
    // public static String server_link = "http://demo.btlindia.com/";
    public static String server_link = "http://www.btlindia.com/";

    public static String IMAGE_LINK = server_link + "files/";

    //  public static String server_link1 = "http://demo.btlindia.com";
    public static String server_link1 = "http://www.btlindia.com";
    static boolean connect = true;
    public static boolean isConnectingToInternet1(Context con){

        BroadcastReceiver mConnReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
	        /*String reason = intent
	                .getStringExtra(ConnectivityManager.EXTRA_REASON);*/
                boolean isFailover = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_IS_FAILOVER, false);

            //    ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);



                @SuppressWarnings("deprecation")
                NetworkInfo currentNetworkInfo = intent
                        .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                // NetworkInfo otherNetworkInfo = (NetworkInfo)
                // intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

                if (noConnectivity){
                    connect = false;

                }
                else if (currentNetworkInfo.isConnected()) {
                    connect = true;


                } else connect = !isFailover;
            }
        };
        return connect;
    }

    public static boolean isConnectingToInternet(Context con){
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    public static void CustomToast(Context context, String st, LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.toast_custom, null);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(st);
        text.setTextSize(14);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);

        if(st!=null &&!st.trim().isEmpty()) toast.show();

    }

    public static void Toast(Context context, String message) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom, null);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(message);
        text.setTextSize(14);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);

        if(message!=null && !message.trim().isEmpty()) toast.show();
    }

    public static void Toast2(Context context, String message) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom, null);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(message);
        text.setTextSize(14);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        if(message!=null && !message.trim().isEmpty()) toast.show();
    }

    public static void defaultError(Context context){
        Toast2(context,"Server might not responding or Something went wrong!\nPlease try later.");
    }

    public static void noInternet(Context context){
        Toast2(context,"No connection found!\nPlease connect to network first.");
    }

    public static void showError(Throwable t,Context context){
        if(t instanceof UnknownHostException || t instanceof ConnectException) noInternet(context);
        else defaultError(context);

        Log.e("Exception", t.getMessage());
    }

    public static void generateNoteOnSD(Context context, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "BTL/Logs");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "BTLLog.txt");
            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append("\n"+sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
        }
    }

    public static String format(long value) {
        if(value < 1000) {
            return "\u20B9 " +format("###", value)+".00";
        } else if(value < 100000) {
            int other = (int) (value / 1000);
            //return "\u20B9 " +format("##,###", other);
            return "\u20B9 " +format("##,###", value)+".00";
        }
        else if(value < 10000000){
            int other = (int) (value / 100000);
            //return "\u20B9 " +format("##", other) + " Lac";
            return "\u20B9 " +format("##,##,###", value)+".00";
        }
        else {
            float other = ((float) value / 10000000);
            Log.e("Value",value+"-->"+other);
            return "\u20B9 " +format(",##.##", other) + " Cr";
        }
    }

    private static String format(String pattern, Object value) {
        return new DecimalFormat(pattern).format(value);
    }

    public static void startCountAnimation(int number, final TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, number);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText("" + (int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
