package com.cyy.naak.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cyy.naak.activity.ApplicationController;
import com.cyy.naak.adapter.VplleyAdapter;
import com.cyy.naak.beans.VplleyBean;
import com.cyy.naak.fragmentdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cyy.naak.fragmentdemo.R.layout.grid_item;

public class SuperAwesomeCardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,AbsListView.OnScrollListener{
	
	private static final String ARG_POSITION = "position";
	private int position;

    private ArrayList<VplleyBean> mData;
    private VplleyAdapter adapter;
    private GridView gv;
    private String url;
    private int page = 1;
    private ProgressDialog progressDialog;

    boolean isLastRow = false;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static SuperAwesomeCardFragment newInstance(int position){
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

    public String comicUrl(int i,int page){
        switch (i){
            case 0:
                url = getUrl(1,page);
            break;
            case 1:
                url = getUrl(2,page);
                break;
            case 2:
                url = getUrl(3,page);
                break;
            case 3:
                url = getUrl(4,page);
                break;
        default:
        break;
    }
        return url;
    }
    public String getUrl(int sort,int page){
        return "http://ch4.kiomon.com/comic_v0/comic/"+ sort +"/30/"+page;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);

        comicUrl(position,page);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View view = View.inflate(getActivity(),R.layout.mygridview,null);
        gv = (GridView)view.findViewById(R.id.gridview);

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);
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

        mData = new ArrayList<VplleyBean>();

        gv.setOnScrollListener(this);
        getFirstData(comicUrl(position, 1));

		return view;
	}
    //获取第一页的数据
    private void getFirstData(String url1){
        StringRequest stringRequest = new StringRequest(url1,new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;

                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        VplleyBean vb = new VplleyBean();
                        vb.setPic(jsonObject.getString("pic"));
                        vb.setTitle(jsonObject.getString("title"));
                        mData.add(vb);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //刷新数据
//                adapter.notifyDataSetChanged();
                adapter = new VplleyAdapter(getActivity(),mData);
                gv.setAdapter(adapter);
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ApplicationController.getInstance().addToRequestQueue(stringRequest);

    }

    private void fetchVplley(String url){
        StringRequest stringRequest = new StringRequest(url,new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                ArrayList<VplleyBean> data = new ArrayList<>();
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        VplleyBean vb = new VplleyBean();
                        vb.setPic(jsonObject.getString("pic"));
                        vb.setTitle(jsonObject.getString("title"));
                        data.add(vb);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //刷新数据
//                adapter.notifyDataSetChanged();
                if (adapter !=null) {
                    adapter.update(data);
                }
                Toast.makeText(getActivity(),"第" + page + "页",Toast.LENGTH_LONG).show();

                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hidePDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ApplicationController.getInstance().addToRequestQueue(stringRequest);

    }

    public void hidePDialog(){
        if(progressDialog!=null)
            progressDialog.dismiss();
            progressDialog=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    @Override
    public void onRefresh() {
        //TODO:写一个Handle的方法
        Toast.makeText(getActivity(),"正在刷新.....",Toast.LENGTH_LONG).show();
//        new Handler().postDelayed(new Runnable() {
//           @Override
//           public void run() {
//                    adapter = new VplleyAdapter(getActivity(),mData);
//                    gv.setAdapter(adapter);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    adapter.notifyDataSetChanged();
//                Toast.makeText(getActivity(),"OK....",Toast.LENGTH_LONG).show();
//           }
//        },2000);
        page = 1;
        getFirstData(comicUrl(position, 1));

//        fetchVplley(comicUrl(position,1));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            //TODO:要加载的元素

            page = page+1;
            comicUrl(position,page);
            fetchVplley(comicUrl(position,page));
//            geneItems();
            adapter.notifyDataSetChanged();
//            addMoreData();

            isLastRow = false;

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount >0){
            isLastRow = true;
        }
    }

    //加载更多数据
//    private void addMoreData(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bundle b = new Bundle();
//                try {
//                    Thread.sleep(2000);
//                    b.putBoolean("addMoreData",true);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }finally {
//                    Message msg = handler.obtainMessage();
//                    msg.setData(b);
//                    handler.sendMessage(msg);
//                }
//            }}).start();
//    }
//
//   private Handler handler = new Handler(){
//       @Override
//       public void handleMessage(Message msg) {
//           footer.setVisibility(View.GONE);
////           adapter.
//       };
//   };

}
