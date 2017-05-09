package com.blm.comparepoint;/**
 * Created by Administrator on 2017/3/29.
 */

import android.app.Application;

import com.tencent.TIMManager;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * author:${白曌勇} on 2017/3/29
 * TODO:
 */
public class CompareApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TIMManager.getInstance().init(this);
        MobclickAgent.setDebugMode(false);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }
}
