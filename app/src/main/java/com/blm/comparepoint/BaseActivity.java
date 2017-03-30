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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void pointClick(View v) {

    }
}
