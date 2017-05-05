package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/5/5.
 */

public class NotifyDetial extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_notify_detial)
    TextView txtNotifyDetial;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_time)
    TextView txtTime;

    private String notifyDetial;
    private String notifyTime;
    private String notifyTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_detial);
        ButterKnife.bind(this);
        notifyDetial = getIntent().getStringExtra("notify");
        notifyTime = getIntent().getStringExtra("time");
        notifyTitle = getIntent().getStringExtra("title");
        txtNotifyDetial.setText(notifyDetial);
        txtTime.setText(notifyTime);
        txtTitle.setText(notifyTitle);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
