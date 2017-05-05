package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.adapter.NotifyListAdapter;
import com.blm.comparepoint.interfacer.OnItemClickListener;
import com.blm.comparepoint.widget.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/5/5.
 */

public class NotifyList extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.recy_notify)
    XRecyclerView recyNotify;
    private List notifyList;
    private NotifyListAdapter notifyListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        notifyList=new ArrayList();
        notifyListAdapter=new NotifyListAdapter(notifyList);
        recyNotify.setPullRefreshEnabled(false);
        recyNotify.setLoadingMoreEnabled(false);
        recyNotify.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyNotify.setAdapter(notifyListAdapter);
        notifyListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                startActivity(new Intent(context,NotifyDetial.class).putExtra("time","").putExtra("title","").putExtra("notify",""));
            }

            @Override
            public void onItemLongClick(View v, int pos) {

            }
        });
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
