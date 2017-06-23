package com.agraeta.user.btl.Distributor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitalee on 9/3/2016.
 */
public class DisRetailerAdapter extends BaseAdapter {

    List<Bean_Dis_Retailer> disRetList;
    //TextView fname;
    Context context;
    Activity activity;
    AppPrefs prefs;
    String userID;
    String uid;
    String statusD,disUserID;
    String jsonPerson="";
    String jsonRetInfo="";
    String json;


    public DisRetailerAdapter(Context context, List<Bean_Dis_Retailer> disRetList, Activity activity){
        this.context=context;
        this.disRetList=disRetList;
        this.activity=activity;

    }

    @Override
    public int getCount() {
        return disRetList.size();
    }

    @Override
    public Object getItem(int position) {
        return disRetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_listview_prof_ret,parent,false);

        final Bean_Dis_Retailer retailerPerson=disRetList.get(position);

        TextView txtName=(TextView) view.findViewById(R.id.txt_prof_name);
        Switch sb = (Switch) view.findViewById(R.id.sb);

        //ImageView edit_icon=(ImageView) view.findViewById(R.id.edit_icon);
        final ImageView info_icon=(ImageView) view.findViewById(R.id.info_icon);
        info_icon.setTag(String.valueOf(disRetList.get(position).getRetailer_id()));
        txtName.setText(retailerPerson.getFirstName()+" "+retailerPerson.getLastName());
        //txtName.setText(salesPerson.getFirstName());
        //txtName.setText(disSalesList.get(position).getFirstName());
//        String txtname=dis_sales_b.getFirstName();
//        txtName.setText(""+txtname);
        prefs=new AppPrefs(context);
        userID=prefs.getUserId();
        //disUserID=prefs.getDisUserId();
        //statusD=prefs.getStatusPR();
        disRetList.get(position).getStatus();




//        edit_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context,Ed.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("retailerPerson", retailerPerson);
//                context.startActivity(intent);
//                activity.finish();
//            }
//        });

        statusD =disRetList.get(position).getStatus();
        //Log.e("ADAPTER STATUS",""+disRetList.get(position).getStatus());
        if(statusD.toString().trim().equalsIgnoreCase("1"))
            sb.setChecked(true);
        else sb.setChecked(false);


        //sb.setChecked(Boolean.getBoolean(statusD));



        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // If the switch button is on
                    //tv.setBackgroundColor(Color.GREEN);
                    //tv.setText("Switch is on.");
                    //prefs = new AppPrefs(context);
                    //Log.e("oooooooooo","on");
                   /* prefs.setStatusPR("1");
                    statusD=prefs.getStatusPR();*/
                    statusD ="1";
                    //statusD = "1";
                    disUserID=String.valueOf(disRetList.get(position).getRetailer_id());
                    //Log.e("oooooooooo",""+disUserID);
                    new GetDisRetStatus().execute();

                }
                else {
                    // If the switch button is off
                    //tv.setBackgroundColor(Color.RED);
                    //tv.setText("Switch is off.");
                    //Log.e("oooooooooo","off");
                    /*prefs.setStatusPR("0");
                    statusD=prefs.getStatusPR();*/
                    statusD = "0";
                    disUserID=String.valueOf(disRetList.get(position).getRetailer_id());
                    //Log.e("oooooooooo",""+disUserID);
                   // uid=prefs.getDisUserId();
                    new GetDisRetStatus().execute();

                }
            }
        });

        info_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("CLICKEDdddddddddddddddd",""+statusD);
                disUserID = info_icon.getTag().toString();
                new GetDisRetInformation(activity,String.valueOf(disRetList.get(position).getRetailer_id())).execute();

                //showDetailInfoDialog();

            }
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
//        super.notifyDataSetChanged();
        Log.d("Size in Adapter",disRetList.size()+"");
    }

    public class GetDisRetStatus extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        Custom_ProgressDialog loadingView;



        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
       /* public GetDisProfStatus(Context context,String disUserID, String statusd){
            activity=activity;
            this.context=context;
            this.disUserID=disUserID;
            this.statusd=statusd;
        }*/

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(activity, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                // parameters.add(new BasicNameValuePair("distributor_id", String.valueOf(distributor_id)));
                parameters.add(new BasicNameValuePair("user_id",disUserID));
                parameters.add(new BasicNameValuePair("status",statusD));

                //Log.e("urvi",""+disUserID);
                //Log.e("urvi",""+statusD);

                ////Log.e("distributor id", "" + distributor_id+"");
               /* //Log.e("user id", "11");
                //Log.e("status ", "" + statusd+"");*/


                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Update_User_Status",ServiceHandler.POST,parameters);

                //System.out.println("Data From Server " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
               // System.out.println("Exception " + e.toString());

                return json;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonPerson=jsonData;

            //Log.e("json",jsonData);

            if(!jsonData.equalsIgnoreCase("")){
                try {


                    //System.out.println(jsonData);

                    if (jsonData.equalsIgnoreCase("")
                            || (jsonData.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                        Globals.CustomToast(activity, "SERVER ERRER", activity.getLayoutInflater());
                        loadingView.dismiss();

                    } else {
                        JSONObject jObj = new JSONObject(jsonData);

                        boolean date = jObj.getBoolean("status");

                        if (date == false) {
                            String Message = jObj.getString("message");

                            Globals.CustomToast(activity, "" + Message, activity.getLayoutInflater());
                            loadingView.dismiss();
                           /* Intent i = new Intent(activity, DisRetailerListActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(i);*/
                        } else {
                            String Message = jObj.getString("message");
                            Globals.CustomToast(activity, "" + Message, activity.getLayoutInflater());
                            loadingView.dismiss();
                          /*  Intent i = new Intent(activity, DisRetailerListActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(i);*/
                        }

                    }
                }
                catch (Exception j)
                {
                    j.printStackTrace();
                }
            }
            loadingView.dismiss();
            //refreshLayout.setRefreshing(false);

        }
    }

    private void showDetailInfoDialog(int position) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_popup_info_details, null);
        dialogBuilder.setView(dialogView);

        final TextView fname=(TextView)dialogView.findViewById(R.id.d_fname);
        final TextView lname=(TextView)dialogView.findViewById(R.id.d_lname);
        final TextView firmName=(TextView)dialogView.findViewById(R.id.d_firmname);
        final TextView creditLimit=(TextView)dialogView.findViewById(R.id.d_creditlimit);
        final TextView creditPeriod=(TextView)dialogView.findViewById(R.id.d_creditperiod);
        final TextView address1=(TextView)dialogView.findViewById(R.id.d_address1);
        final TextView address2=(TextView)dialogView.findViewById(R.id.d_address2);
        final TextView address3=(TextView)dialogView.findViewById(R.id.d_address3);
        final TextView pincode=(TextView)dialogView.findViewById(R.id.d_pincode);
        final TextView country=(TextView)dialogView.findViewById(R.id.d_country);
        final TextView state=(TextView)dialogView.findViewById(R.id.d_state);
        final TextView city=(TextView)dialogView.findViewById(R.id.d_city);
        final TextView area=(TextView)dialogView.findViewById(R.id.d_area);
        final TextView contactPersonName=(TextView)dialogView.findViewById(R.id.d_contact_p_name);
        final TextView telephone=(TextView)dialogView.findViewById(R.id.d_tele);
        final TextView emailid=(TextView)dialogView.findViewById(R.id.d_emailid);
        final TextView panNo=(TextView)dialogView.findViewById(R.id.d_panno);
        final TextView vatNo=(TextView)dialogView.findViewById(R.id.d_vatno);
        final TextView serviceTaxNo=(TextView)dialogView.findViewById(R.id.d_servicetaxno);
        final TextView phoneNO=(TextView)dialogView.findViewById(R.id.d_mobile);
        final ImageView cancel=(ImageView)dialogView.findViewById(R.id.btn_cancel);



//        if(disRetList.get(0).getFirstName().toString().equalsIgnoreCase("") || disRetList.get(0).getFirstName().toString().equalsIgnoreCase(null)){
//            fname.setText("N/A");
//        }else {
            fname.setText(disRetList.get(position).getFirstName());
      //  }
        if(disRetList.get(position).getLastName().equalsIgnoreCase("") || disRetList.get(position).getLastName().equalsIgnoreCase(null)){
            lname.setText("N/A");
        }else {
            lname.setText(disRetList.get(position).getLastName());
        }
        //Log.e("1212121221",""+disRetList.get(0).getFirmName());
        if(disRetList.get(position).getFirmName().equalsIgnoreCase("") || disRetList.get(position).getFirmName().equalsIgnoreCase(null)){
            firmName.setText("N/A");
        }else {
            firmName.setText(disRetList.get(position).getFirmName());
        } if(disRetList.get(position).getCreditLimit().equalsIgnoreCase("") || disRetList.get(position).getCreditLimit().equalsIgnoreCase(null)){
            creditLimit.setText("N/A");
        }else {
            creditLimit.setText(disRetList.get(position).getCreditLimit());
        }if(disRetList.get(position).getCreditPeriod().equalsIgnoreCase("") || disRetList.get(position).getCreditPeriod().equalsIgnoreCase(null)){
            creditPeriod.setText("N/A");
        }else {
            creditPeriod.setText(disRetList.get(position).getCreditPeriod());
        }if(disRetList.get(position).getAddress1().equalsIgnoreCase("") || disRetList.get(position).getAddress1().equalsIgnoreCase(null)){
            address1.setText("N/A");
        }else {
            address1.setText(disRetList.get(position).getAddress1());
        }if(disRetList.get(position).getAddress2().equalsIgnoreCase("") || disRetList.get(position).getAddress2().equalsIgnoreCase(null)){
            address2.setText("N/A");
        }else {
            address2.setText(disRetList.get(position).getAddress2());
        }if(disRetList.get(position).getAddress3().equalsIgnoreCase("") || disRetList.get(position).getAddress3().equalsIgnoreCase(null)){
            address3.setText("N/A");
        }else {
            address3.setText(disRetList.get(position).getAddress3());
        }if(disRetList.get(position).getPincode().equalsIgnoreCase("") || disRetList.get(position).getPincode().equalsIgnoreCase(null)){
            pincode.setText("N/A");
        }else {
            pincode.setText(disRetList.get(position).getPincode());
        }if(disRetList.get(position).getCountry().equalsIgnoreCase("") || disRetList.get(position).getCountry().equalsIgnoreCase(null)){
            country.setText("N/A");
        }else {
            country.setText(disRetList.get(position).getCountry());
        }if(disRetList.get(position).getState().equalsIgnoreCase("") || disRetList.get(position).getState().equalsIgnoreCase(null)){
            state.setText("N/A");
        }else {
            state.setText(disRetList.get(position).getState());
        }if(disRetList.get(position).getCity().equalsIgnoreCase("") || disRetList.get(position).getCity().equalsIgnoreCase(null)){
            city.setText("N/A");
        }else {
            city.setText(disRetList.get(position).getCity());
        }if(disRetList.get(position).getArea().equalsIgnoreCase("") || disRetList.get(position).getArea().equalsIgnoreCase(null)){
            area.setText("N/A");
        }else {
            area.setText(disRetList.get(position).getArea());
        }if(disRetList.get(position).getContactPersonName().equalsIgnoreCase("") || disRetList.get(position).getContactPersonName().equalsIgnoreCase(null)){
            contactPersonName.setText("N/A");
        }else {
            contactPersonName.setText(disRetList.get(position).getContactPersonName());
        }if(disRetList.get(position).getTelephoneNo().equalsIgnoreCase("") || disRetList.get(position).getTelephoneNo().equalsIgnoreCase(null)){
            telephone.setText("N/A");
        }else {
            telephone.setText(disRetList.get(position).getTelephoneNo());
        }if(disRetList.get(position).getEmailid().equalsIgnoreCase("") || disRetList.get(position).getEmailid().equalsIgnoreCase(null)){
            emailid.setText("N/A");
        }else {
            emailid.setText(disRetList.get(position).getEmailid());
        }if(disRetList.get(position).getPancardno().equalsIgnoreCase("") || disRetList.get(position).getPancardno().equalsIgnoreCase(null)){
            panNo.setText("N/A");
        }else {
            panNo.setText(disRetList.get(position).getPancardno());
        }if(disRetList.get(position).getVatno().equalsIgnoreCase("") || disRetList.get(position).getVatno().equalsIgnoreCase(null)){
            vatNo.setText("N/A");
        }else {
            vatNo.setText(disRetList.get(position).getVatno());
        }if(disRetList.get(position).getServiceTaxNo().equalsIgnoreCase("") || disRetList.get(position).getServiceTaxNo().equalsIgnoreCase(null)){
            serviceTaxNo.setText("N/A");
        }else {
            serviceTaxNo.setText(disRetList.get(position).getServiceTaxNo());
        }if(disRetList.get(position).getPhone_no().equalsIgnoreCase("") || disRetList.get(position).getPhone_no().equalsIgnoreCase(null)){
            phoneNO.setText("N/A");
        }else {
            phoneNO.setText(disRetList.get(position).getPhone_no());
        }


        //new GetDisRetInformation(activity,disUserID).execute();


        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });


        b.show();


    }

    public class GetDisRetInformation extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        //String role_id;
        String jsonData="";

        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
        public GetDisRetInformation(Activity activity,String user_id){
            this.activity=activity;
            this.user_id=user_id;
            //this.role_id=role_id;
        }


        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(activity, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                // parameters.add(new BasicNameValuePair("distributor_id", String.valueOf(distributor_id)));
                parameters.add(new BasicNameValuePair("user_id",user_id));
                //parameters.add(new BasicNameValuePair("role_id",role_id));

                ////Log.e("distributor id", "" + distributor_id+"");
                //Log.e("user id", "" + user_id+"");
                ////Log.e("role id", "" + role_id+"");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_User_Info",ServiceHandler.POST,parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
             //   System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonRetInfo=jsonData;

            int position=0;

            if(!jsonData.equalsIgnoreCase("")){
                //Log.e("mmmmmmmmm","mmmmmmm");
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){

                        //Log.e("lllllllllllll","llllllllllll");
                        JSONObject dataObject=mainObject.getJSONObject("data");
                        JSONObject userObject=dataObject.getJSONObject("User");
                        JSONObject disObject=dataObject.getJSONObject("Distributor");
                        JSONObject addressObject=dataObject.getJSONObject("Address");
                        //JSONArray addressArray=dataObject.getJSONArray("Address");
                        JSONObject countryObject=addressObject.getJSONObject("Country");
                        JSONObject stateObject=addressObject.getJSONObject("State");
                        JSONObject cityObject=addressObject.getJSONObject("City");
                        JSONObject areaObject=addressObject.getJSONObject("Area");

                        for(int i=0; i<disRetList.size(); i++){
                            if(String.valueOf(disRetList.get(i).getRetailer_id()).equals(userObject.getString("id"))){
                                position=i;

                                disRetList.get(i).setFirstName(userObject.getString("first_name"));
                                disRetList.get(i).setLastName(userObject.getString("last_name"));
                                disRetList.get(i).setPhone_no(userObject.getString("phone_no"));
                                //Log.e("MOBILE NO",""+userObject.getString("phone_no"));
                                disRetList.get(i).setEmailid(userObject.getString("email_id"));
                                //Log.e("setFirmName",""+disObject.getString("firm_name"));
                                disRetList.get(i).setFirmName(disObject.getString("firm_name"));
                                disRetList.get(i).setCreditLimit(disObject.getString("tally_credit_limit"));
                                disRetList.get(i).setCreditPeriod(disObject.getString("tally_default_credit_period"));
                                disRetList.get(i).setContactPersonName(disObject.getString("tally_contact_person_name"));
                                disRetList.get(i).setTelephoneNo(disObject.getString("tally_telephone"));
                                disRetList.get(i).setPancardno(disObject.getString("tally_pan_no"));
                                disRetList.get(i).setVatno(disObject.getString("tally_vat_tin_no"));
                                disRetList.get(i).setServiceTaxNo(disObject.getString("tally_service_tax_reg_no"));





                                disRetList.get(i).setAddress1(addressObject.getString("address_1"));
                                disRetList.get(i).setAddress2(addressObject.getString("address_2"));
                                disRetList.get(i).setAddress3(addressObject.getString("address_3"));

                                disRetList.get(i).setCountry(countryObject.getString("name"));
                                disRetList.get(i).setState(stateObject.getString("name"));
                                disRetList.get(i).setCity(cityObject.getString("name"));
                                disRetList.get(i).setArea(areaObject.getString("name"));

                                break;
                            }
                        }

                        //Log.e("nnnnnnnnnnnnn","nnnnnnnnnnnnnnnn");
                        //JSONObject subObj=dataObject.getJSONObject(String.valueOf(0));


//                        for(int i=0; i<addressArray.length(); i++){
//                            //Log.e("33333333333333333333",""+userID);
//                            JSONObject subObject=addressArray.getJSONObject(i);
//                            Bean_Dis_Retailer ret1=new Bean_Dis_Retailer();
//
//                            ret1.setAddress1(subObject.getString("address_1"));
//                            ret1.setAddress2(subObject.getString("address_2"));
//                            ret1.setAddress3(subObject.getString("address_3"));
//
//
//                            JSONObject countryObject=subObject.getJSONObject("Country");
//                            JSONObject stateObject=subObject.getJSONObject("State");
//                            JSONObject cityObject=subObject.getJSONObject("City");
//                            JSONObject areaObject=subObject.getJSONObject("Area");
//
//
//                            ret1.setCountry(countryObject.getString("name"));
//                            ret1.setState(stateObject.getString("name"));
//                            ret1.setCity(cityObject.getString("name"));
//                            ret1.setArea(areaObject.getString("name"));
//
//                            disRetList.add(ret1);
//                        }


                        }
                        notifyDataSetChanged();
                        //Log.e("Size",disRetList.size()+"");
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
            showDetailInfoDialog(position);
        }
    }

    public void updateData(List<Bean_Dis_Retailer> disRetList) {
        this.disRetList=disRetList;
        notifyDataSetChanged();
    }



}
