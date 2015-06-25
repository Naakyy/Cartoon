package com.cyy.naak.activity;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by naak on 15/6/15.
 */
public class ApplicationController extends Application{

    private static final String TAG= ApplicationController.class.getSimpleName();

    private RequestQueue requestQueue;
//    private ImageLoader imageLoader;

    private static ApplicationController mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(getApplicationContext());

        mInstance=this;

    }

    //初始化ImageLoader
    public static void initImageLoader(Context context){
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration.Builder configuration = new ImageLoaderConfiguration.Builder(context);
        configuration.threadPriority(Thread.NORM_PRIORITY - 2);
        configuration.denyCacheImageMultipleSizesInMemory();
        configuration.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        configuration.diskCacheSize(50 * 1024 * 1024);
        configuration.tasksProcessingOrder(QueueProcessingType.LIFO);
        configuration.writeDebugLogs();

        ImageLoader.getInstance().init(configuration.build());

    }

    public static synchronized ApplicationController getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null)
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

//    public ImageLoader getImageLoader(){
//        getRequestQueue();
//        if(imageLoader==null){
//            imageLoader=new ImageLoader(requestQueue,new LruBitmapCache());
//        }
//        return imageLoader;
//    }

    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag){
        if(requestQueue!=null){
            requestQueue.cancelAll(tag);
        }
    }
}
