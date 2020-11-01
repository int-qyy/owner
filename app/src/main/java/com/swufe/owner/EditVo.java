package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

}