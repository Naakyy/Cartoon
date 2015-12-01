package com.cyy.naak.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by naak on 15/7/8.
 */
public class ComicDBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ComicData.sqlite";
    private static int VERSION = 1;
    private String sql;

    //必须要有构造函数
    public ComicDBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    //当第一次创建数据库的时候，调用该方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "create table collect(ccid integer primary key autoincrement not null,cTitle text not null,eps text not null,pic text not null)";
        db.execSQL(sql);
        sql = "create table historical(ccid integer not null,hTitle text not null,ep text not null,orgUrl text not null,createTime text)";
        db.execSQL(sql);

    }

    //当更新数据库的时候执行该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息
        Log.i(DB_NAME,"updata Database------------>");
    }
}
