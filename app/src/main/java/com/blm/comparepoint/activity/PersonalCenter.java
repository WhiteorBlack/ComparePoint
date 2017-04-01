package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class PersonalCenter extends BaseActivity {
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
    @BindView(R.id.fl_my_info)
    FrameLayout flMyInfo;
    @BindView(R.id.fl_my_order)
    FrameLayout flMyOrder;
    @BindView(R.id.fl_my_charge)
    FrameLayout flMyCharge;
    @BindView(R.id.fl_help)
    FrameLayout flHelp;
    @BindView(R.id.fl_service)
    FrameLayout flService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        ButterKnife.bind(this);
        setUserInfo();
    }

    private void setUserInfo() {
        glideImage((String) SPUtils.get(context, Constants.AVATAR, ""), imgAvatar);
        txtName.setText((String) SPUtils.get(context, Constants.NICKNAME, ""));
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l) + "");
        imgSign.setEnabled(!(boolean) SPUtils.get(context, Constants.ISSIGN, false));
        if ((boolean) SPUtils.get(context, Constants.ISSIGN, false)){
            imgSign.setText("已签到");
        }else {
            imgSign.setText("签到");
        }
    }

    private void glideImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @OnClick({R.id.img_back, R.id.img_sign, R.id.fl_my_info, R.id.fl_my_order, R.id.fl_my_charge, R.id.fl_help, R.id.fl_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.img_sign:

                break;
            case R.id.fl_my_info:
                startActivity(new Intent(context,MyInfoCard.class));
                break;
            case R.id.fl_my_order:
                startActivity(new Intent(context,MyOrder.class));
                break;
            case R.id.fl_my_charge:
                startActivity(new Intent(context,ChargeAndConvert.class));
                break;
            case R.id.fl_help:

                break;
            case R.id.fl_service:
                startActivity(new Intent(context,ServiceCenter.class));
                break;
        }
    }
}
