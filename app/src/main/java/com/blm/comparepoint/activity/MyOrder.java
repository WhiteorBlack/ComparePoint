package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.adapter.MyOrderAdapter;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.widget.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class MyOrder extends BaseActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.img_sign)
    TextView imgSign;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.txt_red_money)
    TextView txtRedMoney;
    @BindView(R.id.recy_order)
    XRecyclerView recyOrder;

    private List orderList;
    private MyOrderAdapter myOrderAdapter;

    private int pageIndex=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        orderList=new ArrayList();
        myOrderAdapter=new MyOrderAdapter(orderList);
        recyOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        recyOrder.setPullRefreshEnabled(false);
        recyOrder.setLoadingMoreEnabled(true);
        recyOrder.setLoadingListener(this);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageIndex++;
    }
}
