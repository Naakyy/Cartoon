package com.cyy.naak.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.beans.HomeBean;

import java.util.ArrayList;

/**
 * Created by naak on 15/7/9.
 */
public class CollectDAO {

    private ComicDBOpenHelper dbHelper;

    public CollectDAO(Context context){
        dbHelper = new ComicDBOpenHelper(context);
    }

    //将数据收藏于收藏夹
    public boolean addCollect(CollectBean collectBean){

        boolean isSuccess = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("ccid",collectBean.getCcid());
            values.put("cTitle",collectBean.getcTitle());
            values.put("eps",collectBean.getEps());
            values.put("pic",collectBean.getPic());
            db.insert("collect",null,values);
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return isSuccess;
    }

    //通过ccid来删除收藏号的数据
    public boolean deleteCollectByItemID(int ccid){

        boolean isSuccess = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete("collect","ccid = ?",new String[]{"" + ccid});
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return isSuccess;
    }

    /**
     * ccid
     * @param ccid
     * @return 如果有则返回Favorite对象，没有则返回null
     */
    public CollectBean findCollectByItemID(int ccid){
        CollectBean cb= null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            //游标
            Cursor c = db.rawQuery("select * from collect where ccid = ?",new String[]{"" + ccid});
            if (c.moveToNext()){
                cb = new CollectBean();
                cb.ccid = c.getInt(c.getColumnIndex("ccid"));
                cb.cTitle = c.getString(c.getColumnIndex("cTitle"));
                cb.eps = c.getString(c.getColumnIndex("eps"));
                cb.pic = c.getString(c.getColumnIndex("pic"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return cb;
    }

    //查询所添加到收藏夹的数据
    public ArrayList<CollectBean> findAll(){
        ArrayList<CollectBean> collectList = new ArrayList<CollectBean>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor c = db.rawQuery("select * from collect",null);
            while (c.moveToNext()){
                CollectBean collectBean = new CollectBean();
                collectBean.ccid = c.getInt(c.getColumnIndex("ccid"));
                collectBean.cTitle = c.getString(c.getColumnIndex("cTitle"));
                collectBean.eps = c.getString(c.getColumnIndex("eps"));
                collectBean.pic  = c.getString(c.getColumnIndex("pic"));

                collectList.add(collectBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return collectList;
    }
}
