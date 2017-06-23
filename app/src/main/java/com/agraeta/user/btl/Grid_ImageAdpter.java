package com.agraeta.user.btl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by chaitalee on 9/6/2016.
 */

public class Grid_ImageAdpter extends BaseAdapter {


    Context context;
    Custom_ProgressDialog loadingView;
    ImageLoader imageloader;
    LayoutInflater layoutinflater;
    int position1 = 0;
    ImageView im;
    TextView tv;
    Activity activity;
    int display_width;
    FrameLayout framelayout_image;
    ArrayList<Bean_texhnicalImages> array_gallery;
    int click;
    AppPrefs appPrefs;
    ImageView im_share;
    ProgressDialog pDialog;
    final File myDir = new File("/sdcard/BTL/Gallery");
    String filesToSend;
    int selected_position;


    String productCode="";
    String productName="";

    public Grid_ImageAdpter(Context context, ArrayList<Bean_texhnicalImages> array_gallery, LayoutInflater layoutinflater, Activity activity) {

        // TODO Auto-generated constructor stub
        this.array_gallery = array_gallery;
        this.activity = activity;
        this.layoutinflater = layoutinflater;
        this.click = click;
        imageloader = new ImageLoader(context);
        appPrefs = new AppPrefs(activity);
    }

    public void setCodeAndName(String productCode, String productName){
        this.productCode=productCode;
        this.productName=productName;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return array_gallery.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
      //  position1 = position;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);


        display_width = (metrics.widthPixels / 3);
        convertView = layoutinflater.inflate(R.layout.grid_imgg, null);

        framelayout_image = (FrameLayout) convertView
                .findViewById(R.id.framelayout_image);
        im = (ImageView) convertView.findViewById(R.id.imageView1);
        im_share = (ImageView) convertView.findViewById(R.id.im_share);
        framelayout_image.getLayoutParams().height = display_width;
        Log.e("sdasdsadad",""+Globals.server_link+ "files/" +array_gallery.get(position).getImg());
        //framelayout_image.getLayoutParams().height = display_width;



        // im.setImageResource(image_url.get(position));
       // imageloader.DisplayImage(Globals.server_link + "files/" + array_gallery.get(position).getImg(), im);
        Picasso.with(context)
                .load(Globals.server_link+ "files/" +array_gallery.get(position).getImg())
                .placeholder(R.drawable.btl_watermark)
                .into(im);

        //imageloader.DisplayImage(array_gallery.get(position).getImg(), im);

        im_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selected_position = position;
                //Log.e("1111111", ""+position);
                fetch_image_for_share(position);
            }
        });
        im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
             //   appPrefs.setEvent_Gallery_Selected_ID(""+position);
                selected_position = position;
                //Log.e("", "position :- "+position);
                Intent iGo = new Intent(activity,Event_Gallery.class);
                iGo.putExtra("FILES_TO_SEND", array_gallery);
                iGo.putExtra("ID",""+array_gallery.get(selected_position).getId());
                iGo.putExtra("position", selected_position);
                iGo.putExtra("productCode",productCode);
                iGo.putExtra("productName",productName);
                iGo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(iGo);
            }
        });


        return convertView;
    }


    private void fetch_image_for_share(int index)
    {
        //Log.e("22222",""+index);
        new DownloadFileFromURL().execute(""+index);

    }


   public class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);
            //Log.e("3333","100");
           /* loadingView = new Custom_ProgressDialog(
                    activity, "");

            loadingView.setCancelable(false);
            loadingView.show();*/

            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Please Wait Downloading Image");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();


        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... i) {

            try {


                //pDialog.setMessage("Please Wait Downloading Image");
                File temp_dir = new File (myDir+"/"+ array_gallery.get(selected_position).getId()+".png");
                if(temp_dir.exists())
                {
                    filesToSend = myDir+"/"+ array_gallery.get(selected_position).getId()+".png";
                }
                else
                {
                    File dir = new File (myDir + "");
                    if(!dir.exists()) {
                        dir.mkdirs();
                    }

                    //Log.e("4444444",""+Globals.server_link + "files/" + array_gallery.get(selected_position).getImg());
                    URL url = new URL(Globals.server_link + "files/" + array_gallery.get(selected_position).getImg());
                    //Log.e("FILE_NAME", "File name is " + array_gallery.get(selected_position).getId()+".png");
                    //Log.e("FILE_URLLINK", "File URL is "+Globals.server_link + "files/" + array_gallery.get(selected_position).getImg());
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(dir+"/" + array_gallery.get(selected_position).getId()+".png"));

                    filesToSend = dir+"/"+ array_gallery.get(selected_position).getId()+".png";
                    //Log.e("", "File to send :- "+dir+"/" + array_gallery.get(selected_position).getId()+".png");
                    byte data[] = new byte[1024];
                    long total = 0;
                    //int count;


                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //  publishProgress((int) ((total * 100)/fileLength));
                        long total1 = (total * 100)/fileLength;
                        publishProgress(total1+"");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

            }
            catch (Exception e) {
                //Log.e("Error: ", ""+e);
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
       /* protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }*/

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //activity.dismissDialog(0);
            pDialog.dismiss();

            String message="Item Code : "+productCode+"\n"+"Item Name : "+productName;

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, "" + productCode + "("+productName+")");
            intent.putExtra(Intent.EXTRA_TEXT,"Item Code : " + "(" + productCode + ")" + "\nItem Name : " + productName);
            intent.setType("image/*");  //This example is sharing jpeg images.

            ArrayList<Uri> files = new ArrayList<Uri>();


            File file = new File(filesToSend);
            Uri uri = Uri.fromFile(file);
            files.add(uri);


            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(intent);


            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            // pDialog.dismiss();
            // setting downloaded into image view
            // my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        }

        /*public void execute(String s) {
        }*/
    }
}
