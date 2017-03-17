package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.Bean_GamerInfo;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.NetUtils;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.blm.comparepoint.wxapi.WXEntryActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends BaseActivity {

    @BindView(R.id.img_splash)
    ImageView imgSplash;
    private CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        countDown();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ((boolean) SPUtils.get(context, Constants.IS_LOGIN, false)) {
            getUserInfo();
        }
    }

    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("Token", (String) SPUtils.get(context, Constants.TOKEN, ""));
        PostTools.postData(Constants.MAIN_URL + "User/GetInfo", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    T.showShort(context, "检查网络后重试!");
                    AppManager.getAppManager().finishActivity();
                    countDownTimer.cancel();
                    return;
                }
                Bean_GamerInfo bean_gamerInfo = new Gson().fromJson(response, Bean_GamerInfo.class);
                if (bean_gamerInfo.Success && bean_gamerInfo.gamerInfo != null) {
                    Constants.USERTOKEN = bean_gamerInfo.gamerInfo.Token;
                    SPUtils.put(context, Constants.TOKEN, bean_gamerInfo.gamerInfo.Token);
                    SPUtils.put(context, Constants.AVATAR, bean_gamerInfo.gamerInfo.Avatar);
                    SPUtils.put(context, Constants.GAMER_ID, bean_gamerInfo.gamerInfo.GameUserId);
                    SPUtils.put(context, Constants.NICKNAME, bean_gamerInfo.gamerInfo.NickName);
                    SPUtils.put(context, Constants.OPENID, bean_gamerInfo.gamerInfo.OpenId);
                    SPUtils.put(context, Constants.USERAMOUNT, bean_gamerInfo.gamerInfo.UserBalance);
                    SPUtils.put(context, Constants.ACTIVEAMOUNT, bean_gamerInfo.gamerInfo.UserActive);
                    SPUtils.put(context, Constants.IS_LOGIN, true);
                    SPUtils.put(context, Constants.ISSIGN, bean_gamerInfo.gamerInfo.IsSignToday);
                    startActivity(new Intent(context, Home.class));
                    AppManager.getAppManager().finishActivity();
                    countDownTimer.cancel();
                } else {
                    SPUtils.put(context, Constants.IS_LOGIN, false);
                    T.showShort(context, "登陆已过期,请重新登陆");
                    startActivity(new Intent(context, WXEntryActivity.class));
                    AppManager.getAppManager().finishActivity();
                    countDownTimer.cancel();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void countDown() {
        countDownTimer = new CountDownTimer(10 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if ((boolean) SPUtils.get(context, Constants.IS_LOGIN, false) && millisUntilFinished / 1000 > 3) {
                    AppManager.getAppManager().finishActivity();
                    startActivity(new Intent(context, WXEntryActivity.class));
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                if (NetUtils.isConnected(context) || NetUtils.isWifi(context)) {
                    T.showShort(context, "获取用户信息失败,请重新登陆");
                    startActivity(new Intent(context, WXEntryActivity.class));
                    AppManager.getAppManager().finishActivity();
                }

            }
        }.start();
    }
}
