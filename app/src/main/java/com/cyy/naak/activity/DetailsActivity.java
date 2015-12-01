package com.cyy.naak.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cyy.naak.adapter.DetailAdapter;
import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.beans.DetailBean;
import com.cyy.naak.beans.HomeBean;
import com.cyy.naak.db.CollectDAO;
import com.cyy.naak.fragmentdemo.R;
import com.cyy.naak.untils.ApplicationController;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsActivity extends Activity {

    private ImageView detailImage;
    private ImageView btBack;
    private ImageView btGetContent;

    private CheckBox cbDelCollect;
    private CollectDAO dao;

    private TextView tvTitleDetail,tvType,tvAuthor,tvCount,tvContent;
    private GridView gvDetail;

    private ArrayList<DetailBean> detailData = new ArrayList<>();
    private DetailAdapter detailAdapter;

    private boolean isItemAvailable = true;
    private int ccid;
    private String picture,count,title,url;
    String url1 = "http://ch4.kiomon.com/comic_v0/detail/";
    String url2 = "/30/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //用Intent获取所传递数据
        Intent intent= this.getIntent();
        Bundle bundle = intent.getExtras();
        ccid = bundle.getInt("ccid");
        picture = bundle.getString("pic");//获取图片数据
        count = bundle.getString("eps");//获取总集数
        title = bundle.getString("title");
        url = url1 + ccid + url2;

        tvTitleDetail = (TextView)findViewById(R.id.titleDetail_bar);
        btBack = (ImageView)findViewById(R.id.back);
        cbDelCollect = (CheckBox)findViewById(R.id.cb_detail_collect);

        dao = new CollectDAO(this);
        CollectBean cb = dao.findCollectByItemID(ccid);
        cbDelCollect.setChecked(cb != null);

        cbDelCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isItemAvailable){
                   Toast.makeText(DetailsActivity.this,"你还没有收藏哦",Toast.LENGTH_LONG).show();
                    return;
                }

                if (isChecked){
                    //加入收藏
                    CollectBean collectBean = new CollectBean();
                    collectBean.setCcid(ccid);
                    collectBean.setcTitle(title);
                    collectBean.setEps(count);
                    collectBean.setPic(picture);
                    //储存数据
                    dao.addCollect(collectBean);
                    Toast.makeText(DetailsActivity.this,"已经收藏",Toast.LENGTH_LONG).show();
                }else{
                    //取消收藏
                    dao.deleteCollectByItemID(ccid);
                    Toast.makeText(DetailsActivity.this,"取消收藏",Toast.LENGTH_LONG).show();
                }
            }
        });

        detailImage = (ImageView)findViewById(R.id.imagedetail);
        tvType = (TextView)findViewById(R.id.type);
        tvAuthor = (TextView)findViewById(R.id.author);
        tvCount = (TextView)findViewById(R.id.count);//总集数
        gvDetail = (GridView)findViewById(R.id.gridview_detail);
        tvContent = (TextView)findViewById(R.id.content);//剧情介绍的内容
        btGetContent = (ImageView)findViewById(R.id.getContent); //隐藏or显示内容的按钮
        btGetContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvContent.isShown()){
                    tvContent.setVisibility(View.GONE);
                }else{
                    tvContent.setVisibility(View.VISIBLE);
               }
            }
        });

        ImageLoader.getInstance().displayImage(picture,detailImage);
        tvCount.setText(count);
        tvTitleDetail.setText(title);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();//返回当前页面
            }
        });

        getData(url);
    }

    //解析详情页面的数据
    private void getData(String url){
        StringRequest stringRequest = new StringRequest(url,new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonString) {
                try {
                    //解析详情页面的数据对象(用JSONObject即可，并不需要单独写出Bean)
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String titleType = jsonObject.getString("type");//动漫类型
                    String author = jsonObject.getString("actors");//作者
                    String juQing = jsonObject.getString("desc");//剧情介绍的剧情

                    tvType.setText(titleType);//设置 动漫类型
                    tvAuthor.setText(author); //设置 作者
                    tvContent.setText(juQing);//设置 剧情介绍的剧情

                    //解析详情页面的数据数组[](用JSONArray和JSONObject 且 需要for循环，需要单独写出Bean)
                    JSONObject epsObject = new JSONObject(jsonObject.getString("eps"));
                    JSONArray one = new JSONArray(epsObject.getString("1"));
                    for (int i = 0; i<one.length();i++){

                        JSONObject json = one.getJSONObject(i);
                        DetailBean vb = new DetailBean();
                        vb.setEp(json.getString("ep"));//第几集
                        vb.setEp_title(json.getString("ep_title"));//第几集的名称
                        vb.setOrg_url(json.getString("org_url")); //播放地址

                        //新增 历史界面上显示的数据
                        vb.ccid = ccid;
                        vb.title = title;

                        detailData.add(vb);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
               }

                //将详情页的GridView设置到相应的适配器（自己写需要的适配器）中
                detailAdapter = new DetailAdapter(DetailsActivity.this,detailData);
                gvDetail.setAdapter(detailAdapter);

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }
}
