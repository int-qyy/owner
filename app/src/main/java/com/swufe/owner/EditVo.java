package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swufe.owner.DB.DBManager;
import com.swufe.owner.DB.VoItem;

public class EditVo extends AppCompatActivity {
    EditText ChString;
    TextView EnString;
    String english,chinese;
    private static final String TAG="EditVo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        english = bundle.getString("EnString","");
        chinese = bundle.getString("ChString","");
        Log.i(TAG,"onItemClick: update english=" +english );
        Log.i(TAG,"onItemClick: update chinese=" +chinese);
        EnString=(TextView) findViewById(R.id.EdEn);
        ChString=(EditText) findViewById(R.id.EdCh);
        EnString.setText(english);
        ChString.setText(chinese);
    }

    public void returnM(View view){
        Intent intent=new Intent(EditVo.this, MainActivity.class);
        startActivity(intent);
    }

    public void submit(View view){

        DBManager dbManager = new DBManager(EditVo.this);
        String chinese=ChString.getText().toString();
        if(chinese == null || chinese.equals("") || chinese.equals(R.string.Ch)){
            Toast.makeText(this, "请输入汉语", Toast.LENGTH_SHORT).show();
        }else{
            VoItem voItem = new VoItem(english, chinese);
            dbManager.update(voItem);
            Log.i("db", "修改"+english+chinese);
            Intent intent = new Intent(EditVo.this, DeleteList.class);
            startActivity(intent);
        }
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
                Toast.makeText(EditVo.this, "你给我回来" , Toast.LENGTH_SHORT).show();
                firstTime = nowTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}