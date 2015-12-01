package com.cyy.naak.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cyy.naak.db.HistoricalDAO;
import com.cyy.naak.fragmentdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by naak on 15/10/27.
 * @Description: ${todo}<Toolbar类>
 */
public class MyToolbar {
    private Toolbar toolbar;
    private Context context;
    private TextView title;
    private ImageButton back_btn;
    public final static int SEARCH_BAR = 0;
    public final static int SETTING_BAR = 1;

    @Bind(R.id.bt_delete_all)
    Button bt_selectall;
    @Bind(R.id.lv_collect)
    ListView lv;

    public MyToolbar(Context context, Toolbar toolbar){
        this.context = context;
        this.toolbar = toolbar;

        ButterKnife.bind((Activity) context);
    }

    public void setToolbar(int type,Boolean showBack, final String toolTitle){


        title = (TextView)toolbar.findViewById(R.id.toolbar_title);
        title.setText(toolTitle);
        if (showBack){
            back_btn = (ImageButton)toolbar.findViewById(R.id.toolbar_back);
            back_btn.setVisibility(View.VISIBLE);
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity)context).finish();
                }
            });
        }

        switch (type){
            case SEARCH_BAR:
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.toolbar_search) {

                        }
                        return true;
                    }
                });
                toolbar.inflateMenu(R.menu.menu_toolbar_edit);
                break;

            case SETTING_BAR:
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.toolbar_setting){
//
                        }
                        return true;
                    }
                });
                toolbar.inflateMenu(R.menu.menu_toolbar_delete);
                break;

            default:
                break;
        }
    }


}
