package com.agraeta.user.btl;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FinalCareersActivity extends AppCompatActivity {

    ListView lst;
    Contact_Adapter1.UserHolder holder1 = null;
    AppPrefs app;
    String f_name, l_name, email_id, phone_no, city_name;
    String idv, idname;
    DatabaseHandler db;
    ArrayList<Bean_JobVacancy> category_data1 = new ArrayList<Bean_JobVacancy>();
    Custom_ProgressDialog loadingView;
    String json = new String();
    Contact_Adapter1 cAdapter1;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<String> VacancyId = new ArrayList<String>();
    ArrayList<String> Vacancyname = new ArrayList<String>();
    ArrayList<String> num_vacancy = new ArrayList<String>();
    ArrayList<String> Vacancy_detail = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreers__html);

        app = new AppPrefs(getApplicationContext());
        app.setjobid("");
        app.setjobname("");

        lst = (ListView) findViewById(R.id.lst);
        new get_Vacancies().execute();
        setRefershData();
        setActionBar();
    }

    private void setActionBar() {


        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        TextView txt_skip = (TextView) mCustomView.findViewById(R.id.txt_skip);
        txt_skip.setVisibility(View.GONE);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinalCareersActivity.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);


    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(getApplicationContext());

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            email_id = user_array_from_db.get(i).getEmail_id();
            phone_no = user_array_from_db.get(i).getPhone_no();
            f_name = user_array_from_db.get(i).getF_name();
            l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            String gender = user_array_from_db.get(i).getGender();
            String usertype = user_array_from_db.get(i).getUser_type();
            String login_with = user_array_from_db.get(i).getLogin_with();
            String str_rid = user_array_from_db.get(i).getStr_rid();
            String add1 = user_array_from_db.get(i).getAdd1();
            String add2 = user_array_from_db.get(i).getAdd2();
            String add3 = user_array_from_db.get(i).getAdd3();
            String landmark = user_array_from_db.get(i).getLandmark();
            String pincode = user_array_from_db.get(i).getPincode();
            String state_id = user_array_from_db.get(i).getState_id();
            String state_name = user_array_from_db.get(i).getState_name();
            String city_id = user_array_from_db.get(i).getCity_id();
            city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(uid);
            contact.setUser_id(user_id);
            contact.setEmail_id(email_id);
            contact.setPhone_no(phone_no);
            contact.setF_name(f_name);
            contact.setL_name(l_name);
            contact.setPassword(password);
            contact.setGender(gender);
            contact.setUser_type(usertype);
            contact.setLogin_with(login_with);
            contact.setStr_rid(str_rid);
            contact.setAdd1(add1);
            contact.setAdd2(add2);
            contact.setAdd3(add3);
            contact.setLandmark(landmark);
            contact.setPincode(pincode);
            contact.setState_id(state_id);
            contact.setState_name(state_name);
            contact.setCity_id(city_id);
            contact.setCity_name(city_name);
            contact.setStr_response(str_response);
            user_data.add(contact);


        }
        db.close();
    }

    public class Contact_Adapter1 extends ArrayAdapter<Bean_JobVacancy> {
        Context context;
        int layoutResourceId;
        Bean_JobVacancy user;
        ArrayList<Bean_JobVacancy> data = new ArrayList<Bean_JobVacancy>();

        public Contact_Adapter1(Context context, int layoutResourceId,
                                ArrayList<Bean_JobVacancy> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
            notifyDataSetChanged();
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                row = inflater.inflate(layoutResourceId, parent, false);
                holder1 = new UserHolder();
                holder1.jname = (TextView)
                        row.findViewById(R.id.job_title);
                holder1.j_nojob = (TextView) row
                        .findViewById(R.id.no_job);
                holder1.j_details = (TextView) row
                        .findViewById(R.id.job_details);
                holder1.btn_apply1 = (Button) row
                        .findViewById(R.id.btn_apply);

                row.setTag(holder1);
            } else {
                holder1 = (UserHolder) row.getTag();
            }
            user = data.get(position);

            holder1.jname.setText(user.getJobtitle());
            holder1.j_nojob.setText("(" + user.getNovacancy() + ")");
            holder1.j_details.setText(Html.fromHtml(user.getDesc()));
            //Log.e("Html_code", user.getDesc());
            holder1.jname.setTag(user.getVid());
            holder1.btn_apply1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    app = new AppPrefs(context);
                    idv = holder1.jname.getTag().toString();
                    idname = holder1.jname.getText().toString();
                    //Log.e("data_id", "" + data.get(position).getVid());
                    //Log.e("data_name", "" + data.get(position).getJobtitle());
                    app.setjobid(data.get(position).getVid());
                    app.setjobname(data.get(position).getJobtitle());

                    //Log.e("data_id", "" + data.get(position).getVid());
                    Intent i = new Intent(context, career_activity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                }
            });


            holder1.btn_apply1.setId(position);


            return row;

        }

        class UserHolder {
            TextView jname;
            TextView j_nojob;
            TextView j_details;
            Button btn_apply1;

        }

    }

    public class get_Vacancies extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            category_data1.clear();
            try {
                loadingView = new Custom_ProgressDialog(
                        FinalCareersActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Vacancy/App_GetVacancies", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {

                    //Globals.CustomToast(getApplicationContext(), "SERVER ERRER" , LayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

//                        Globals.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        JSONArray categories = jObj.getJSONArray("data");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);

                         /*   String vId = catObj.getString("id");
                            String vname = catObj.getString("job_title");
                            String num_vacancy1 = catObj.getString("no_of_vacancy");
                            String vdetail = catObj.getString("description");*/


                       /*     Vacancyname.add(vname);
                            VacancyId.add(vId);
                            Vacancy_detail.add(vdetail);
                            num_vacancy.add(num_vacancy1);
*/


                            Bean_JobVacancy beanf = new Bean_JobVacancy();


                            beanf.setVid(catObj.getString("id"));
                            beanf.setJobtitle(catObj.getString("job_title"));
                            beanf.setNovacancy(catObj.getString("no_of_vacancy"));
                            beanf.setDesc(catObj.getString("description"));

                            category_data1.add(beanf);

                            cAdapter1 = new Contact_Adapter1(getApplicationContext(), R.layout.vacancies_row,
                                    category_data1);
                            //Log.e("category_data1",""+category_data1.size());
                            lst.setAdapter(cAdapter1);
                            cAdapter1.notifyDataSetChanged();

                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }


}
