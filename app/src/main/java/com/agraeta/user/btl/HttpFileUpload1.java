package com.agraeta.user.btl;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class HttpFileUpload1 implements Runnable{
    URL connectURL;
    String responseString;
    String Title;
    String ITitle;
    String data_set;
    String first_name;
    String last_name;
    String email;
    String contact_no;
    String city_name;
    String job_id;
    String job_name;
    byte[ ] dataToServer;
    ProgressDialog loadingView;
    FileInputStream fileInputStream = null;
    DataOutputStream dos =null;
    String state_name;

    HttpFileUpload1(String urlString, String vTitle,String s_fname,String s_lname,String s_email,String s_mobile,String position_cityname,String position_state,String jobid,String jobname){
            try{
                connectURL = new URL(urlString);
                Title= vTitle;

                first_name=s_fname;
                last_name=s_lname;
                email=s_email;
                contact_no=s_mobile;
                city_name=position_cityname;

                job_id=jobid;
                job_name=jobname;
                state_name = position_state;
            }catch(Exception ex){
                Log.i("HttpFileUpload11","URL Malformatted");
            }
    }

    void Send_Now(FileInputStream fStream){
            fileInputStream = fStream;
            //Sending();
            new sending_file().execute();
    }

    private class sending_file extends AsyncTask<String, Void, String> {
        
        private ProgressDialog Dialog;
        String response = "";
        @Override
        protected void onPreExecute() {
        	
        }

        @Override
        protected String doInBackground(String... urls) {
              //Get All Route values
                   Sending();
              return response;

        }

        @Override
        protected void onPostExecute(String result) {
        	
        }
  }
    
    void Sending(){

            String pFileName = Title;
        String a_first_name = first_name;
        String a_last_name=last_name;
        String a_email=email;
        String a_contact_no=contact_no;
        String a_city_name=city_name;
        String a_job_id=job_id;
        String a_job_name=job_name;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String Tag="fSnd";
            try
            {
                    //Log.e(Tag,"Starting Http File Sending to URL");

                    // Open a HTTP connection to the URL
                    HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();

                    // Allow Inputs
                    conn.setDoInput(true);

                    // Allow Outputs
                    conn.setDoOutput(true);

                    // Don't use a cached copy.
                    conn.setUseCaches(false);

                    // Use a post method.
                    conn.setRequestMethod("POST");

                    conn.setRequestProperty("Connection", "Keep-Alive");

                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"first_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(first_name);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    //Log.e("111", "111" + first_name);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"last_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(last_name);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                //Log.e("222", "222" + last_name);

                    dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(email);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                //Log.e("333", "333" + email);

                    dos.writeBytes("Content-Disposition: form-data; name=\"contact_no\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(contact_no);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                //Log.e("444", "444" + contact_no);

                    dos.writeBytes("Content-Disposition: form-data; name=\"city_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(city_name);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);


                dos.writeBytes("Content-Disposition: form-data; name=\"state_name\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(state_name);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                //Log.e("555", "555" + city_name);

                    dos.writeBytes("Content-Disposition: form-data; name=\"vacancy_id\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(job_id);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                //Log.e("666", "666" + job_id);

                    dos.writeBytes("Content-Disposition: form-data; name=\"vacancy_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(job_name);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                //Log.e("777", "777" + job_name);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"career_file\";filename=\"" + pFileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                //Log.e("888", "888" +pFileName);



                    //Log.e(Tag,"Headers are written");

                    // create a buffer of maximum size
                    int bytesAvailable = fileInputStream.available();
                        
                    int maxBufferSize = 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[ ] buffer = new byte[bufferSize];

                    // read file and write it into form...
                    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0)
                    {
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable,maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // close streams
                    fileInputStream.close();
                        
                    dos.flush();
                        
                    //Log.e(Tag, "File Sent, Response: " + String.valueOf(conn.getResponseCode()));

                //Log.e("10", "10");
                         
                    InputStream is = conn.getInputStream();
                        
                    // retrieve the response from server
                    int ch;

                    StringBuffer b =new StringBuffer();
                    while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
                    String s=b.toString();
                    Log.i("Response",s);
                    dos.close();
                    
                  
            }
            catch (MalformedURLException ex)
            {
                    //Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            }

            catch (IOException ioe)
            {
                    //Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            }
    }

    @Override
    public void run() {
            // TODO Auto-generated method stub
    }
}
