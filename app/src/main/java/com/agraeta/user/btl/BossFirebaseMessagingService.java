package com.agraeta.user.btl;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.agraeta.user.btl.utils.BadgeUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;


/**
 * Created by Nivida new on 17-Sep-16.
 */
public class BossFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        //Log.e(TAG, "From: " + remoteMessage.getFrom());
        //Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "DATA Message Body: " + remoteMessage.getData().toString());

        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm dd/MMM", Locale.getDefault());

        AppPrefs prefs=new AppPrefs(getApplicationContext());
        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
        /*Bean_Notification notification=new Bean_Notification();
        notification.setTitle(remoteMessage.getData().get("title"));
        notification.setMessage(remoteMessage.getData().get("message"));
        notification.setTime(sdf.format(new Date()));
        //Log.e("time of notification", notification.getTime());
        db.AddNotification(notification,remoteMessage.getData().get("user_id"));*/

        Log.e("Data Msg", remoteMessage.getData().toString());

        boolean isForLogout=false;

        if(remoteMessage.getData().toString().contains("status=") && remoteMessage.getData().get("status").equals("false")){
            isForLogout=true;
            String user_id=remoteMessage.getData().get("user_id");

            /*if(prefs.getUserId().equalsIgnoreCase(user_id)){
                prefs.setUserId("");
                prefs.setUser_LoginInfo("");
                prefs.setUser_PersonalInfo("");
                prefs.setUser_SocialIdInfo("");
                prefs.setUser_SocialFirst("");
                prefs.setUser_Sociallast("");
                prefs.setUser_Socialemail("");
                prefs.setUser_SocialReInfo("");
                prefs.setUserRoleId("");
                db.Delete_user_table();
                db.Clear_ALL_table();
            }*/
        }

        String user_id=remoteMessage.getData().get("user_id");

        if (remoteMessage.getData().get("message").contains("OTP")) {
            notifyUser(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
            int totalCount = prefs.getNoticount();
            totalCount++;
            prefs.setNoticount(totalCount);
            BadgeUtils.setBadge(getApplicationContext(), totalCount);
        } else if (prefs.getUserId().equalsIgnoreCase(user_id) && !isForLogout) {
            notifyUser(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
            int totalCount = prefs.getNoticount();
            totalCount++;
            prefs.setNoticount(totalCount);
            BadgeUtils.setBadge(getApplicationContext(), totalCount);
        }


    }



    //This method is only generating push notification
    //It is same as we did in earlier posts

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notifyUser(String title, String message){
        int notificationID=new Random(2).nextInt();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, SingleNotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("directNotification", true);
        intent.putExtra("directTitle", title);
        intent.putExtra("directMessage", message);
        intent.putExtra("notiID",notificationID);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.btl_action);
        Notification mNotification = new Notification.Builder(this)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(message))
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentText(message)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.btl_noti_1)
                .setContentIntent(pIntent)
                .setSound(soundUri)
                .setAutoCancel(true)
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
        mNotification.flags |= Notification.FLAG_SHOW_LIGHTS;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, mNotification);
    }
}