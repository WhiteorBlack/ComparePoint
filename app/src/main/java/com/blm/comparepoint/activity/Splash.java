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
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.NetUtils;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.blm.comparepoint.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

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
        Constants.USERTOKEN = (String) SPUtils.get(context, Constants.TOKEN, "");
        PostTools.postData(Constants.MAIN_URL + "User/GetInfo", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    T.showShort(context, "检查网络后重试!");
                    finish();
                    countDownTimer.cancel();
                    return;
                }
                Bean_GamerInfo bean_gamerInfo = new Gson().fromJson(response, Bean_GamerInfo.class);
                if (bean_gamerInfo.Success && bean_gamerInfo.Data != null) {
                    TIMUser user = new TIMUser();
                    user.setAccountType(Constants.ACCOUNT_TYPE);
                    user.setAppIdAt3rd(Constants.IM_APP_ID + "");
                    user.setIdentifier(bean_gamerInfo.Data.GameUserId);
                    TIMManager.getInstance().login(Constants.IM_APP_ID, user, bean_gamerInfo.Data.IMSign, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            L.e("IM error--" + i + "---" + s);
                        }

                        @Override
                        public void onSuccess() {
                            L.e("IM login success--");
                            TIMGroupManager.getInstance().applyJoinGroup(Constants.GROUP_ID, "", new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    L.e("applay error--" + i + s);
                                }

                                @Override
                                public void onSuccess() {
                                    L.e("join success");
                                }
                            });
                            finish();
                            startActivity(new Intent(context, Home.class));
                            countDownTimer.cancel();
                        }
                    });
                    Constants.USERTOKEN = bean_gamerInfo.Data.Token;
                    SPUtils.put(context, Constants.TOKEN, bean_gamerInfo.Data.Token);
                    SPUtils.put(context, Constants.AVATAR, bean_gamerInfo.Data.Avatar==null?"":bean_gamerInfo.Data.Avatar);
                    SPUtils.put(context, Constants.GAMER_ID, bean_gamerInfo.Data.GameUserId);
                    SPUtils.put(context, Constants.NICKNAME, bean_gamerInfo.Data.NickName);
                    SPUtils.put(context, Constants.USERAMOUNT, bean_gamerInfo.Data.UserBalance);
                    SPUtils.put(context, Constants.ACTIVEAMOUNT, bean_gamerInfo.Data.UserActive);
                    SPUtils.put(context, Constants.IS_LOGIN, true);
                    SPUtils.put(context, Constants.ISSIGN, bean_gamerInfo.Data.IsSignToday);
                    SPUtils.put(context,Constants.USERNAME,bean_gamerInfo.Data.UserName);

                } else {
                    SPUtils.put(context, Constants.IS_LOGIN, false);
                    T.showShort(context, "登陆已过期,请重新登陆");
                    startActivity(new Intent(context, Login.class));
                    finish();
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
                if (!(boolean) SPUtils.get(context, Constants.IS_LOGIN, false) && millisUntilFinished / 1000 == 8) {
                    finish();
                    startActivity(new Intent(context, Login.class));
                    countDownTimer.cancel();
                }
            }

            @Override
            public void onFinish() {
                    T.showShort(context, "无网络连接,请检查后重试");
                    finish();

            }
        }.start();
    }
}
