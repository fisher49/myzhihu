package org.qxh.myapp.myzhihu.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by QXH on 2016/1/24.
 */
public class CacheDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME ="myzhihu.db";
    private static final int  DATABASE_VERSION = 1;

    public CacheDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS newscachelist(id INTEGER primary key autoincrement,date INTEGER unique,json text)");
        db.execSQL("CREATE TABLE IF NOT EXISTS articlecachelist(id INTEGER primary key autoincrement,newsId INTEGER unique,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS newscachelist");
        db.execSQL("DROP TABLE IF EXISTS articlecachelist");
        onCreate(db);
    }
}
