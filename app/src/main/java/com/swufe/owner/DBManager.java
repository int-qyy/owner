package com.swufe.owner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    private static final String TAG = "DBManager";

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(VoItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EnString", item.getEnString());
        //values.put("kaString", item.getKaString());
        values.put("ChString",item.getChString());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<VoItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (VoItem item : list) {
            ContentValues values = new ContentValues();
            values.put("EnString", item.getEnString());
            //values.put("kaString", item.getKaString());
            values.put("ChString",item.getChString());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }


    public void delete(String eng){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ENSTRING=?", new String[]{String.valueOf(eng)});
        Log.i(TAG,"have delete");
        db.close();
    }

    public void update(VoItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ENSTRING", item.getEnString());
        //values.put("kaString", item.getKaString());
        values.put("CHSTRING",item.getChString());
        db.update(TBNAME, values, "ENSTRING=?", new String[]{String.valueOf(item.getEnString())});
        db.close();
    }

    public List<VoItem> listAll(){
        List<VoItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<VoItem>();
            while(cursor.moveToNext()){
                VoItem item = new VoItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setEnString(cursor.getString(cursor.getColumnIndex("ENSTRING")));
                //item.setKaString(cursor.getString(cursor.getColumnIndex("kaString")));
                item.setChString(cursor.getString(cursor.getColumnIndex("CHSTRING")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

public int count(){
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    int i=0;
    Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
    if(cursor!=null){
        while(cursor.moveToNext()){
        i++;
        }
        cursor.close();
    }
    db.close();
    return i;
}





    public VoItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        VoItem voItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            voItem = new VoItem();
            voItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            voItem.setEnString(cursor.getString(cursor.getColumnIndex("ENSTRING")));
            //jaItem.setKaString(cursor.getString(cursor.getColumnIndex("kaString")));
            voItem.setChString(cursor.getString(cursor.getColumnIndex("CHSTRING")));
            cursor.close();
        }
        db.close();
        return voItem;
    }
}
