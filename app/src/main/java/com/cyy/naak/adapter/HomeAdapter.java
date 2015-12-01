package com.cyy.naak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyy.naak.activity.DetailsActivity;
import com.cyy.naak.beans.DetailBean;
import com.cyy.naak.beans.HomeBean;
import com.cyy.naak.fragmentdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by naak on 15/6/15.
 */
public class HomeAdapter extends BaseAdapter {

    private ArrayList<HomeBean> homeBeans;
    //导入布局
    private LayoutInflater inflater;
    ImageView imageView;
    DisplayImageOptions options;
    Context context;
    private TextView textView2;

    //构造器
    public HomeAdapter(Context context, ArrayList<HomeBean> homeBeans) {

        //图片获取的配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
        .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(200)
                .tasksProcessingOrder(QueueProcessingType.FIFO.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);//全局初始化此配置

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.dianying)
                .showImageForEmptyUri(R.mipmap.dianying)
                .showImageOnFail(R.mipmap.dianying)
                .cacheOnDisk(true)
                .delayBeforeLoading(100)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .build();//构建完成

        this.homeBeans = homeBeans;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    //刷新适配器
    public void update(ArrayList<HomeBean> homeBeans){
        if (this.homeBeans == null){
            this.homeBeans = homeBeans;
        } else {
            this.homeBeans.addAll(homeBeans);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return homeBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return homeBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = inflater.inflate(R.layout.grid_item_main,null);

        imageView = (ImageView)view.findViewById(R.id.image_main);
        textView2 = (TextView)view.findViewById(R.id.title_main);

        ImageLoader.getInstance().displayImage(homeBeans.get(i).getPic(),imageView,options);//uri,imageArea,DisplayImageOptions options
        textView2.setText((CharSequence) homeBeans.get(i).getTitle());

        //GridView上图片设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                //向详情页发送数据
                Bundle bundle = new Bundle();
                bundle.putInt("ccid",homeBeans.get(i).getCcid());//获取首页的id
                bundle.putString("pic", homeBeans.get(i).getPic());//key,url=homeBeans.get(i).getPic()
                bundle.putString("eps",homeBeans.get(i).getEps());
                bundle.putString("title",homeBeans.get(i).getTitle());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }

}
