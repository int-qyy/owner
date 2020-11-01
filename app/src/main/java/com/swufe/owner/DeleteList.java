package com.swufe.owner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteList  extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {


    private static final String TAG = "DeleteList";
    private int position;
    ListView listdelete;
    Handler handler;
    ArrayAdapter adapter;
    SimpleAdapter listItemAdapter;
    AlertDialog.Builder builder;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>(); ;
    List<String> retList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_list);

        Intent intent = getIntent();
        listdelete = (ListView)findViewById(R.id.dlist);
        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    listItems = (ArrayList<HashMap<String, String>>) msg.obj;
                    Log.i(TAG,"listItems"+listItems);
                    listItemAdapter = new SimpleAdapter(DeleteList.this, listItems,  R.layout.editlist,
                            new String[] { "EnString", "ChString" },new int[] { R.id.Eng,R.id.Chi} );
                    listdelete.setAdapter(listItemAdapter);

                    listdelete.setOnItemClickListener(DeleteList.this);//添加事件监听
                    listdelete.setOnItemLongClickListener(DeleteList.this);//添加长按事件监听
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run()......");
        Message msg = handler.obtainMessage(5);
        List<String> retList = new ArrayList<String>();
        DBManager dbManager = new DBManager(DeleteList.this);

        ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
        List<VoItem> result = dbManager.listAll();
        if(dbManager.listAll()==null){
            Toast.makeText(this, "数据库为空", Toast.LENGTH_SHORT).show();
        }else {
            for (VoItem voItem : dbManager.listAll()) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("EnString", voItem.getEnString());
                map.put("ChString", voItem.getChString());
                list1.add(map);
            }
            msg.obj = list1;
            handler.sendMessage(msg);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//更新
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: parent=" + parent);
        //SimpleItemAdapter:
        //listItems.remove(position);
        //listItemAdapter.notifyDataSetChanged();


        //adapter.remove(parent.getItemAtPosition(position));
        // adapter.notifyDataSetChanged()会自动调用
        Object ip = listdelete.getItemAtPosition(position);
        HashMap<String, String> map = (HashMap<String, String>)ip;
        String ENGLISH = map.get("EnString");
        String CHINESE = map.get("ChString");
        Log.i(TAG, "onItemClick: update english=" + ENGLISH);
        Log.i(TAG, "onItemClick: update chinese=" + CHINESE);
        DBManager dbManager = new DBManager(DeleteList.this);
        Intent config_new = new Intent(this,EditVo.class);
        //传递参数
        Bundle bdl = new Bundle();
        bdl.putString("EnString",ENGLISH);
        bdl.putString("ChString",CHINESE);
        config_new.putExtras(bdl);

        Log.i(TAG,"openOne:update english=" + ENGLISH);
        Log.i(TAG,"openOne:update chinese=" + CHINESE);
        //打开新页面
        startActivity(config_new);





    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {//删除
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onItemLongClick: 对话框事件处理");
                        //数据库删除
                        Object ip = listdelete.getItemAtPosition(position);
                        HashMap<String, String> map = (HashMap<String, String>)ip;
                        String ENGLISH = map.get("EnString");
                        String CHINESE = map.get("ChString");
                        Log.i(TAG, "onItemClick: delete english=" + ENGLISH);
                        Log.i(TAG, "onItemClick: delete chinese=" + CHINESE);
                        DBManager dbManager = new DBManager(DeleteList.this);
                        dbManager.delete(ENGLISH);
                        //SimpleItemAdapter:
                        // 删除数据项
                        listItems.remove(position);
                        // 更新适配器
                        listItemAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("否", null);
        builder.create().show();
        return false;
    }



    public void rem(View view){
        Intent intent=new Intent(DeleteList.this, MainActivity.class);
        startActivity(intent);
    }
}


