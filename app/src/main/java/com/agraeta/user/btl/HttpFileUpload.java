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

public class HttpFileUpload implements Runnable{
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
    String product_name;
    String reason;

    String u_fname;
    String u_lname;
    String u_email;
    String u_mobile;
    String u_curFileName;
    String u_productname;
    String u_statename;
    String u_cityname;
    String u_purchsebillno;
    String u_date;
    String u_dname;
    String u_dadd ;
    String u_dstatename;
    String u_dcityname;
    String u_complain;
    String u_invoice;
    String u_wa;



    byte[ ] dataToServer;
    ProgressDialog loadingView;
    FileInputStream fileInputStream = null;
    DataOutputStream dos =null;

    HttpFileUpload(String urlString,String s_fname,String s_lname,String s_email,String s_mobile,String curFileName,String productname,String statename,String cityname,String s_invoice,String s_complain,String wa){
            try{
                    connectURL = new URL(urlString);
                u_fname=s_fname;
                u_lname=s_lname;
                u_email=s_email;
                u_mobile=s_mobile;
                u_curFileName=curFileName;
                 u_productname=productname;
                 u_statename=statename;
                 u_cityname=cityname;
                u_invoice=s_invoice;
                 u_complain=s_complain;
                u_wa =wa;

            }catch(Exception ex){
                Log.i("HttpFileUpload","URL Malformatted");
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
        String  a_fname=u_fname;
        String a_lname=u_lname;
        String a_email=u_email;
        String a_mobile=u_mobile;
        String a_curFileName=u_curFileName;
        String a_productname=u_productname;
        String  a_statename=u_statename;
        String a_cityname=u_cityname;
        String a_purchsebillno=u_purchsebillno;
        String a_date=u_date;
        String a_dname=u_dname;
        String a_dadd =u_dadd;
        String a_dstatename=u_dstatename;
        String a_dcityname=u_dcityname;
        String a_complain=u_complain;

        String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String Tag="fSnd";
            try
            {
                    ////Log.e(Tag,"Starting Http File Sending to URL");

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
                    dos.writeBytes(u_fname);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    ////Log.e("111", "111" + u_fname);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"last_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(u_lname);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("222", "222" + u_lname);

                    dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(u_email);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                ////Log.e("333", "333" + u_email);

                    dos.writeBytes("Content-Disposition: form-data; name=\"contact_no\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(u_mobile);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("444", "444" + u_mobile);

                    dos.writeBytes("Content-Disposition: form-data; name=\"product_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(u_productname);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("555", "555" + u_productname);

                dos.writeBytes("Content-Disposition: form-data; name=\"state_name\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(u_statename);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("777", "777" + u_statename);


                    dos.writeBytes("Content-Disposition: form-data; name=\"city_name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(u_cityname);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("777", "777" + u_cityname);



                dos.writeBytes("Content-Disposition: form-data; name=\"reason\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(u_complain);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("777", "777" + u_complain);

                dos.writeBytes("Content-Disposition: form-data; name=\"warranty\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(u_wa);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("777", "777" + u_wa);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"upload_photo\";filename=\"" + u_curFileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                ////Log.e("888", "888" + u_curFileName);

                dos.writeBytes("Content-Disposition: form-data; name=\"upload_invoice\";filename=\"" + u_invoice + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
            //   dos.writeBytes(twoHyphens + boundary + lineEnd);
                ////Log.e("888", "888"+u_invoice);
                ////Log.e("999", "999");

                    ////Log.e(Tag,"Headers are written");

                    // create a buffer of maximum size
                int bytesAvailable = 0;
                ////Log.e("fileInputStream",""+fileInputStream.available());
                if(fileInputStream == null){

                }else {
                    bytesAvailable = fileInputStream.available();
                }

                ////Log.e("byteeee",""+bytesAvailable);
                        
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
                        
                    ////Log.e(Tag, "File Sent, Response: " + String.valueOf(conn.getResponseCode()));

                ////Log.e("10", "10");
                         
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
                    ////Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            }

            catch (IOException ioe)
            {
                    ////Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            }
    }

    @Override
    public void run() {
            // TODO Auto-generated method stub
    }
}
