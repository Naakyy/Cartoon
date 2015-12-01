package com.cyy.naak.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cyy.naak.adapter.HomeAdapter;
import com.cyy.naak.untils.ApplicationController;
import com.cyy.naak.beans.HomeBean;
import com.cyy.naak.fragmentdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SuperAwesomeCardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,AbsListView.OnScrollListener {

    private static final String ARG_POSITION = "position";
    private int position;

    private ArrayList<HomeBean> mData;
    private HomeAdapter adapter;
    private GridView gv;

    private String url;
    private int page = 1;
    private ProgressDialog progressDialog;

    boolean isLastRow = false;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    //使得导航上的每一项的内容不一样
    public String comicUrl(int i, int page) {
        switch (i) {
            case 0:
                url = getUrl(1, page);
                break;
            case 1:
                url = getUrl(2, page);
                break;
            case 2:
                url = getUrl(3, page);
                break;
            case 3:
                url = getUrl(4, page);
                break;
            default:
                break;
        }
        return url;
    }

    public String getUrl(int sort, int page) {
        return "http://ch4.kiomon.com/comic_v0/comic/" + sort + "/30/" + page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);

        comicUrl(position, page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.gridview_main, null);
        gv = (GridView) view.findViewById(R.id.gridview);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("加载中.......");
        progressDialog.show();

        gv.setOnScrollListener(this);
        getFirstData(comicUrl(position, 1));

        return view;
    }

    //获取第一页的数据
    private void getFirstData(String url1) {
        StringRequest stringRequest = new StringRequest(url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                mData = new ArrayList<HomeBean>();

                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        HomeBean vb = new HomeBean();
                        vb.setCcid(jsonObject.getInt("ccid"));
                        vb.setPic(jsonObject.getString("pic"));
                        vb.setTitle(jsonObject.getString("title"));
                        vb.setEps(jsonObject.getString("eps"));
                        mData.add(vb);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //刷新数据
//                adapter.notifyDataSetChanged();
                adapter = new HomeAdapter(getActivity(),mData);
                gv.setAdapter(adapter);
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ApplicationController.getInstance().addToRequestQueue(stringRequest);

    }

    private void fetchVplley(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                ArrayList<HomeBean> data = new ArrayList<>();
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        HomeBean vb = new HomeBean();
                        vb.setCcid(jsonObject.getInt("ccid"));
                        vb.setPic(jsonObject.getString("pic"));
                        vb.setTitle(jsonObject.getString("title"));
                        vb.setEps(jsonObject.getString("eps"));
                        data.add(vb);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //刷新适配器的数据
                if (adapter != null) {
                    adapter.update(data);
                }
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    public void hidePDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    @Override
    public void onRefresh() {

        Toast.makeText(getActivity(), "正在刷新.....", Toast.LENGTH_LONG).show();
        page = 1; //还原
        getFirstData(comicUrl(position, 1));

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //正在滚动时回调，回调2~3次，手指没抛则回调两次。（scrollState = 2的这次不回调）
        //回调的顺序如下：
        //第一次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
        //第二次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
        //第三次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
        //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；由于用户的操作，屏幕产生惯性滑动时为2；

        //当滚到最后一行且停止滚动时，执行加载
        if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

            page = page + 1;
            comicUrl(position, page);
            fetchVplley(comicUrl(position, page));
//            Log.i("s======sss=s=s",page+":  "+comicUrl(position,page));
            adapter.notifyDataSetChanged();
            isLastRow = false;

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次
        //firstVisibleItem  当前能看见的第一个列表项ID（从0开始）
        //visibleItemCount  当前看见的列表项个数（小半个也算）
        //totalItemCount    总共的列表项

        //判断是否滚到最后一行
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
            isLastRow = true;
        }
    }
}