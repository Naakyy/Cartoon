package com.cyy.naak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyy.naak.activity.PartOfActivity;
import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.beans.DetailBean;
import com.cyy.naak.beans.HistoricalBean;
import com.cyy.naak.db.CollectDAO;
import com.cyy.naak.fragmentdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by naak on 15/6/29.
 */
public class DetailAdapter extends BaseAdapter {
    private ArrayList<DetailBean> detailBeans;
    private LayoutInflater inflater;
    private Context context;
    ImageView ivDetail;
    TextView tvNumber;
    private ImageLoader mImageLoader;
    DisplayImageOptions options;

    public DetailAdapter(Context context,ArrayList<DetailBean> detailBeans){

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

        //获取图片加载实例
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.dianying)
                .showImageForEmptyUri(R.mipmap.dianying)
                .showImageOnFail(R.mipmap.dianying)
                .cacheOnDisk(true)
                .delayBeforeLoading(100)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .build();//构建完成

        this.context = context;
        this.detailBeans = detailBeans;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return detailBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return detailBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = inflater.inflate(R.layout.grid_item_detail,null);

        ivDetail = (ImageView)view.findViewById(R.id.imagedetail);
        tvNumber = (TextView)view.findViewById(R.id.number);
        tvNumber.setText((CharSequence) detailBeans.get(i).getEp());//获取详情页面中的GridView中的数字

        tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(context, PartOfActivity.class);
                //向part页面发送org_url数据
//                Bundle bundle = new Bundle();
//                bundle.putString("org_url",detailBeans.get(i).getOrg_url());
                intent.putExtra("myData",detailBeans);
                intent.putExtra("pos",i);

                context.startActivity(intent);

            }
        });

        return view;
    }
}
