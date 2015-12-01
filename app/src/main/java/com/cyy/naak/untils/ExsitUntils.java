package com.cyy.naak.untils;

import android.content.Context;

import com.cyy.naak.db.HistoricalDAO;

/**
 * Created by naak on 15/7/21.
 */
public class ExsitUntils {

    HistoricalDAO dao;

    public boolean isExsitInDB(Context mc,int id){
        dao = new HistoricalDAO(mc);
        if(dao.findHistoricalByItemID(id) == null){
            return true;
        }else{

            return false;
        }
    }

}
