package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddVo extends AppCompatActivity {

    EditText JaString,kaString,ChString;
    private static final String TAG="addVo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vo);
        JaString=(EditText)findViewById(R.id.Ja);
        kaString=(EditText)findViewById(R.id.ka);
        ChString=(EditText)findViewById(R.id.Ch);


    }
    public void commit(View view){
        List<String> retList = new ArrayList<String>();
        DBManager dbManager = new DBManager(AddVo.this);
        String ja=JaString.getText().toString();
        String ka=kaString.getText().toString();
        String ch=ChString.getText().toString();
        JaItem rateItem = new JaItem(ja,ka,ch);
        dbManager.add(rateItem);
        Log.i("db","添加新记录集");
    }

}