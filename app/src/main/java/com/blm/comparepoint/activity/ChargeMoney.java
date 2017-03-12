package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class ChargeMoney extends BaseActivity {
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
    @BindView(R.id.img_code_pic)
    ImageView imgCodePic;
    @BindView(R.id.txt_notify)
    TextView txtNotify;
    @BindView(R.id.img_download)
    ImageView imgDownload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_money);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.img_sign, R.id.img_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.img_sign:
                break;
            case R.id.img_download:
                break;
        }
    }
}
