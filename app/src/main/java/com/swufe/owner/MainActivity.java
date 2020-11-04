package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.swufe.owner.Utils.Message;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationCompat;
    private static final String TAG="Main";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message.show(MainActivity.this);
    }

    public void addV(View view){
        Intent intent=new Intent(MainActivity.this, AddVo.class);
        startActivity(intent);
    }
    public void testV(View view){
        Intent intent=new Intent(MainActivity.this, TestVo.class);
        startActivity(intent);
    }
    public void showV(View view){
        Intent intent=new Intent(MainActivity.this, DeleteList.class);
        startActivity(intent);
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
                Toast.makeText(MainActivity.this, "你给我回来", Toast.LENGTH_SHORT).show();
                firstTime = nowTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}