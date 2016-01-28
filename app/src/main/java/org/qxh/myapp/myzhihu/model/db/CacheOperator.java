package org.qxh.myapp.myzhihu.model.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/28.
 */
public class CacheOperator {
    private static final String TABLE_NEWS = "newscachelist";//要操作的数据表的名称
    private static final String TABLE_ARTICLE = "articlecachelist";//要操作的数据表的名称

    private CacheDbManager manager;

    public CacheOperator() {
        manager = CacheDbManager.getInstance();
    }

    public void insertNewsCache(String date, String json){
        SQLiteDatabase db = manager.openDadabase();
        String sql = "REPLACE INTO " + TABLE_NEWS + " (DATE,JSON)" +" VALUES(?,?)";
        String args[]=new String[]{date, json};
        db.execSQL(sql, args);
        manager.closeDadabase();
    }

    public void insertWebCache(String newsId, String json){
        SQLiteDatabase db = manager.openDadabase();
        String sql = "REPLACE INTO " + TABLE_ARTICLE + " (NEWSID,JSON)" +" VALUES(?,?)";
        String args[]=new String[]{newsId, json};
        db.execSQL(sql, args);
        manager.closeDadabase();
    }

    public String findNewsCache(String date)
    {
        SQLiteDatabase db = manager.openDadabase();
        String json = null;
        String sql = "SELECT * FROM " + TABLE_NEWS + " WHERE DATE = " + date;
//        String args[]=new String[]{date};
        Cursor result = db.rawQuery(sql, null);
        if (result.moveToFirst()) {
            json = result.getString(result.getColumnIndex("json"));
        }
        manager.closeDadabase();
        return json;
    }

    public String findWebCache(String newsId){
        SQLiteDatabase db = manager.openDadabase();
        String json = null;
        String sql = "SELECT * FROM " + TABLE_ARTICLE + " WHERE NEWSID = " + newsId;
//        String args[]=new String[]{newsId};
        Cursor result = db.rawQuery(sql, null);
        if (result.moveToFirst()) {
            json = result.getString(result.getColumnIndex("json"));
        }
        manager.closeDadabase();
        return json;
    }

    public List<String> readAllDateRecordNewsCache(){
        SQLiteDatabase db = manager.openDadabase();
        List<String> dates = null;
        String sql = "SELECT DATE FROM " + TABLE_NEWS + " ORDER BY DATE ASC";
        Cursor result = db.rawQuery(sql, null);
        if(result.getCount() > 0) {
            dates = new ArrayList<String>();
            for(result.moveToFirst();!result.isAfterLast();result.moveToNext()  )
            {
                dates.add(result.getString(0));
            }
        }
        manager.closeDadabase();
        return dates;
    }
}
