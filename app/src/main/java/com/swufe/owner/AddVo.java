package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.swufe.owner.DB.DBManager;
import com.swufe.owner.DB.VoItem;

import java.util.ArrayList;
import java.util.List;

public class AddVo extends AppCompatActivity {

    EditText EnString,ChString;
    private static final String TAG="addVo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vo);
        EnString=(EditText)findViewById(R.id.En);
        ChString=(EditText)findViewById(R.id.Ch);
        Stetho.initializeWithDefaults(this);

    }
    public void submit(View view){
        List<String> retList = new ArrayList<String>();
        DBManager dbManager = new DBManager(AddVo.this);
        String english=EnString.getText().toString();
        String chinese=ChString.getText().toString();
        if(english == null || english.equals("") || english.equals(R.string.En)){//no input
            Toast.makeText(this, "请输入英文单词", Toast.LENGTH_SHORT).show();
        }else if(chinese == null || chinese.equals("") || chinese.equals(R.string.Ch)){
            Toast.makeText(this, "请输入汉语", Toast.LENGTH_SHORT).show();
        }else{
            VoItem voItem = new VoItem(english, chinese);
            dbManager.add(voItem);
            Log.i("db", "添加新词汇"+english+chinese);
            // ((TextView) ChString).setText("");
            //((TextView) EnString).setText("");
            Intent intent = new Intent(AddVo.this, AddVo.class);
            startActivity(intent);
        }
    }
    public void returnM(View view){

        Intent intent=new Intent(AddVo.this, MainActivity.class);
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
                Toast.makeText(AddVo.this, "你给我回来", Toast.LENGTH_SHORT).show();
                firstTime = nowTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }


}