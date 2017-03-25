package com.blm.comparepoint.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.adapter.BetHistoryAdapter;
import com.blm.comparepoint.adapter.BetNumberAdapter;
import com.blm.comparepoint.adapter.NumberAdapter;
import com.blm.comparepoint.bean.Bean_AppVersion;
import com.blm.comparepoint.bean.Bean_BetNumber;
import com.blm.comparepoint.bean.Bean_CurrentInfo;
import com.blm.comparepoint.bean.Bean_GameConfig;
import com.blm.comparepoint.bean.Bean_Number;
import com.blm.comparepoint.bean.Bean_SystemConfig;
import com.blm.comparepoint.dialog.GoOnBetPop;
import com.blm.comparepoint.dialog.NotifyPop;
import com.blm.comparepoint.dialog.UpdatePop;
import com.blm.comparepoint.dialog.WinPop;
import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.interfacer.OnItemClickListener;
import com.blm.comparepoint.interfacer.PopInterfacer;
import com.blm.comparepoint.presenter.HomePresenter;
import com.blm.comparepoint.untils.AppUtils;
import com.blm.comparepoint.untils.DensityUtils;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.widget.RecycleViewDivider;
import com.blm.comparepoint.widget.ScrollTextView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends BaseActivity implements HomeOprateView, PopInterfacer {

    @BindView(R.id.txt_notify)
    ScrollTextView txtNotify;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.img_sign)
    TextView imgSign;
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
    @BindView(R.id.txt_count_down)
    TextView txtCountDown;
    @BindView(R.id.txt_single_mutil)
    TextView txtSingleMutil;
    @BindView(R.id.txt_small_mutil)
    TextView txtSmallMutil;
    @BindView(R.id.txt_double_mutil)
    TextView txtDoubleMutil;
    @BindView(R.id.txt_big_mutil)
    TextView txtBigMutil;
    @BindView(R.id.img_ten)
    ImageView imgTen;
    @BindView(R.id.img_fifty)
    ImageView imgFifty;
    @BindView(R.id.img_han)
    ImageView imgHan;
    @BindView(R.id.img_fifty_han)
    ImageView imgFiftyHan;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.activity_home)
    LinearLayout activityHome;
    private HomePresenter homePresenter;

    private BetHistoryAdapter betHistoryAdapter;
    private BetNumberAdapter betNumberAdapter;
    private NumberAdapter numberAdapter;
    private List betHistoryList;
    private List betNumberList;
    private List numberList;

    //popwindow
    private WinPop winPop;
    private GoOnBetPop goOnBetPop;
    private NotifyPop notifyPop;
    private ProgressDialog progressDialog;
    private UpdatePop updatePop;

    private int historyCount = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter = new HomePresenter(this);
        initView();
        initNumData();
        recyNumber.post(new Runnable() {
            @Override
            public void run() {
                numberAdapter.setHeight(recyNumber.getHeight() - DensityUtils.dp2px(context, 9));
                numberAdapter.notifyDataSetChanged();
            }
        });
        recyHistory.post(new Runnable() {
            @Override
            public void run() {
                historyCount = recyHistory.getHeight() / DensityUtils.dp2px(context, 30);
            }
        });

        selectTen();

        homePresenter.resetStatue();
        homePresenter.getSystemConfig();
        homePresenter.getGameConfig();
        homePresenter.getVersion();
    }

    private void initNumData() {
        for (int i = 0; i < 10; i++) {
            Bean_Number number = new Bean_Number();
            number.number = i + 1;
            number.isSelected = false;
            number.isShine = false;
            numberList.add(number);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void setUserInfo() {
        glideImage((String) SPUtils.get(context, Constants.AVATAR, ""), imgAvatar);
        txtName.setText((String) SPUtils.get(context, Constants.NICKNAME, ""));
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0) + "");
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0) + "");
        imgSign.setEnabled(!(boolean) SPUtils.get(context, Constants.ISSIGN, false));
    }

    private void initData() {
        txtBigMutil.setText(Constants.RATIO);
        txtSingleMutil.setText(Constants.RATIO);
        txtSmallMutil.setText(Constants.RATIO);
        txtDoubleMutil.setText(Constants.RATIO);
        for (int i = 0; i < 10; i++) {
            Bean_BetNumber betNumber = new Bean_BetNumber();
            betNumber.betMutil = Constants.RATIO;
            betNumber.number = i + 1;
            betNumber.isSelected = false;
            betNumber.betGold=0;
            betNumberList.add(betNumber);
        }
        betNumberAdapter.notifyDataSetChanged();

    }

    private void initView() {
        betHistoryList = new ArrayList();
        betNumberList = new ArrayList();
        numberList = new ArrayList();

        betHistoryAdapter = new BetHistoryAdapter(betHistoryList);
        recyHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyHistory.addItemDecoration(new RecycleViewDivider(context,LinearLayoutManager.HORIZONTAL,1,R.color.lineLight));
        recyHistory.setAdapter(betHistoryAdapter);

        betNumberAdapter = new BetNumberAdapter(betNumberList);
        GridLayoutManager gridManager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        gridManager.setAutoMeasureEnabled(true);
        recyBetNumber.setLayoutManager(gridManager);
        recyBetNumber.addItemDecoration(new RecycleViewDivider(context, GridLayoutManager.HORIZONTAL, 1, R.color.lineLight));
        recyBetNumber.addItemDecoration(new RecycleViewDivider(context, GridLayoutManager.VERTICAL, 1, R.color.lineLight));
        recyBetNumber.setAdapter(betNumberAdapter);
        betNumberAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                homePresenter.betMoney(pos);
            }

            @Override
            public void onItemLongClick(View v, int pos) {

            }
        });

        recyNumber.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        numberAdapter = new NumberAdapter(numberList);
        recyNumber.setAdapter(numberAdapter);
        selectTen();
    }

    /**
     * 选择 10 金币
     */
    private void selectTen() {
        Constants.SELECT_GOLD = 10;
        glideImage(R.mipmap.icon_ten_big, imgTen);
        glideImage(R.mipmap.icon_fifuty_small, imgFifty);
        glideImage(R.mipmap.icon_handrad_small, imgHan);
        glideImage(R.mipmap.icon_fifuty_h_small, imgFiftyHan);
    }

    /**
     * 选择 50 金币
     */
    private void selectFifty() {
        Constants.SELECT_GOLD = 50;
        glideImage(R.mipmap.icon_ten_small, imgTen);
        glideImage(R.mipmap.icon_fifty_big, imgFifty);
        glideImage(R.mipmap.icon_handrad_small, imgHan);
        glideImage(R.mipmap.icon_fifuty_h_small, imgFiftyHan);
    }

    /**
     * 选择 100 金币
     */
    private void selectHan() {
        Constants.SELECT_GOLD = 100;
        glideImage(R.mipmap.icon_ten_small, imgTen);
        glideImage(R.mipmap.icon_fifuty_small, imgFifty);
        glideImage(R.mipmap.icon_han_big, imgHan);
        glideImage(R.mipmap.icon_fifuty_h_small, imgFiftyHan);
    }

    /**
     * 选择 500 金币
     */
    private void selectFiftyHan() {
        Constants.SELECT_GOLD = 500;
        glideImage(R.mipmap.icon_ten_small, imgTen);
        glideImage(R.mipmap.icon_fifuty_small, imgFifty);
        glideImage(R.mipmap.icon_handrad_small, imgHan);
        glideImage(R.mipmap.icon_fifty_han_big, imgFiftyHan);
    }

    private void glideImage(int resId, ImageView imageView) {
        Glide.with(context).load(resId).into(imageView);
    }

    private void glideImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void setNotify() {

    }

    @Override
    public void sigin(boolean successful) {
        imgSign.setEnabled(successful);
        if (successful) {
            SPUtils.put(context, Constants.USERAMOUNT, (long) ((long) SPUtils.get(context, Constants.USERAMOUNT, 0) +
                    (int) SPUtils.get(context, Constants.SIGNBOUNS, 0)));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0) + "");
                }
            });
        }

    }

    @Override
    public void betMoney() {

    }

    @Override
    public void toastNotify(String notify) {
        T.showShort(context, notify);
    }

    @Override
    public void getResult() {

    }

    private CountDownTimer countDownTimer;

    @Override
    public void countDown(final int type, int time) {
        countDownTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing()) {
                    cancel();
                }
                int leftTIme = (int) (millisUntilFinished / 1000);
                if (type == Constants.TYPE_BET_MONEY && leftTIme < 6) {
                    if (leftTIme % 2 > 1) {
                        txtCountDown.setTextColor(Color.RED);
                    } else {
                        txtCountDown.setTextColor(Color.WHITE);
                    }
                }
                txtCountDown.setText(leftTIme + "s");
            }

            @Override
            public void onFinish() {
                txtCountDown.setTextColor(Color.WHITE);
                homePresenter.endCountDown(type);
            }
        };
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    public void showWinPop(String money) {
        if (winPop == null) {
            winPop = new WinPop(context);
        }
        winPop.setMOney(money);
        winPop.showPop(txtBigMutil);
        winPop.setPopInterfacer(this, Constants.WIN_POP_FLAG);
    }

    @Override
    public void showGoBet(String notify) {
        if (goOnBetPop == null) {
            goOnBetPop = new GoOnBetPop(context);
        }
        goOnBetPop.setNotify(notify);
        goOnBetPop.showPop(txtBigMutil);
        goOnBetPop.setPopInterfacer(this, Constants.GO_BET_POP_FLAG);
    }

    @Override
    public void showNotify(String notify) {
        if (notifyPop == null) {
            notifyPop = new NotifyPop(context);
        }
        notifyPop.setNotify(notify);
        notifyPop.showPop(txtBigMutil);
        notifyPop.setPopInterfacer(this, Constants.NOTIFY_POP_FLAG);
    }

    @Override
    public void betClick(int pos) {
        switch (pos) {
            case 0: //十金币
                Constants.SELECT_GOLD = 10;
                selectTen();
                break;
            case 1:  //50gold
                Constants.SELECT_GOLD = 50;
                selectFifty();
                break;
            case 2:  //100 gold
                Constants.SELECT_GOLD = 100;
                selectHan();
                break;
            case 3:  //500 gold
                Constants.SELECT_GOLD = 500;
                selectFiftyHan();
                break;
        }
    }

    @Override
    public void resetTable() {

    }

    @Override
    public void toastCommonNotify(String notify) {

    }

    @Override
    public void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载配置中...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dimissDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void setSystemConfig(Bean_SystemConfig.SystemConfig systemConfig) {
        SPUtils.put(context, Constants.MINEXCHANGEAMOUNT, systemConfig.MinWithdramAmount);
        SPUtils.put(context, Constants.SIGNBOUNS, systemConfig.SignBonus);
        Constants.ROUNDTIME = systemConfig.RoundTime;
        Constants.LOTTERYTIME = systemConfig.LotteryTime;
        Constants.GOldRANGE = systemConfig.GoldRange;
    }

    @Override
    public void setGameConfig(Bean_GameConfig.GameConfig gameConfig) {
        Constants.RATIO = gameConfig.Ratio;
        initData();
    }


    private String downUrl;

    @Override
    public void checkUpdate(Bean_AppVersion.AppVersion appVersion) {
        if (appVersion.AppVisionId > AppUtils.getVersionCode(context) && AppUtils.getVersionCode(context) > 0) {
            downUrl = appVersion.DownUrl;
            if (updatePop == null) {
                updatePop = new UpdatePop(context);
                updatePop.setPopInterfacer(this, Constants.UPDATE_POP_FLAG);
            }
            updatePop.setContent(appVersion.VisionDesc);
            updatePop.showPop(txtBigMutil);
        }
    }

    @Override
    public void currentInfo(Bean_CurrentInfo.CurrentInfo currentInfo) {
        betHistoryList.addAll(currentInfo.LastBonusNum);
        betHistoryAdapter.notifyDataSetChanged();
        Constants.COUNTDOWNTIME = (int) (currentInfo.StartTime - (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE));
        Constants.BONUSDOWNTIME = (int) (currentInfo.BonusTime - (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE));
        if (Constants.COUNTDOWNTIME > 1) {
            countDown(Constants.TYPE_BET_MONEY, Constants.COUNTDOWNTIME);
        } else {
            countDown(Constants.TYPE_OPEN_CHESS, Constants.BONUSDOWNTIME);
        }
    }


    @OnClick({R.id.txt_notify, R.id.txt_name, R.id.txt_money, R.id.img_sign, R.id.txt_red_money, R.id.img_avatar,
            R.id.txt_role, R.id.txt_order, R.id.fl_single, R.id.fl_small, R.id.fl_double, R.id.fl_big,
            R.id.img_clear, R.id.img_confirm, R.id.img_ten, R.id.img_fifty, R.id.img_han, R.id.img_fifty_han})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_notify:

                break;
            case R.id.txt_name:
                break;
            case R.id.txt_money:

                break;
            case R.id.img_sign:
                homePresenter.signIn();
                break;
            case R.id.txt_red_money:
                break;
            case R.id.txt_role:
                break;
            case R.id.txt_order:
                startActivity(new Intent(context, MyOrder.class));
                break;
            case R.id.fl_single:
                homePresenter.betMoney(103);
                break;
            case R.id.fl_small:
                homePresenter.betMoney(102);
                break;
            case R.id.fl_double:
                homePresenter.betMoney(104);
                break;
            case R.id.fl_big:
                homePresenter.betMoney(101);
                break;
            case R.id.img_clear:
                homePresenter.resetStatue();
                break;
            case R.id.img_confirm:
                homePresenter.betMoney();
                break;
            case R.id.img_ten:
                selectTen();
                break;
            case R.id.img_fifty:
                selectFifty();
                break;
            case R.id.img_han:
                selectHan();
                break;
            case R.id.img_fifty_han:
                selectFiftyHan();
                break;
            case R.id.img_avatar:
                startActivity(new Intent(context, PersonalCenter.class));
                break;
        }
    }

    @Override
    public void OnDismiss(int flag) {
        switch (flag) {
            case Constants.WIN_POP_FLAG:
                winPop = null;
                break;
            case Constants.GO_BET_POP_FLAG:
                goOnBetPop = null;
                break;
            case Constants.NOTIFY_POP_FLAG:
                notifyPop = null;
                break;
            case Constants.UPDATE_POP_FLAG:
                updatePop = null;
                break;
        }
    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        switch (flag) {
            case Constants.UPDATE_POP_FLAG:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri downUri = Uri.parse(downUrl);
                intent.setData(downUri);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OnCancle(int flag) {

    }
}
