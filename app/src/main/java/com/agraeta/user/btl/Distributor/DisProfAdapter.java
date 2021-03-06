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
 * Created by SEO on 9/8/2016.
 */
public class DisProfAdapter extends BaseAdapter{

    List<Bean_Dis_Prof> disProfList;
    Context context;
    Activity activity;
    AppPrefs prefs;
    String userID;
    String uid;
    String statusD,disUserID;
    String jsonPerson="";
    String jsonProfInfo="";
    String json;

    public DisProfAdapter(Context context, List<Bean_Dis_Prof> disProfList, Activity activity){
        this.context=context;
        this.disProfList=disProfList;
        this.activity=activity;

    }

    @Override
    public int getCount() {
        return disProfList.size();
    }

    @Override
    public Object getItem(int position) {
        return disProfList.get(position);
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

        final Bean_Dis_Prof profPerson=disProfList.get(position);

        TextView txtName=(TextView) view.findViewById(R.id.txt_prof_name);
        Switch sb = (Switch) view.findViewById(R.id.sb);


        //ImageView edit_icon=(ImageView) view.findViewById(R.id.edit_icon);
        ImageView info_icon=(ImageView) view.findViewById(R.id.info_icon);

        txtName.setText(profPerson.getFirstName()+" "+profPerson.getLastName());
        //txtName.setText(salesPerson.getFirstName());
        //txtName.setText(disSalesList.get(position).getFirstName());
//        String txtname=dis_sales_b.getFirstName();
//        txtName.setText(""+txtname);
        prefs=new AppPrefs(context);
        userID=prefs.getUserId();
        disUserID=prefs.getDisUserId22();
        statusD=prefs.getStatusPR22();


        statusD =disProfList.get(position).getStatus();
        //Log.e("ADAPTER STATUS",""+disProfList.get(position).getStatus());
        if(statusD.trim().equalsIgnoreCase("1"))
            sb.setChecked(true);
        else sb.setChecked(false);

        //sb.setChecked(Boolean.parseBoolean(prefs.getStatusPR22()));

        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new GetDisProfStatus(String.valueOf(disProfList.get(position).getProfessional_id()),isChecked,position).execute();
                /*if(isChecked){
                    // If the switch button is on
                    //tv.setBackgroundColor(Color.GREEN);
                    //tv.setText("Switch is on.");
                    //prefs = new AppPrefs(context);
                    //Log.e("oooooooooo","on");
                    prefs.setStatusPR("1");
                    statusD=prefs.getStatusPR22();
                    statusD = "1";
                    disUserID=prefs.getDisUserId22();
                    //Log.e("oooooooooo",""+disUserID);
                    uid=prefs.getDisUserId22();




                }
                else {
                    // If the switch button is off
                    //tv.setBackgroundColor(Color.RED);
                    //tv.setText("Switch is off.");
                    //Log.e("oooooooooo","off");
                    prefs.setStatusPR22("0");
                    statusD=prefs.getStatusPR22();
                    statusD = "0";
                    disUserID=prefs.getDisUserId22();
                    //Log.e("oooooooooo",""+disUserID);
                    // uid=prefs.getDisUserId();
                    new GetDisProfStatus().execute();

                }*/
            }
        });

        info_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("CLICKEDdddddddddddddddd",""+statusD);
                new GetDisProfInformation(activity,String.valueOf(profPerson.getProfessional_id())).execute();
                //showDetailInfoDialog();

            }
        });


//        edit_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context,EditDisSalesActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("profPerson",profPerson);
//                context.startActivity(intent);
//                activity.finish();
//            }
//        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",disProfList.size()+"");
    }

    public class GetDisProfStatus extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        Custom_ProgressDialog loadingView;

        String userID="";
        String isActive="0";
        int selectedPos=0;

        public GetDisProfStatus(String userID, boolean isActive,int position) {
            this.userID = userID;
            this.isActive = isActive ? "1" : "0";
            this.selectedPos=position;
        }

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
                parameters.add(new BasicNameValuePair("user_id",userID));
                parameters.add(new BasicNameValuePair("status",isActive));

                ///Log.e("urvi",""+disUserID);
                //Log.e("urvi",""+statusD);

                //Log.e("distributor id", "" + distributor_id+"");
               /* Log.e("user id", "11");
                Log.e("status ", "" + statusd+"");*/


                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Update_User_Status",ServiceHandler.POST,parameters);

                //System.out.println("Data From Server " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
              //  System.out.println("Exception " + e.toString());

                return json;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonPerson=jsonData;

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

                        if (!date) {
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
                            disProfList.get(selectedPos).setStatus(isActive);
                        }

                    }
                }
                catch (Exception j)
                {
                    j.printStackTrace();
                }
            }
            loadingView.dismiss();
            notifyDataSetChanged();
            //refreshLayout.setRefreshing(false);

        }
    }

    private void showDetailInfoDialog(int position) {

       // new GetDisProfInformation(activity,disUserID).execute();

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





        if(disProfList.get(position).getFirstName().isEmpty() || disProfList.get(position).getFirstName().equalsIgnoreCase(null)){
            fname.setText("N/A");
        }else {
            fname.setText(disProfList.get(position).getFirstName());
        }if(disProfList.get(position).getLastName().equalsIgnoreCase("") || disProfList.get(position).getLastName().equalsIgnoreCase(null)){
            lname.setText("N/A");
        }else {
            lname.setText(disProfList.get(position).getLastName());
        }if(disProfList.get(position).getFirmName().equalsIgnoreCase("") || disProfList.get(position).getFirmName().equalsIgnoreCase(null)){
            firmName.setText("N/A");
        }else {
            firmName.setText(disProfList.get(position).getFirmName());
        }if(disProfList.get(position).getCreditLimit().equalsIgnoreCase("") || disProfList.get(position).getCreditLimit().equalsIgnoreCase(null)){
            creditLimit.setText("N/A");
        }else {
            creditLimit.setText(disProfList.get(position).getCreditLimit());
        }if(disProfList.get(position).getCreditPeriod().equalsIgnoreCase("") || disProfList.get(position).getCreditPeriod().equalsIgnoreCase(null)){
            creditPeriod.setText("N/A");
        }else {
            creditPeriod.setText(disProfList.get(position).getCreditPeriod());
        }if(disProfList.get(position).getAddress1().equalsIgnoreCase("") || disProfList.get(position).getAddress1().equalsIgnoreCase(null)){
            address1.setText("N/A");
        }else {
            address1.setText(disProfList.get(position).getAddress1());
        }if(disProfList.get(position).getAddress2().equalsIgnoreCase("") || disProfList.get(position).getAddress2().equalsIgnoreCase(null)){
            address2.setText("N/A");
        }else {
            address2.setText(disProfList.get(position).getAddress2());
        }if(disProfList.get(position).getAddress3().equalsIgnoreCase("") || disProfList.get(position).getAddress3().equalsIgnoreCase(null)){
            address3.setText("N/A");
        }else {
            address3.setText(disProfList.get(position).getAddress3());
        }if(disProfList.get(position).getPincode().equalsIgnoreCase("") || disProfList.get(position).getPincode().equalsIgnoreCase(null)){
            pincode.setText("N/A");
        }else {
            pincode.setText(disProfList.get(position).getPincode());
        }if(disProfList.get(position).getCountry().equalsIgnoreCase("") || disProfList.get(position).getCountry().equalsIgnoreCase(null)){
            country.setText("N/A");
        }else {
            country.setText(disProfList.get(position).getCountry());
        }if(disProfList.get(position).getState().equalsIgnoreCase("") || disProfList.get(position).getState().equalsIgnoreCase(null)){
            state.setText("N/A");
        }else {
            state.setText(disProfList.get(position).getState());
        }if(disProfList.get(position).getCity().equalsIgnoreCase("") || disProfList.get(position).getCity().equalsIgnoreCase(null)){
            city.setText("N/A");
        }else {
            city.setText(disProfList.get(position).getCity());
        }if(disProfList.get(position).getArea().equalsIgnoreCase("") || disProfList.get(position).getArea().equalsIgnoreCase(null)){
            area.setText("N/A");
        }else {
            area.setText(disProfList.get(position).getArea());
        }if(disProfList.get(position).getContactPersonName().equalsIgnoreCase("") || disProfList.get(position).getContactPersonName().equalsIgnoreCase(null)){
            contactPersonName.setText("N/A");
        }else {
            contactPersonName.setText(disProfList.get(position).getContactPersonName());
        }if(disProfList.get(position).getTelephoneNo().equalsIgnoreCase("") || disProfList.get(position).getTelephoneNo().equalsIgnoreCase(null)){
            telephone.setText("N/A");
        }else {
            telephone.setText(disProfList.get(position).getTelephoneNo());
        }if(disProfList.get(position).getEmailid().equalsIgnoreCase("") || disProfList.get(position).getEmailid().equalsIgnoreCase(null)){
            emailid.setText("N/A");
        }else {
            emailid.setText(disProfList.get(position).getEmailid());
        }if(disProfList.get(position).getPancardno().equalsIgnoreCase("") || disProfList.get(position).getPancardno().equalsIgnoreCase(null)){
            panNo.setText("N/A");
        }else {
            panNo.setText(disProfList.get(position).getPancardno());
        }if(disProfList.get(position).getVatno().equalsIgnoreCase("") || disProfList.get(position).getVatno().equalsIgnoreCase(null)){
            vatNo.setText("N/A");
        }else {
            vatNo.setText(disProfList.get(position).getVatno());
        }if(disProfList.get(position).getServiceTaxNo().equalsIgnoreCase("") || disProfList.get(position).getServiceTaxNo().equalsIgnoreCase(null)){
            serviceTaxNo.setText("N/A");
        }else {
            serviceTaxNo.setText(disProfList.get(position).getServiceTaxNo());
        }if(disProfList.get(position).getPhone_no().equalsIgnoreCase("") || disProfList.get(position).getPhone_no().equalsIgnoreCase(null)){
            phoneNO.setText("N/A");
        }else {
            phoneNO.setText(disProfList.get(position).getPhone_no());
        }






//        dialogBuilder.setTitle("Select Any one");
//        dialogBuilder.setPositiveButton("Send Order", null);
//        dialogBuilder.setNegativeButton("Cancel", null);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(context,DisProfListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                activity.finish();*/
                b.dismiss();
            }
        });

        b.show();


    }

    public class GetDisProfInformation extends AsyncTask<Void,Void,String> {
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
        public GetDisProfInformation(Activity activity,String user_id){
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

                //Log.e("distributor id", "" + distributor_id+"");
                //Log.e("user id", "" + user_id+"");
                //Log.e("role id", "" + role_id+"");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_User_Info",ServiceHandler.POST,parameters);

               // System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
               // System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonProfInfo=jsonData;

            int position=0;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        JSONObject dataObject=mainObject.getJSONObject("data");
                        JSONObject userObject=dataObject.getJSONObject("User");
                        JSONObject disObject=dataObject.getJSONObject("Distributor");
                        JSONObject addressObject=dataObject.getJSONObject("Address");
                        JSONObject countryObject=addressObject.getJSONObject("Country");
                        JSONObject stateObject=addressObject.getJSONObject("State");
                        JSONObject cityObject=addressObject.getJSONObject("City");
                        JSONObject areaObject=addressObject.getJSONObject("Area");



                        for(int i=0; i<disProfList.size(); i++){
                            if(String.valueOf(disProfList.get(i).getProfessional_id()).equals(userObject.getString("id"))){
                                position=i;

                                disProfList.get(i).setFirstName(userObject.getString("first_name"));
                                disProfList.get(i).setLastName(userObject.getString("last_name"));
                                disProfList.get(i).setPhone_no(userObject.getString("phone_no"));
                                disProfList.get(i).setEmailid(userObject.getString("email_id"));

                                disProfList.get(i).setFirmName(disObject.getString("firm_name"));
                                disProfList.get(i).setCreditLimit(disObject.getString("tally_credit_limit"));
                                disProfList.get(i).setCreditPeriod(disObject.getString("tally_default_credit_period"));
                                disProfList.get(i).setContactPersonName(disObject.getString("tally_contact_person_name"));
                                disProfList.get(i).setTelephoneNo(disObject.getString("tally_telephone"));
                                disProfList.get(i).setPancardno(disObject.getString("tally_pan_no"));
                                disProfList.get(i).setVatno(disObject.getString("tally_vat_tin_no"));
                                disProfList.get(i).setServiceTaxNo(disObject.getString("tally_service_tax_reg_no"));

                                disProfList.get(i).setAddress1(addressObject.getString("address_1"));
                                disProfList.get(i).setAddress2(addressObject.getString("address_2"));
                                disProfList.get(i).setAddress3(addressObject.getString("address_3"));

                                disProfList.get(i).setCountry(countryObject.getString("name"));
                                disProfList.get(i).setState(stateObject.getString("name"));
                                disProfList.get(i).setCity(cityObject.getString("name"));
                                disProfList.get(i).setArea(areaObject.getString("name"));

                                break;
                            }
                        }

//                        for(int i=0; i<dataArray.length(); i++){
                        //JSONObject subObject=dataObject.getJSONObject(jsonData);



                        //prefs.setStatusPR(subObject.getString("status"));
                        //salesDistributor.setPancardno(subObject.getString("pancard_sales"));
                        // salesDistributor.setAltPhone_no(subObject.getString("alternate_no"));
                        //salesDistributor.setAadhaarcardno(subObject.getString("aadhar_card_sales"));
                        // salesDistributor.setPassword(subObject.getString("password"));
                        // }
                        notifyDataSetChanged();
                        //Log.e("Size",disProfList.size()+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
            showDetailInfoDialog(position);
        }
    }

    public void updateData(List<Bean_Dis_Prof> disProfList) {
        this.disProfList=disProfList;
        notifyDataSetChanged();
    }
}
