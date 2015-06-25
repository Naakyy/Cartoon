package com.cyy.naak.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyy.naak.activity.ApplicationController;
import com.cyy.naak.beans.VplleyBean;
import com.cyy.naak.fragmentdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by naak on 15/6/15.
 */
public class VplleyAdapter extends BaseAdapter {

    private ArrayList<VplleyBean> vplleyBeans;
    private Activity activity;
    private LayoutInflater inflater;
//    private com.android.volley.toolbox.ImageLoader imageLoader;
    ImageView imageView;
    DisplayImageOptions options;


    private TextView textView1,textView2;

    public VplleyAdapter() {
        super();
    }

    public VplleyAdapter(Activity activity, ArrayList<VplleyBean> vplleyBeans) {


        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(activity)

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

                .cacheOnDisk(true)
                .delayBeforeLoading(100)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .build();//构建完成

        this.vplleyBeans = vplleyBeans;
        this.activity = activity;
    }
    public void update(ArrayList<VplleyBean> vplleyBeans){
        if (this.vplleyBeans == null){
            this.vplleyBeans = vplleyBeans;
        } else {
            this.vplleyBeans.addAll(vplleyBeans);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vplleyBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return vplleyBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.grid_item,null);

//        if (imageLoader == null)
//            imageLoader = ApplicationController.getInstance().getImageLoader();


        imageView = (ImageView)view.findViewById(R.id.image);

        textView2 = (TextView)view.findViewById(R.id.title);

        ImageLoader.getInstance().displayImage(vplleyBeans.get(i).getPic(),imageView,options);
//        vplleyImage.setImageUrl(vplleyBeans.get(i).getPic(),imageLoader);
//        textView1.setText((CharSequence)vplleyBeans.get(i).getCcid());
        textView2.setText((CharSequence)vplleyBeans.get(i).getTitle());

        return view;
    }
}
