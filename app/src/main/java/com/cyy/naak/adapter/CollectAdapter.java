package com.cyy.naak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.beans.HomeBean;
import com.cyy.naak.fragmentdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naak on 15/7/9.
 */
public class CollectAdapter extends BaseAdapter{
    private ArrayList<CollectBean> collectBean;

    private Context context;
    private LayoutInflater inflater;

    DisplayImageOptions options;
    private ImageView imageView;
    private TextView tvCollectTitle,tvCollectEps;
    private CheckBox cb;
    public static boolean flag = false;
    // 用来控制CheckBox的选中状况
    private static List<Boolean> isSelected;

    public CollectAdapter(Context context,ArrayList<CollectBean> collectBean) {

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

        this.context = context;
        this.collectBean = collectBean;
        this.inflater = LayoutInflater.from(context);

        isSelected = new ArrayList<Boolean>();
        // 初始化数据
        setData(collectBean);

    }

    //获取ListView个数
    @Override
    public int getCount() {
        return collectBean.size();
    }

    //获取第position的数据资料
    @Override
    public Object getItem(int i) {
        return collectBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = inflater.inflate(R.layout.listview_item_collect,null);

        imageView = (ImageView)view.findViewById(R.id.iv_collect_pic);
        tvCollectTitle = (TextView)view.findViewById(R.id.tv_collect_title);
        tvCollectEps = (TextView)view.findViewById(R.id.tv_collect_eps);
        cb = (CheckBox)view.findViewById(R.id.checkbox_collect_item);
        if (flag){
            cb.setChecked(false);
            cb.setVisibility(View.VISIBLE);
        }else{
            cb.setChecked(true);
            cb.setVisibility(View.INVISIBLE);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSelected.set(i,isChecked);
            }
        });
        cb.setChecked(getIsSelected().get(i));

        ImageLoader.getInstance().displayImage(collectBean.get(i).getPic(),imageView,options);
        tvCollectTitle.setText((CharSequence)collectBean.get(i).getcTitle());
        tvCollectEps.setText("共" + (CharSequence)collectBean.get(i).getEps() + "集");

        return view;
    }

    public static List<Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(List<Boolean> isSelected) {
        CollectAdapter.isSelected = isSelected;
    }

    // 初始化isSelected的数据，设置cheakbox的初始状态，false为没选
    private void setData(ArrayList<CollectBean> lList) {
        if (lList != null){
            this.collectBean = lList;
        }else{
            this.collectBean = new ArrayList<CollectBean>();
        }

        if (collectBean.size()>0) {
            for (int i = 0; i < collectBean.size(); i++) {
                isSelected.add(false);
            }
        }
    }
}
