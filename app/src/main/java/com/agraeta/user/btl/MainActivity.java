package com.agraeta.user.btl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.agraeta.user.btl.CompanySalesPerson.UserTypeActivity;
import com.agraeta.user.btl.DisSalesPerson.SalesTypeActivity;
import com.agraeta.user.btl.admin.AdminDashboard;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    AppPrefs prefs;

    String currentVersion="1.0";

    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs=new AppPrefs(getApplicationContext());

        try {
            Process process = new ProcessBuilder()
                    .command("logcat", "-c")
                    .redirectErrorStream(true)
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("Locale", Locale.getDefault().getCountry());

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            currentVersion="1.0";
            e.printStackTrace();
        }

        Log.e("Current Version","-->"+currentVersion);

        new GetVersionCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        new CountDownTimer(2500, 1300) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                if(prefs.getUserRoleId().equals(C.COMP_SALES_PERSON))
                {
                    if(prefs.getCurrentPage().equalsIgnoreCase("MainPageDrawer"))
                    {
                        Intent iGo = new Intent(MainActivity.this,
                                MainPage_drawer.class);
                        iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iGo);
                        finish();
                    }
                    else
                    {
                        Intent iGo = new Intent(MainActivity.this,
                                UserTypeActivity.class);
                        iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iGo);
                        finish();
                    }

                }

               else if(prefs.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON))
                {
                    if(prefs.getCurrentPage().equalsIgnoreCase("MainPageDrawer"))
                    {
                        Intent iGo = new Intent(MainActivity.this,
                                MainPage_drawer.class);
                        iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iGo);
                        finish();
                    }
                    else
                    {
                        Intent iGo = new Intent(MainActivity.this,
                                SalesTypeActivity.class);
                        iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iGo);
                        finish();
                    }

                }
                else if(prefs.getUserRoleId().equals(C.ADMIN)){
                    Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent iGo = new Intent(MainActivity.this,
                            MainPage_drawer.class);
                    iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iGo);
                    finish();
                }




            }


        }.start();

    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {

                }
            }
            Log.e("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }
}
