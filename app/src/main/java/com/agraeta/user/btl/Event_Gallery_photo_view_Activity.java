package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Event_Gallery_photo_view_Activity extends Activity {

	ViewPager mViewPager;
	CustomPagerAdapter mCustomPagerAdapter;
	ArrayList<Bean_ProductImage> array_image = new ArrayList<Bean_ProductImage>();
	ArrayList<String> array_image_id = new ArrayList<String>();
	DatabaseHandler db;
	AppPrefs appPrefs;
	String pid;
    ImageView img_share;

    ArrayList<Bean_Product> productList=new ArrayList<>();

    String ImgProcode="";
    String ImgProName="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event__gallery_photo_view);

		Intent intent=getIntent();
		int position=intent.getIntExtra("position",0);
        ImgProcode=intent.getStringExtra("imgProCode");
        ImgProName=intent.getStringExtra("imgProName");

        appPrefs= new AppPrefs(Event_Gallery_photo_view_Activity.this);
		pid=appPrefs.getproduct_id();
        //ArrayList<Bean_ProductImage> myList = (ArrayList<Bean_ProductImage>) getIntent().getSerializableExtra("mylist");
        //array_image = getIntent().getParcelableExtra("mylist");
		db = new DatabaseHandler(Event_Gallery_photo_view_Activity.this);
		array_image = db.Get_ProductImage();
        productList = db.Get_Product();

        /*for (Bean_Product productItem : productList ){
            ImgProcode=productItem.getPro_code();
            ImgProName=productItem.getPro_name();
        }*/

        img_share=(ImageView) findViewById(R.id.img_share);

        //Log.e("Size of Images",array_image.size()+"");

        final Gallery gallery=(Gallery) findViewById(R.id.gallery);
        gallery.setSpacing(4);

        //ViewGroup.MarginLayoutParams mlp=(ViewGroup.MarginLayoutParams) gallery.getLayoutParams();

        //gallery.setGravity(Gravity.START);
        //mlp.setMargins(-200, 0, 0, 0);

		//Log.e("bean_productImages_size",""+array_image.size());
		if (array_image.size() != 0) {

			for (int i = 0; i < array_image.size(); i++) {

				//Log.e("pid", "" + array_image.get(i).getPro_id() + ":" + pid);
				if (array_image.get(i).getPro_id().equalsIgnoreCase(pid)) {
					//Log.e("bean_productImages_loop", "" + i);
					array_image_id.add(array_image.get(i).getPro_Images());
				}
			}
		}
		mCustomPagerAdapter = new CustomPagerAdapter(this,array_image_id,Event_Gallery_photo_view_Activity.this);
        mCustomPagerAdapter.setCodeAndName(ImgProcode,ImgProName);
        final ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.pager_gallery);

        mViewPager.setAdapter(mCustomPagerAdapter);
        //Log.e("position selected",""+position);
        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int mCurrentFragmentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                boolean isGoingToRightPage = position == mCurrentFragmentPosition;
                if(isGoingToRightPage)
                {
                    // user is going to the right page
                }
                else
                {
                    // user is going to the left page
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentFragmentPosition = position;
                gallery.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        GalleryImageAdapter galleryImageAdapter=new GalleryImageAdapter(this,array_image_id);
        gallery.setAdapter(galleryImageAdapter);
        gallery.setSelection(position);


        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewPager.setCurrentItem(position);
                gallery.setSelection(position);
                //Log.e("selected pos",position+"");
                //Log.e("Gallery pos",""+ gallery.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        img_share.setVisibility(View.GONE);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentPosition=mViewPager.getCurrentItem();

                ImageView img_selected=(ImageView) gallery.getSelectedView();
                img_selected.setDrawingCacheEnabled(true);
                img_selected.destroyDrawingCache();
                img_selected.buildDrawingCache();
                Bitmap bitmap=img_selected.getDrawingCache();
                String filePath="";
                try
                {

                    File rootFile=new File(Environment.getExternalStorageDirectory()+"/BTL/Gallery");
                    if(rootFile.exists()) rootFile.delete();
                    rootFile.mkdir();
                    File imagePath=new File(rootFile+File.separator+ImgProcode+"_"+ImgProName+"_"+currentPosition+".jpg");
                    if(imagePath.exists()) imagePath.delete();
                    imagePath.createNewFile();
                    //fileName = filePath+File.separator+fileName;
                    OutputStream ostream = new FileOutputStream(imagePath);
                    filePath=imagePath.getAbsolutePath();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.flush();
                    ostream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //Log.e("File Path","--> "+filePath);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.setAction(Intent.ACTION_SEND_MULTIPLE);
                share.putExtra(Intent.EXTRA_SUBJECT, ""+ImgProcode);
                share.putExtra(Intent.EXTRA_TEXT, "Item Code : "+"("+ImgProcode+")"+"\nItem Name : "+ImgProName);
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                startActivity(Intent.createChooser(share,"Share via"));
            }
        });
		

        
	}

    @Override
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        //Log.e("System GC","Called");
        super.onResume();
    }
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub.
		
		/*Intent iGo = new Intent(Event_Gallery_photo_view_Activity.this,Event_Gallery_Activity.class);
    	iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iGo);*/
		finish();
		
		super.onBackPressed();
	}
}
