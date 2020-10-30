package com.swufe.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShowList extends AppCompatActivity {

    private static final String TAG = "ShowList";
    SimpleAdapter listItemAdapter;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        ListView list = (ListView)findViewById(R.id.showMylist);
        List<String> retList = new ArrayList<String>();
        DBManager dbManager = new DBManager(ShowList.this);
        for(JaItem jaItem : dbManager.listAll()){
            retList.add(jaItem.getJaString() + "=>" + jaItem.getJaString());
        }

        listItemAdapter = new SimpleAdapter(ShowList.this, listItems,  R.layout.editlist,
                new String[] { "JaString", "kaString","ChString" },new int[] { R.id.Jap, R.id.kan,R.id.Chi} );
        list.setAdapter(listItemAdapter);

    }
}