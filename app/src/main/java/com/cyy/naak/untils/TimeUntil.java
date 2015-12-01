package com.cyy.naak.untils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by naak on 15/7/21.
 */
public class TimeUntil {

    public static String fromDate2YYYYMMDDHHMMSS(Date date){
      try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.format(date);
          }catch (Exception e){
            return null;
          }
    }
}
