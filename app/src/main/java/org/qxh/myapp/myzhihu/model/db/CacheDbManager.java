package org.qxh.myapp.myzhihu.model.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by QXH on 2016/1/26.
 */
public class CacheDbManager {
    private static CacheDbManager instance = null;
    private static CacheDbHelper dbHelper;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;

    private CacheDbManager(){
    }

    public static synchronized void initialize(CacheDbHelper helper){
        if(instance == null){
            instance = new CacheDbManager();
            dbHelper = helper;
        }
    }

    public static synchronized CacheDbManager getInstance(){
        if (instance == null) {
            throw new IllegalStateException(CacheDbManager.class.getSimpleName() +
                    " is not initialized, call initialize(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDadabase(){
        if(mOpenCounter.incrementAndGet() == 1) {
            mDatabase = dbHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDadabase(){
        if(mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

}
