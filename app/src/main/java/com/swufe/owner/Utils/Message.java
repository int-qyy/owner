package com.swufe.owner.Utils;


import java.util.Random;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.swufe.owner.R;

public class Message {
        private static final String CHANNEL_ID="channel_id";
        public static final String  CHANEL_NAME="chanel_name";


        @TargetApi(Build.VERSION_CODES.O)
        public static void show(Context context){
            NotificationChannel channel = null;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(true);
                channel.setLightColor(Color.GREEN);
                channel.setShowBadge(false);
            }
            Notification notification;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                //向上兼容 用Notification.Builder构造notification对象
                notification = new Notification.Builder(context,CHANNEL_ID)
                        .setContentTitle("别看我了")
                        .setContentText("快点滚去学习!")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.no)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.no))
                        .build();
            }else {
                //向下兼容 用NotificationCompat.Builder构造notification对象
                notification = new NotificationCompat.Builder(context)
                        .setContentTitle("别看我了")
                        .setContentText("快点滚去学习!")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.no)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.no))
                        .build();
            }


            int  notifiId=1;
            //创建一个通知管理器
            NotificationManager   notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(notifiId,notification);

        }



}
