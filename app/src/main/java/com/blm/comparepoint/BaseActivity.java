package com.blm.comparepoint;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.blm.comparepoint.activity.NotifyDetial;
import com.blm.comparepoint.bean.Bean_Jpush;
import com.blm.comparepoint.interfacer.UpdateRedAmountInterfacer;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/2/15.
 */

public class BaseActivity extends AppCompatActivity {

    public Context context;
    public Activity activity;
   public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setRedAmount();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        context = this;
        activity = this;
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("action.UpdateRedAmount");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setRedAmount();
        }
    };

    public void setRedAmount(){

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
        unregisterReceiver(broadcastReceiver);
    }

    public void pointClick(View v) {

    }

}
