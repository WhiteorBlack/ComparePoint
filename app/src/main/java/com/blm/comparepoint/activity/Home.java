package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.presenter.HomePresenter;
import com.blm.comparepoint.widget.ScrollTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends BaseActivity implements HomeOprateView {

    @BindView(R.id.txt_notify)
    ScrollTextView txtNotify;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.txt_red_money)
    TextView txtRedMoney;
    @BindView(R.id.txt_online)
    TextView txtOnline;
    @BindView(R.id.txt_role)
    TextView txtRole;
    @BindView(R.id.txt_order)
    TextView txtOrder;
    @BindView(R.id.recy_history)
    RecyclerView recyHistory;
    @BindView(R.id.fl_single)
    FrameLayout flSingle;
    @BindView(R.id.fl_small)
    FrameLayout flSmall;
    @BindView(R.id.fl_double)
    FrameLayout flDouble;
    @BindView(R.id.fl_big)
    FrameLayout flBig;
    @BindView(R.id.recy_bet_number)
    RecyclerView recyBetNumber;
    @BindView(R.id.recy_number)
    RecyclerView recyNumber;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.ll_money_content)
    LinearLayout llMoneyContent;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    private HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter = new HomePresenter(this);
    }

    @Override
    public void setNotify() {

    }

    @Override
    public void sigin(boolean successful) {

    }

    @Override
    public void showRoleDetial() {

    }

    @Override
    public void showOrderInfo() {

    }

    @Override
    public void betMoney() {

    }

    @Override
    public void toastNotify(String notify) {

    }

    @Override
    public void clearBetMoney() {

    }

    @Override
    public void getResult() {

    }

    @Override
    public void countDown(int type, int time) {
        new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing()) {
                    cancel();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @OnClick({R.id.txt_notify, R.id.txt_name, R.id.txt_money, R.id.img_sign, R.id.txt_red_money, R.id.txt_role, R.id.txt_order, R.id.fl_single, R.id.fl_small, R.id.fl_double, R.id.fl_big, R.id.img_clear, R.id.img_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_notify:
                break;
            case R.id.txt_name:
                break;
            case R.id.txt_money:
                break;
            case R.id.img_sign:
                break;
            case R.id.txt_red_money:
                break;
            case R.id.txt_role:
                break;
            case R.id.txt_order:
                break;
            case R.id.fl_single:
                break;
            case R.id.fl_small:
                break;
            case R.id.fl_double:
                break;
            case R.id.fl_big:
                break;
            case R.id.img_clear:
                break;
            case R.id.img_confirm:
                break;
        }
    }
}
