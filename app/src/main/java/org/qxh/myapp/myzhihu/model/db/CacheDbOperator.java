package org.qxh.myapp.myzhihu.model.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/26.
 */
public class CacheDbOperator {
    private static final String TABLE_NEWS = "newscachelist";//要操作的数据表的名称
    private static final String TABLE_ARTICLE = "articlecachelist";//要操作的数据表的名称
    private SQLiteDatabase db = null; //数据库操作

    public CacheDbOperator(SQLiteDatabase db)
    {
        this.db=db;
    }

    public void insertNewsCache(String date, String json)
    {
        String sql = "REPLACE INTO " + TABLE_NEWS + " (DATE,JSON)" +" VALUES(?,?)";
        String args[]=new String[]{date, json};
        this.db.execSQL(sql, args);
        this.db.close();
    }

    public void insertWebCache(String newsId, String json)
    {
        String sql = "REPLACE INTO " + TABLE_ARTICLE + " (NEWSID,JSON)" +" VALUES(?,?)";
        String args[]=new String[]{newsId, json};
        this.db.execSQL(sql, args);
        this.db.close();
    }

    public String findNewsCache(String date)
    {
        String json = null;
        String sql = "SELECT * FROM " + TABLE_NEWS + "WHERE DATE = " + "VALUES(?)";
        String args[]=new String[]{date};
        Cursor result = this.db.rawQuery(sql, args);
        if (result.moveToFirst()) {
            json = result.getString(result.getColumnIndex("json"));
        }
        this.db.close();
        return json;
    }

    public String findWebCache(String newsId)
    {
        String json = null;
        String sql = "SELECT * FROM " + TABLE_ARTICLE + "WHERE NEWSID = " + "VALUES(?)";
        String args[]=new String[]{newsId};
        Cursor result = this.db.rawQuery(sql, args);
        if (result.moveToFirst()) {
            json = result.getString(result.getColumnIndex("json"));
        }
        this.db.close();
        return json;
    }

    public List<String> readAllDateRecordNewsCache(){
        List<String> dates = null;
        String sql = "SELECT DATE FROM " + TABLE_NEWS + "ORDER BY DATE ASC";
        Cursor result = this.db.rawQuery(sql, null);
        if(result.getCount() > 0) {
            dates = new ArrayList<String>();
            for(result.moveToFirst();!result.isAfterLast();result.moveToNext()  )
            {
                dates.add(result.getString(0));
            }
        }
        this.db.close();
        return dates;
    }

}
