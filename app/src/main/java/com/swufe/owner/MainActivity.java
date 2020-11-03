package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationCompat;
    private static final String TAG="Main";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //context="快点滚去学习";
        //Message.show(context);

    }

    public void addV(View view){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("标题")
                        .setContentText("信息").build();
        notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
        Intent intentt=new Intent(MainActivity.this, AddVo.class);
        startActivity(intentt);
    }
    public void testV(View view){
        Intent intent=new Intent(MainActivity.this, TestVo.class);
        startActivity(intent);
    }
    public void showV(View view){
        Intent intent=new Intent(MainActivity.this, DeleteList.class);
        startActivity(intent);
    }

    /**
     * 消息提醒
     */
    public void sendBroadCast() {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_EDIT);//类型
        intent.putExtra("topic", "最新消息");
        intent.putExtra("msg", "出来了");
        super.sendBroadcast(intent);
          }


    private long firstTime = 0;
    /**
     * 监听keyUP 实现双击退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "双击退出", Toast.LENGTH_SHORT).show();
                firstTime = nowTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}