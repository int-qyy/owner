package com.swufe.owner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteList  extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {


    private static final String TAG = "DeleteList";
    private int position;
    ListView listdelete;
    Handler handler;
    ArrayAdapter adapter;
    SimpleAdapter listItemAdapter;
    AlertDialog.Builder builder;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>(); ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_list);
        Intent intent = getIntent();
        listdelete = (ListView)findViewById(R.id.dlist);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i(TAG, "onItemLongClick: 对话框事件处理");

                        /*//ArrayAdapter:
                        adapter.remove(parent.getItemAtPosition(osition));
                        adapter.notifyDataSetChanged()会自动调用*/

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

    @Override
    public void run() {

    }
}