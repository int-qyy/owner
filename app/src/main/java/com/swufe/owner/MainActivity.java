package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}