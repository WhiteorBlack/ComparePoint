package com.blm.comparepoint;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.L;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/2/15.
 */

public class BaseActivity extends AppCompatActivity {

    public Context context;
    public Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        context = this;
        activity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void pointClick(View v) {

    }
}
