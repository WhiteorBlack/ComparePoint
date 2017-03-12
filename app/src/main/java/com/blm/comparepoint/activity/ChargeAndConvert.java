package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.adapter.MyOrderAdapter;
import com.blm.comparepoint.untils.AppManager;
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

public class ChargeAndConvert extends BaseActivity implements XRecyclerView.LoadingListener {
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
    @BindView(R.id.recy_history)
    XRecyclerView recyHistory;
    @BindView(R.id.img_convert)
    ImageView imgConvert;
    @BindView(R.id.img_charge)
    ImageView imgCharge;

    private List orderList;
    private MyOrderAdapter myOrderAdapter;

    private int pageIndex=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_convert);
        ButterKnife.bind(this);
    }
    private void initView() {
        orderList=new ArrayList();
        myOrderAdapter=new MyOrderAdapter(orderList);
        recyHistory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        recyHistory.setPullRefreshEnabled(false);
        recyHistory.setLoadingMoreEnabled(true);
        recyHistory.setLoadingListener(this);
    }
    @OnClick({R.id.img_back, R.id.img_convert, R.id.img_charge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.img_convert:
                startActivity(new Intent(context,ConvertMoney.class));
                break;
            case R.id.img_charge:
                startActivity(new Intent(context,ChargeMoney.class));
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageIndex++;
    }
}
