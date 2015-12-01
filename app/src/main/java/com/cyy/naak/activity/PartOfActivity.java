package com.cyy.naak.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cyy.naak.adapter.PartOfAdapter;
import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.beans.DetailBean;
import com.cyy.naak.beans.HistoricalBean;
import com.cyy.naak.beans.HomeBean;
import com.cyy.naak.db.CollectDAO;
import com.cyy.naak.db.HistoricalDAO;
import com.cyy.naak.fragmentdemo.R;
import com.cyy.naak.untils.ApplicationController;
import com.cyy.naak.untils.TimeUntil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * @Description: ${todo}<一集分段界面>
 */

public class PartOfActivity extends Activity implements MediaPlayer.OnCompletionListener,MediaPlayer.OnInfoListener{

    //一集中总共分为几段（从0开始）
    private GridView gv;
    private ImageView btBackPartOf;

    private PartOfAdapter partOfAdapter;
    //API地址
    private String urlApi = "http://ch4.kiomon.com/comic_v0/get_url";
    private int ccid;
    //播放地址
    private String orgurl;

    private ArrayList<DetailBean> detailBeans;
    private int positions;
    private HistoricalDAO dao;
    private HistoricalBean hb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_of);

        //数据获取
        detailBeans = (ArrayList<DetailBean>)getIntent().getSerializableExtra("myData");
        positions = getIntent().getIntExtra("pos",0);

        //用Intent获取所传递数据
        Intent intent= this.getIntent();
        Bundle bundle = intent.getExtras();
        ccid = bundle.getInt("ccid");

        gv = (GridView) findViewById(R.id.gridview_partof);
        btBackPartOf = (ImageView)findViewById(R.id.back_partof);
        btBackPartOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dao = new HistoricalDAO(this);
        hb = dao.findHistoricalByItemID(ccid);

        if (positions >= 0){
            orgurl = detailBeans.get(positions).getOrg_url();
        }else {
            orgurl = hb.orgUrl;
        }

        getOrgUrl();
    }

    //利用Volley的post方式发送请求
    private void getOrgUrl() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
//                        Log.i("sss=s=s=s=s",s);
                        List<String> list = new ArrayList<String>();
                        try {

                            JSONArray jsonArray = new JSONArray(s);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                list.add(jsonArray.getString(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        partOfAdapter = new PartOfAdapter(PartOfActivity.this,list,detailBeans,positions);//新添加的数据传入
                        gv.setAdapter(partOfAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }}) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //放orgUrl的播放地址
                Map<String, String> map = new HashMap<String, String>();
                map.put("url", orgurl);

                return map;
            }};
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {}
}
