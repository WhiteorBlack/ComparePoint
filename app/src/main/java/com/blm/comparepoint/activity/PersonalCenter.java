package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

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
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;
    @BindView(R.id.txt_user_id)
    TextView txtUserId;
    @BindView(R.id.fl_notify)
    FrameLayout flNotify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }

    @Override
    public void setRedAmount() {
        super.setRedAmount();
        T.showShort(context,"hhhh");
        txtRedMoney.setText(SPUtils.get(this, Constants.ACTIVEAMOUNT, 0l) + "");
    }

    private void setUserInfo() {
        glideImage((String) SPUtils.get(context, Constants.AVATAR, ""), imgAvatar);
        txtName.setText((String) SPUtils.get(context, Constants.NICKNAME, ""));
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l) + "");
        imgSign.setEnabled(!(boolean) SPUtils.get(context, Constants.ISSIGN, false));
        if ((boolean) SPUtils.get(context, Constants.ISSIGN, false)) {
            imgSign.setText("已签到");
        } else {
            imgSign.setText("签到");
        }
        txtUserId.setText((String) SPUtils.get(context, Constants.GAMER_ID, ""));
    }

    private void glideImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @OnClick({R.id.fl_notify,R.id.btn_login_out, R.id.img_back, R.id.img_sign, R.id.fl_my_info, R.id.fl_my_order, R.id.fl_my_charge, R.id.fl_help, R.id.fl_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_notify:
                startActivity(new Intent(context,NotifyList.class));
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_sign:
                signIn();
                break;
            case R.id.fl_my_info:
                startActivity(new Intent(context, MyInfoCard.class));
                break;
            case R.id.fl_my_order:
                startActivity(new Intent(context, MyOrder.class));
                break;
            case R.id.fl_my_charge:
                startActivity(new Intent(context, ChargeAndConvert.class));
                break;
            case R.id.fl_help:
                startActivity(new Intent(context, WebView.class).putExtra("url", Constants.HELP_URL).putExtra("title", "帮助中心"));
                break;
            case R.id.fl_service:
                startActivity(new Intent(context, ServiceCenter.class));
                break;
            case R.id.btn_login_out:
                SPUtils.clear(context);
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(context, Login.class));
                break;
        }
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
                        SPUtils.put(PersonalCenter.this, Constants.USERAMOUNT, (long) ((long) SPUtils.get(PersonalCenter.this, Constants.USERAMOUNT, 0) +
                                (int) SPUtils.get(PersonalCenter.this, Constants.SIGNBOUNS, 0)));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtMoney.setText(SPUtils.get(PersonalCenter.this, Constants.USERAMOUNT, 0) + "");
                            }
                        });
                    } else {
                        imgSign.setEnabled(true);
                        T.showShort(PersonalCenter.this, baseBean.Msg);
                    }
                }
            }
        });
    }
}
