package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.adapter.NotifyListAdapter;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.Bean_NotifyList;
import com.blm.comparepoint.interfacer.OnItemClickListener;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.widget.xrecycleview.XRecyclerView;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/5/5.
 */

public class NotifyList extends BaseActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.recy_notify)
    XRecyclerView recyNotify;
    private List notifyList;
    private NotifyListAdapter notifyListAdapter;
    private int pageIndex = 1;
    private int pageSize = 15;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_list);
        ButterKnife.bind(this);
        initView();
        getNotifyList();
    }

    private void getNotifyList() {
        Map<String, String> params = new HashMap<>();
        params.put("PageIndex", pageIndex + "");
        params.put("PageSize", pageSize + "");
        PostTools.getData(Constants.MAIN_URL + "User/GetSystemMsg", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                Bean_NotifyList bean_NotifyList = new Gson().fromJson(response, Bean_NotifyList.class);
                if (bean_NotifyList.Success && bean_NotifyList.Data != null && bean_NotifyList.Data.size() > 0) {
                    notifyList.addAll(bean_NotifyList.Data);
                    if (bean_NotifyList.Data.size() < pageSize) {
                        recyNotify.setLoadingMoreEnabled(false);
                    } else {
                        recyNotify.setLoadingMoreEnabled(true);
                    }
                    notifyListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        notifyList = new ArrayList();
        notifyListAdapter = new NotifyListAdapter(notifyList);
        recyNotify.setPullRefreshEnabled(false);
        recyNotify.setLoadingMoreEnabled(false);
        recyNotify.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyNotify.setAdapter(notifyListAdapter);
        recyNotify.setLoadingListener(this);
        notifyListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bean_NotifyList.NotifyDetail notifyDetial = (Bean_NotifyList.NotifyDetail) notifyList.get(pos);
                startActivity(new Intent(context, NotifyDetial.class).putExtra("time", notifyDetial.SendTime).
                        putExtra("title", notifyDetial.MsgTitle).putExtra("notify", notifyDetial.MsgContent));
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

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        getNotifyList();
    }
}
