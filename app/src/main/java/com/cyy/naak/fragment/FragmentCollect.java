package com.cyy.naak.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyy.naak.activity.DetailsActivity;
import com.cyy.naak.adapter.CollectAdapter;
import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.db.CollectDAO;
import com.cyy.naak.fragmentdemo.R;

import java.util.ArrayList;

public class FragmentCollect extends Fragment {
	private TextView mTv;
    private Button bt_selectall;
    private ImageView bt_show;
    private int checkNum = 0;
    private int ccid;

    private ListView lv;
    private CollectDAO dao;

    private ArrayList<CollectBean> collectList = new ArrayList<>();

    private CollectAdapter adapter;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = View.inflate(getActivity(),R.layout.fragment_collect,null);
       lv = (ListView)view.findViewById(R.id.lv_collect);
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               //收藏界面往详情界面传递数据
               Intent intent = new Intent(getActivity(), DetailsActivity.class);
               Bundle bundle = new Bundle();
               bundle.putInt("ccid",collectList.get(position).ccid);
               bundle.putString("pic",collectList.get(position).pic);
               bundle.putString("eps",collectList.get(position).eps);
               bundle.putString("title",collectList.get(position).cTitle);
               intent.putExtras(bundle);
               startActivity(intent);
           }
       });

        return view;
    }

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

        collectList = new ArrayList<CollectBean>();
        dao = new CollectDAO(getActivity());

		mTv = (TextView)getView().findViewById(R.id.titleCollect_bar);
        mTv.setText("我的收藏");
        bt_selectall = (Button) getView().findViewById(R.id.bt_delete_all);//删除所选
        bt_selectall.setVisibility(View.GONE);
        bt_show = (ImageView) getView().findViewById(R.id.bt_edit);//编辑

        // 删除所选的数据
        bt_selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CollectAdapter.flag) {

                    for (int i = 0; i < collectList.size(); i++) {
                        if (CollectAdapter.getIsSelected().get(i)){
                            ccid = collectList.get(i).ccid;
                            if (dao.deleteCollectByItemID(ccid)){
                                checkNum++;
                            }
                        }
                    }
                    if (checkNum == 0) {
                        return;
                    }else{
                        CollectAdapter.flag = false;
                        bt_selectall.setVisibility(View.GONE);
//                        bt_show.setText("编辑");
                        collectList = dao.findAll();
                        checkNum = 0;
                        adapter = new CollectAdapter(getActivity(),collectList);
                        lv.setAdapter(adapter);
                    }
                }
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(CollectAdapter.flag){
                    CollectAdapter.flag = false;
                    bt_selectall.setVisibility(View.GONE);
                    collectList = dao.findAll();

                    adapter = new CollectAdapter(getActivity(),collectList);
                    lv.setAdapter(adapter);
                }else{
                    CollectAdapter.flag = true;
                    bt_selectall.setVisibility(View.VISIBLE);

                    adapter = new CollectAdapter(getActivity(),collectList);
                    lv.setAdapter(adapter);
                }
            }
        });
	}

    //查询已经收藏的数据
    @Override
    public void onResume() {
        collectList = dao.findAll();

        adapter = new CollectAdapter(getActivity(),collectList);
        lv.setAdapter(adapter);

        super.onResume();
    }

    private void dataChanged() {

        adapter.notifyDataSetChanged();
    };

    @Override
	public void onPause() {
		super.onPause();
	}

}
