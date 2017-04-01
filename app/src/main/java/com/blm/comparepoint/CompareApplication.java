package com.blm.comparepoint;/**
 * Created by Administrator on 2017/3/29.
 */

import android.app.Application;

import com.tencent.TIMManager;

/**
 * author:${白曌勇} on 2017/3/29
 * TODO:
 */
public class CompareApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TIMManager.getInstance().init(this);
    }
}
