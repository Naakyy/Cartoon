package com.cyy.naak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cyy.naak.beans.HistoricalBean;
import com.cyy.naak.fragmentdemo.R;
import com.cyy.naak.untils.TimeUntil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by naak on 15/7/15.
 */
public class HistoricalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HistoricalBean> historicalBeans;
    private LayoutInflater inflater;
    private TextView tvHistoricalTitle,tvHistoricalEp,tvHistorivalDate;

    //调用工具类
    private TimeUntil tu;

    public HistoricalAdapter(Context context,ArrayList<HistoricalBean> historicalBeans) {
        this.context = context;
        this.historicalBeans = historicalBeans;
        this.inflater = LayoutInflater.from(context);
        tu = new TimeUntil();
    }

    @Override
    public int getCount() {
        return historicalBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return historicalBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = inflater.inflate(R.layout.listview_item_historical,null);

        tvHistoricalTitle = (TextView)view.findViewById(R.id.tv_historical_title);
        tvHistoricalEp = (TextView)view.findViewById(R.id.tv_historical_ep);
        tvHistorivalDate = (TextView)view.findViewById(R.id.tv_historical_date);

        tvHistoricalTitle.setText((CharSequence)historicalBeans.get(i).gethTitle());
        tvHistoricalEp.setText("第" + (CharSequence)historicalBeans.get(i).getEp() + "集");

        //获取本地日期
        Date date = new Date();
        tvHistorivalDate.setText((CharSequence)tu.fromDate2YYYYMMDDHHMMSS(date));

        return view;
    }

    //适配器的刷新
    public void update(ArrayList<HistoricalBean> historicalBeans){
        if (this.historicalBeans == null){
            this.historicalBeans = historicalBeans;
        } else {
            //清理一下
            this.historicalBeans.clear();
        }
        notifyDataSetChanged();
    }
}
