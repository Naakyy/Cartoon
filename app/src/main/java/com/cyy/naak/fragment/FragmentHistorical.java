package com.cyy.naak.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyy.naak.activity.DetailsActivity;
import com.cyy.naak.activity.PartOfActivity;
import com.cyy.naak.activity.VedioActivity;
import com.cyy.naak.adapter.CollectAdapter;
import com.cyy.naak.adapter.HistoricalAdapter;
import com.cyy.naak.beans.CollectBean;
import com.cyy.naak.beans.HistoricalBean;
import com.cyy.naak.db.CollectDAO;
import com.cyy.naak.db.ComicDBOpenHelper;
import com.cyy.naak.db.HistoricalDAO;
import com.cyy.naak.fragmentdemo.R;

import java.util.ArrayList;

import io.vov.vitamio.utils.Log;

public class FragmentHistorical extends Fragment {
	private TextView mTv;
    private ListView lv;
    private ImageView btHistoricalDeleteAll;
    private ArrayList<HistoricalBean> historicalList;
    private HistoricalDAO dao;
    private HistoricalAdapter adapter;

    private Dialog dialog;
    private TextView dialogTitle,dialogContent;
    private Button dialogCancel,dialogComplete;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//        Log.i("这是--onCreateView()--");
        System.out.println("这是--onCreateView()--");

        View view = View.inflate(getActivity(),R.layout.fragment_historical,null);
        lv = (ListView)view.findViewById(R.id.lv_historical);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //历史纪录界面往分段界面传递数据
                Intent intent = new Intent(getActivity(), PartOfActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ccid", historicalList.get(position).ccid);
                bundle.putInt("pos",-1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btHistoricalDeleteAll = (ImageView)view.findViewById(R.id.bt_historical_bar);
        btHistoricalDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用提示界面
                getDialog();
            }
        });

		return view;
	}

    public void getDialog(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_historical_layout);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);

        dialogTitle = (TextView)dialog.findViewById(R.id.dialog_historical_title);
        dialogContent = (TextView)dialog.findViewById(R.id.dialog_historical_content);
        dialogCancel = (Button)dialog.findViewById(R.id.dialog_historical_cancel);
        dialogComplete = (Button)dialog.findViewById(R.id.dialog_historical_complete);

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //历史纪录清除所有数据
                dao.deleteAll();
                //刷新适配器的数据
                if (adapter != null) {
                    adapter.update(historicalList);
                }
                dialog.dismiss();
            }
        });

        dialogWindow.setBackgroundDrawableResource(R.color.dialog_transparent);
        dialog.show();

    }


    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
//        Log.i("这是--onActivityCreated()--");
        System.out.println("这是--onActivityCreated()--");

		super.onActivityCreated(savedInstanceState);
		mTv = (TextView)getView().findViewById(R.id.titleHistorical_bar);
		mTv.setText("历史纪录");

        dao = new HistoricalDAO(getActivity());
	}

    @Override
    public void onResume() {
//        Log.i("这是--onResume()--");
        System.out.println("这是--onResume()--");

        //查询历史纪录的数据
        historicalList = dao.findAll();

        adapter = new HistoricalAdapter(getActivity(),historicalList);
        lv.setAdapter(adapter);

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
