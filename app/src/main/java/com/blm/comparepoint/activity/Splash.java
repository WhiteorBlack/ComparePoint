package com.blm.comparepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.wxapi.Constants;
import com.blm.comparepoint.wxapi.WXEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends BaseActivity {

    @BindView(R.id.img_splash)
    ImageView imgSplash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        countDown();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void countDown() {
        new CountDownTimer(3*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing()){
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                if ((boolean)SPUtils.get(context, Constants.IS_LOGIN,false)){
                    startActivity(new Intent(context, WXEntryActivity.class));
                }else {
                    startActivity(new Intent(context,Home.class));
                }
                AppManager.getAppManager().finishActivity();

            }
        }.start();
    }
}
