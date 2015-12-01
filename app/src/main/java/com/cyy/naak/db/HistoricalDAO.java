package com.cyy.naak.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cyy.naak.beans.HistoricalBean;

import java.util.ArrayList;

/**
 * Created by naak on 15/7/9.
 */
public class HistoricalDAO {

    private ComicDBOpenHelper dbHelper;

    public HistoricalDAO(Context context){
        dbHelper = new ComicDBOpenHelper(context);
    }

    //添加数据到历史纪录上
    public boolean addHistorical(HistoricalBean historicalBean){

        boolean isSuccess = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("ccid",historicalBean.getCcid());
            values.put("hTitle",historicalBean.gethTitle());
            values.put("ep",historicalBean.getEp());
            values.put("orgUrl",historicalBean.getOrgUrl());
            values.put("createTime",historicalBean.getCreateTime());
            db.insert("historical",null,values);
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return isSuccess;
    }

    //通过ccid来删除数据
    public boolean deleteHistoricalByItemID(int ccid){

        boolean isSuccess = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete("historical","ccid = ?",new String[]{"" + ccid});
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return isSuccess;
    }

    //清空所有历史数据
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("historical",null,null);
        db.close();
    }

    /**
     * ccid
     * @param ccid
     * @return 如果有则返回Favorite对象，没有则返回null
     */
    public HistoricalBean findHistoricalByItemID(int ccid){
        HistoricalBean hb= null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            //游标
            Cursor c = db.rawQuery("select * from historical where ccid = ?",new String[]{"" + ccid});
            if (c.moveToNext()){
                hb = new HistoricalBean();
                hb.ccid = c.getInt(c.getColumnIndex("ccid"));
                hb.hTitle = c.getString(c.getColumnIndex("hTitle"));
                hb.ep = c.getString(c.getColumnIndex("ep"));
                hb.orgUrl = c.getString(c.getColumnIndex("orgUrl"));
                hb.createTime = c.getString(c.getColumnIndex("createTime"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return hb;
    }

    //查询历史纪录的所有数据
    public ArrayList<HistoricalBean> findAll(){
        ArrayList<HistoricalBean> historicalList = new ArrayList<HistoricalBean>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor c = db.rawQuery("select * from historical",null);
            while (c.moveToNext()){
                HistoricalBean historicalBean = new HistoricalBean();
                historicalBean.ccid = c.getInt(c.getColumnIndex("ccid"));
                historicalBean.hTitle = c.getString(c.getColumnIndex("hTitle"));
                historicalBean.ep = c.getString(c.getColumnIndex("ep"));
                historicalBean.createTime = c.getString(c.getColumnIndex("createTime"));

                historicalList.add(historicalBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return historicalList;
    }
}
