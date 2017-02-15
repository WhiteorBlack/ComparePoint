package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.untils.AppManager;

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
                finish();
            }
        }.start();
    }
}
