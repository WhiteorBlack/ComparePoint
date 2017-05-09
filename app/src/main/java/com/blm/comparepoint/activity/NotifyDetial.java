package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.AppUtils;
import com.blm.comparepoint.untils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

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
        if (getIntent().getBooleanExtra("isPush",false)){
            Bundle bundle=getIntent().getBundleExtra("data");
            notifyDetial=bundle.getString(JPushInterface.EXTRA_ALERT);
            notifyTitle=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            notifyTime= AppUtils.longToString(bundle.getLong("time"),"yyyy-MM-dd HH:mm");
        }else {
            notifyDetial = getIntent().getStringExtra("notify");
            notifyTime = getIntent().getStringExtra("time");
            notifyTitle = getIntent().getStringExtra("title");
        }

        txtNotifyDetial.setText(notifyDetial);
        txtTime.setText(notifyTime);
        txtTitle.setText(notifyTitle);

    }

    @OnClick(R.id.img_back)
    public void onClick() {
        if (!AppManager.getAppManager().isActivityInStack(this)){
            startActivity(new Intent(context,Home.class));
        }
        finish();
    }
}
