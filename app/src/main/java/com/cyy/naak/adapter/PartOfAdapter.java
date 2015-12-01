package com.cyy.naak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyy.naak.activity.PartOfActivity;
import com.cyy.naak.activity.VedioActivity;
import com.cyy.naak.beans.DetailBean;
import com.cyy.naak.beans.HistoricalBean;
import com.cyy.naak.beans.HomeBean;
import com.cyy.naak.db.HistoricalDAO;
import com.cyy.naak.fragmentdemo.R;
import com.cyy.naak.untils.ExsitUntils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naak on 15/7/2.
 */
public class PartOfAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<String>();
    private LayoutInflater inflater;
    private Context context;
    private ImageView ivPart;
    private TextView tvPartTitle;

    private HistoricalDAO dao;
    private ArrayList<DetailBean> detailBeans;
    private int pos;
    private ExsitUntils eu;

    public PartOfAdapter(Context context, List<String> list,ArrayList<DetailBean> detailBeans,int pos) {

        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.detailBeans = detailBeans;
        this.pos = pos;
        dao = new HistoricalDAO(context);
        eu = new ExsitUntils();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.grid_item_partof,null);

        ivPart = (ImageView)view.findViewById(R.id.iv_part);
        tvPartTitle = (TextView)view.findViewById(R.id.title_part);
        tvPartTitle.setText("part"+(i+1));

        //在Part界面的GridView上图片设置点击事件
        ivPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去除视频的分段大小
                final String[] stringArray = list.toArray(new String[list.size()]);
                Intent intent = new Intent().setClass(context, VedioActivity.class);
                // 获取当前需要播放的url数据
                Bundle bundle = new Bundle();
                bundle.putString("url",list.get(i));
                //数组数据的传送
                bundle.putStringArray("stringArray",stringArray);
                bundle.putInt("position",i);
                intent.putExtras(bundle);

                if (pos >= 0){
                    //将历史记录的数据进行存储
                    HistoricalBean dbean = new HistoricalBean();
                    dbean.hTitle = detailBeans.get(pos).title;
                    dbean.ccid = detailBeans.get(pos).ccid;
                    dbean.setEp(detailBeans.get(pos).getEp());
                    dbean.orgUrl = detailBeans.get(pos).getOrg_url();

                    //判断是否为当前的ID后再进行清空历史纪录
                    if (!eu.isExsitInDB(context,dbean.ccid)){
                        dao.deleteHistoricalByItemID(dbean.ccid);
                    }
                    dao.addHistorical(dbean);
                }
                context.startActivity(intent);
            }
        });
        return view;
    }
}
