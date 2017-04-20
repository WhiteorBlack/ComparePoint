package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.adapter.GoldRecordAdapter;
import com.blm.comparepoint.adapter.MyOrderAdapter;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.bean.Bean_GoldRecord;
import com.blm.comparepoint.bean.Bean_Order;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.widget.xrecycleview.XRecyclerView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class GoldRecord extends BaseActivity implements XRecyclerView.LoadingListener {
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
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private List orderList;
    private GoldRecordAdapter myOrderAdapter;

    private int pageIndex = 1;
    private int pageSize = 15;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);
        ButterKnife.bind(this);
        initView();
        setUserInfo();
        getOrderData();
    }

    private void setUserInfo() {
        Glide.with(this).load(SPUtils.get(context, Constants.AVATAR, "")).into(imgAvatar);
        txtName.setText((String) SPUtils.get(context, Constants.NICKNAME, ""));
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l) + "");
        imgSign.setEnabled(!(boolean) SPUtils.get(context, Constants.ISSIGN, false));
        if ((boolean) SPUtils.get(context, Constants.ISSIGN, false)) {
            imgSign.setText("已签到");
        } else {
            imgSign.setText("签到");
        }
    }

    private void getOrderData() {
        Map<String, String> params = new HashMap<>();
        params.put("PageIndex", pageIndex + "");
        params.put("PageSize", pageSize + "");
        params.put("Type","BetSignRecharge");
        PostTools.getData(Constants.MAIN_URL + "User/GetGoldRecord", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                if (pageIndex == 1) {
                    orderList.clear();
                }
                Bean_GoldRecord bean_order = new Gson().fromJson(response, Bean_GoldRecord.class);
                if (bean_order.Success && bean_order.Data != null) {
                    orderList.addAll(bean_order.Data);
                    if (bean_order.Data.size() < pageSize) {
                        recyOrder.setLoadingMoreEnabled(false);
                    } else {
                        recyOrder.setLoadingMoreEnabled(true);
                    }
                }
                myOrderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        txtTitle.setText("金币统计记录");
        orderList = new ArrayList();
        myOrderAdapter = new GoldRecordAdapter(orderList);
        recyOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyOrder.setAdapter(myOrderAdapter);
        recyOrder.setPullRefreshEnabled(false);
        recyOrder.setLoadingMoreEnabled(true);
        recyOrder.setLoadingListener(this);
    }

    @OnClick({R.id.img_back, R.id.img_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_sign:
                signIn();
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        getOrderData();
    }

    /**
     * 用户签到
     */
    public void signIn() {
        PostTools.postData(Constants.MAIN_URL + "User/Sign", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (!TextUtils.isEmpty(response)) {
                    BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                    if (baseBean.Success) {
                        imgSign.setEnabled(false);
                        SPUtils.put(context, Constants.USERAMOUNT, (long) ((long) SPUtils.get(context, Constants.USERAMOUNT, 0) +
                                (int) SPUtils.get(context, Constants.SIGNBOUNS, 0)));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0) + "");
                            }
                        });
                    } else {
                        imgSign.setEnabled(true);
                        T.showShort(context, baseBean.Msg);
                    }
                }
            }
        });
    }

}
