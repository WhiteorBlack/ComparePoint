package com.blm.comparepoint.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
import com.blm.comparepoint.dialog.RolePop;
import com.blm.comparepoint.dialog.UpdatePop;
import com.blm.comparepoint.dialog.WinPop;
import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.interfacer.OnItemClickListener;
import com.blm.comparepoint.interfacer.PopInterfacer;
import com.blm.comparepoint.presenter.HomePresenter;
import com.blm.comparepoint.untils.AppUtils;
import com.blm.comparepoint.untils.DensityUtils;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.ScreenUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.widget.MSGCountTimeView;
import com.blm.comparepoint.widget.RecycleViewDivider;
import com.blm.comparepoint.widget.ScrollTextView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.tencent.TIMManager;

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
    MSGCountTimeView txtCountDown;
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
    @BindView(R.id.txt_single_bet)
    TextView txtSingleBet;
    @BindView(R.id.ll_single)
    LinearLayout llSingle;
    @BindView(R.id.txt_small_bet)
    TextView txtSmallBet;
    @BindView(R.id.ll_small)
    LinearLayout llSmall;
    @BindView(R.id.txt_double_bet)
    TextView txtDoubleBet;
    @BindView(R.id.ll_doulbe)
    LinearLayout llDoulbe;
    @BindView(R.id.txt_big_bet)
    TextView txtBigBet;
    @BindView(R.id.ll_big)
    LinearLayout llBig;
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
    private RolePop rolePop;
    private String roleString;

    private int historyCount = 15;
    private int unselectedHeight = 0;
    private int selectedHeight = 0;
    private List gameConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        homePresenter = new HomePresenter(this);
        Constants.USER_ID = (String) SPUtils.get(context, Constants.GAMER_ID, "-1");
        initView();
        initNumData();
        initBetStatue();
        recyNumber.post(new Runnable() {
            @Override
            public void run() {
                numberAdapter.setHeight(recyNumber.getHeight());
                numberAdapter.notifyDataSetChanged();
            }
        });
        recyHistory.post(new Runnable() {
            @Override
            public void run() {
                betHistoryAdapter.setHeight(recyHistory.getHeight());
            }
        });

        selectTen();
        clearBetMoney();
        homePresenter.resetStatue();
        homePresenter.getSystemConfig();
        homePresenter.getGameConfig();
        homePresenter.getVersion();
        homePresenter.getCurrentInfo();
    }

    private void initNumData() {
        selectedHeight = (int) (ScreenUtils.getScreenWidth(context) / 8.5);
        unselectedHeight = selectedHeight * 3 / 4;
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
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l) + "");
        imgSign.setEnabled(!(boolean) SPUtils.get(context, Constants.ISSIGN, false));
        if ((boolean) SPUtils.get(context, Constants.ISSIGN, false)) {
            imgSign.setText("已签到");
        } else {
            imgSign.setText("签到");
        }
    }

    private void initData() {

        for (int i = 0; i < 14; i++) {
            Bean_GameConfig.GameConfig config = (Bean_GameConfig.GameConfig) gameConfig.get(i);
            if (i < 10) {
                Bean_BetNumber betNumber = new Bean_BetNumber();
                betNumber.betMutil = config.Ratio;
                betNumber.number = i + 1;
                betNumber.isSelected = false;
                betNumber.betGold = 0;
                betNumberList.add(betNumber);
            } else {
                switch (config.Number) {
                    case 101: //大
                        txtBigMutil.setText(config.Ratio + "");
                        break;
                    case 102: //小
                        txtSmallMutil.setText(config.Ratio + "");
                        break;
                    case 103:  //单
                        txtSingleMutil.setText(config.Ratio + "");
                        break;
                    case 104:  //双
                        txtDoubleMutil.setText(config.Ratio + "");
                        break;
                }
            }

        }
        betNumberAdapter.notifyDataSetChanged();

    }

    private void initView() {
        betHistoryList = new ArrayList();
        betNumberList = new ArrayList();
        numberList = new ArrayList();
        gameConfig = new ArrayList();

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
                if (Constants.SELECT_GOLD > (long) SPUtils.get(context, Constants.USERAMOUNT, 0l)) {
                    toastNotify("账户余额不足");
                    return;
                }
                homePresenter.betMoney(pos + 1);
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
        setPointSelected(imgTen);
        setPointUnselected(imgFifty);
        setPointUnselected(imgHan);
        setPointUnselected(imgFiftyHan);
    }

    /**
     * 选择 50 金币
     */
    private void selectFifty() {
        Constants.SELECT_GOLD = 50;
        setPointSelected(imgFifty);
        setPointUnselected(imgTen);
        setPointUnselected(imgHan);
        setPointUnselected(imgFiftyHan);
    }

    /**
     * 选择 100 金币
     */
    private void selectHan() {
        Constants.SELECT_GOLD = 100;
        setPointSelected(imgHan);
        setPointUnselected(imgFifty);
        setPointUnselected(imgTen);
        setPointUnselected(imgFiftyHan);
    }

    /**
     * 选择 500 金币
     */
    private void selectFiftyHan() {
        Constants.SELECT_GOLD = 500;
        setPointSelected(imgFiftyHan);
        setPointUnselected(imgFifty);
        setPointUnselected(imgHan);
        setPointUnselected(imgTen);
    }

    private void setPointSelected(View v) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
        params.height = params.width = selectedHeight;
        v.setLayoutParams(params);

    }

    private void setPointUnselected(View v) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
        params.height = params.width = unselectedHeight;
        v.setLayoutParams(params);

    }

    private void glideImage(int resId, ImageView imageView) {
        Glide.with(context).load(resId).into(imageView);
    }

    private void glideImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    private void initBetStatue() {
        llBig.setVisibility(View.GONE);
        llDoulbe.setVisibility(View.GONE);
        llSingle.setVisibility(View.GONE);
        llSmall.setVisibility(View.GONE);
        for (int i = 0; i < betNumberList.size(); i++) {
            ((Bean_BetNumber) betNumberList.get(i)).isSelected = false;
        }
        betNumberAdapter.notifyDataSetChanged();
    }

    @Override
    public void setNotify() {

    }

    @Override
    public void sigin(boolean successful, final long userBalance) {
        imgSign.setEnabled(!successful);
        SPUtils.put(context, Constants.ISSIGN, successful);
        if (successful) {
            imgSign.setText("已签到");
        } else {
            imgSign.setText("签到");
        }
        if (successful) {
            SPUtils.put(context, Constants.USERAMOUNT, userBalance);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtMoney.setText(userBalance + "");
                }
            });
        }

    }

    @Override
    public void betMoney(int pos) {
        resetBetStatue();
        if (pos > 10) {
            switch (pos) {
                case 101: //大
                    llBig.setVisibility(View.VISIBLE);
                    break;
                case 102: //小
                    llSmall.setVisibility(View.VISIBLE);
                    break;
                case 103:  //单
                    llSingle.setVisibility(View.VISIBLE);
                    break;
                case 104:  //双
                    llDoulbe.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            ((Bean_BetNumber) betNumberList.get(pos - 1)).isSelected = true;
            betNumberAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void toastNotify(String notify) {
        T.showShort(context, notify);
    }

    @Override
    public void getResult() {

    }

//    private CountDownTimer countDownTimer;

    @Override
    public void countDown(final int type, int time) {
        L.e("time----" + time);
        txtCountDown.isAllowRun(true);
        txtCountDown.setTotaltime(time * 1000);
        txtCountDown.setTextSize(14f);
        txtCountDown.setSuffixRuntext("秒");//设置运行时的文字的后缀
        switch (type) {
            case Constants.TYPE_BET_MONEY:
                txtCountDown.setFinishtext("开奖中");
                break;
            case Constants.TYPE_OPEN_CHESS:
                txtCountDown.setFinishtext("等待");
                break;
        }
        txtCountDown.startCount();
        txtCountDown.onDownTime(new MSGCountTimeView.onDownTime() {
            @Override
            public void onDown(int time) {
                if (type == Constants.TYPE_OPEN_CHESS) {
                    return;
                }
                if (time < 7) {
                    if (time % 2 > 0) {
                        txtCountDown.setTimeColor(Color.RED);
                    } else {
                        txtCountDown.setTimeColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onFinish() {
                if (type == Constants.TYPE_BET_MONEY) {
//                    countDown(Constants.TYPE_OPEN_CHESS,Constants.BONUSDOWNTIME);
                }
                homePresenter.endCountDown(type);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TIMManager.getInstance().logout();
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
    }

    @Override
    public void showWinPop(String money) {
        winPop = new WinPop(context);
        winPop.setMOney(money);
        winPop.setPopInterfacer(this, Constants.WIN_POP_FLAG);
        if (isFinishing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            winPop.showPop(txtBigMutil);
                        }
                    });
                }
            }, 300);
        } else {
            try {
                winPop.showPop(txtBigMutil);
            } catch (WindowManager.BadTokenException e) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                winPop.showPop(txtBigMutil);
                            }
                        });
                    }
                }, 300);
            }
        }
    }

    @Override
    public void showGoBet(String notify) {
        goOnBetPop = new GoOnBetPop(context);
        goOnBetPop.setNotify(notify);

        goOnBetPop.setPopInterfacer(this, Constants.GO_BET_POP_FLAG);
        if (isFinishing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goOnBetPop.showPop(txtBigMutil);
                        }
                    });
                }
            }, 300);
        } else {
            try {
                goOnBetPop.showPop(txtBigMutil);
            } catch (WindowManager.BadTokenException e) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                goOnBetPop.showPop(txtBigMutil);
                            }
                        });
                    }
                }, 300);
            }
        }
    }

    @Override
    public void showNotify(String notify) {
        notifyPop = new NotifyPop(context);
        notifyPop.setNotify(notify);
        notifyPop.showPop(txtBigMutil);
        notifyPop.setPopInterfacer(this, Constants.NOTIFY_POP_FLAG);
    }

    @Override
    public void betClick(int pos) {
        clearBetMoney();
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
        homePresenter.dismissAllPop();
        homePresenter.resetBetMoney();
        homePresenter.resetTotalBetMoney();
        initBetStatue();
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
        roleString = systemConfig.GameRules;
        SPUtils.put(context, Constants.MINEXCHANGEAMOUNT, systemConfig.MinWithdramAmount);
        SPUtils.put(context, Constants.SIGNBOUNS, systemConfig.SignBonus);
        Constants.ROUNDTIME = systemConfig.RoundTime;
        Constants.LOTTERYTIME = systemConfig.LotteryTime;
        Constants.GOldRANGE = systemConfig.GoldRange;
        SPUtils.put(context, Constants.CHARGEURL, Constants.PIC_URL + systemConfig.RechargeQrCode);
        SPUtils.put(context, Constants.SERVICEURL, Constants.PIC_URL + systemConfig.CustomerQrCode);
    }

    @Override
    public void setGameConfig(List<Bean_GameConfig.GameConfig> data) {
        this.gameConfig.addAll(data);
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
//        betHistoryList.addAll(currentInfo.LastBonusNum);
        if (currentInfo.LastBonusNum != null && currentInfo.LastBonusNum.length > 0) {
            for (int i = 0; i < currentInfo.LastBonusNum.length; i++) {
                if (i < historyCount + 1) {
                    Bean_CurrentInfo.BonusNumList num = new Bean_CurrentInfo.BonusNumList();
                    num.BonusNum = currentInfo.LastBonusNum[i];
                    betHistoryList.add(num);
                } else {
                    break;
                }

            }
            betHistoryAdapter.notifyDataSetChanged();
        }
        Constants.ROUNDNO = currentInfo.RoundNo;
        long systemTime = (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE) / 1000;
        int timeDelate = (int) (currentInfo.StartTime - systemTime);
        if (currentInfo.LotteryCost == 0 && Constants.LOTTERYTIME > 0) {
            currentInfo.LotteryCost = Constants.LOTTERYTIME;
        }
        if (currentInfo.LotteryCost == 0) {
            currentInfo.LotteryCost = 5;
        }
        Constants.COUNTDOWNTIME = timeDelate + (int) (currentInfo.BonusTime - currentInfo.LotteryCost - systemTime);
        Constants.BONUSDOWNTIME = (int) (currentInfo.BonusTime - systemTime);
        L.e("time--" + currentInfo.StartTime + "-system-" + systemTime + "--" + currentInfo.BonusTime + "--" + Constants.COUNTDOWNTIME + "--" + Constants.BONUSDOWNTIME + "--" + currentInfo.LotteryCost + Constants.LOTTERYTIME);
        if (Constants.COUNTDOWNTIME > 2) {
            countDown(Constants.TYPE_BET_MONEY, Constants.COUNTDOWNTIME - 1);
            Constants.ISBETABLE = true;
        } else {
            if (Constants.BONUSDOWNTIME > currentInfo.LotteryCost) {
                countDown(Constants.TYPE_BET_MONEY, Constants.BONUSDOWNTIME - currentInfo.LotteryCost);
                Constants.ISBETABLE = true;

            } else {
                countDown(Constants.TYPE_OPEN_CHESS, Constants.BONUSDOWNTIME);
                Constants.ISBETABLE = false;
            }

        }
    }

    @Override
    public void updateOnline(String count) {
        txtOnline.setText("在线人数: " + count);
    }

    @Override
    public void updateAmount(long money) {
        txtMoney.setText(money + "");
    }

    @Override
    public void updateBounsHistory(final int num) {

        Bean_CurrentInfo.BonusNumList bonusNumList = new Bean_CurrentInfo.BonusNumList();
        bonusNumList.BonusNum = num;
        betHistoryList.add(0, bonusNumList);
        if (betHistoryList.size() > historyCount) {
            betHistoryList.remove(betHistoryList.size() - 1);
        }
        betHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateRedAmount(long money) {
        long redAmount = (long) SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l);
        redAmount += money;
        txtRedMoney.setText(redAmount + "");
        SPUtils.put(context, Constants.ACTIVEAMOUNT, redAmount);
    }

    @Override
    public void setNotifyData(List<String> data) {
        if (data != null && data.size() > 0) {
            txtNotify.setVisibility(View.VISIBLE);
            txtNotify.setData(data);
        }
    }

    @Override
    public void dismissAllPop() {
        if (goOnBetPop != null && goOnBetPop.isShowing()) {
            goOnBetPop.dismiss();
        }
        if (notifyPop != null && notifyPop.isShowing()) {
            notifyPop.dismiss();
        }
        if (winPop != null && winPop.isShowing()) {
            winPop.dismiss();
        }
    }

    @Override
    public void clearBetMoney() {
        txtBigBet.setText("0");
        txtBigBet.setVisibility(View.GONE);
        txtSmallBet.setText("0");
        txtSmallBet.setVisibility(View.GONE);
        txtSingleBet.setText("0");
        txtSingleBet.setVisibility(View.GONE);
        txtDoubleBet.setText("0");
        txtDoubleBet.setVisibility(View.GONE);
        for (int i = 0; i < betNumberList.size(); i++) {
            ((Bean_BetNumber) betNumberList.get(i)).betGold = 0;

        }
        betNumberAdapter.notifyDataSetChanged();
    }

    @Override
    public void resetBetStatue() {
        initBetStatue();
    }


    @Override
    public void updateBetMoney(int pos, int money) {
        if (pos > 10) {
            switch (pos) {
                case 101: //大
                    txtBigBet.setVisibility(View.VISIBLE);
                    String bigBet = txtBigBet.getText().toString();
                    int bigBetInt = 0;
                    if (!TextUtils.isEmpty(bigBet)) {
                        try {
                            bigBetInt = Integer.parseInt(bigBet) + money;
                        } catch (Exception e) {
                            txtBigBet.setVisibility(View.GONE);
                        }
                    }
                    txtBigBet.setText(bigBetInt + "");
                    if (bigBetInt == 0) {
                        txtBigBet.setVisibility(View.GONE);
                    } else {
                        txtBigBet.setVisibility(View.VISIBLE);
                    }
                    break;
                case 102: //小
                    txtSmallBet.setVisibility(View.VISIBLE);
                    String smallBet = txtSmallBet.getText().toString();
                    int smallInt = 0;
                    if (!TextUtils.isEmpty(smallBet)) {
                        try {
                            smallInt = Integer.parseInt(smallBet) + money;
                        } catch (Exception e) {
                            txtSmallBet.setVisibility(View.GONE);
                        }
                    }
                    txtSmallBet.setText(smallInt + "");
                    if (smallInt == 0) {
                        txtSmallBet.setVisibility(View.GONE);
                    } else {
                        txtSmallBet.setVisibility(View.VISIBLE);
                    }
                    break;
                case 103:  //单
                    txtSingleBet.setVisibility(View.VISIBLE);
                    String singleBet = txtSingleBet.getText().toString();
                    int singleInt = 0;
                    if (!TextUtils.isEmpty(singleBet)) {
                        try {
                            singleInt = Integer.parseInt(singleBet) + money;
                        } catch (Exception e) {
                            txtSingleBet.setVisibility(View.GONE);
                        }
                    }
                    txtSingleBet.setText(singleInt + "");
                    if (singleInt == 0) {
                        txtSingleBet.setVisibility(View.GONE);
                    } else {
                        txtSingleBet.setVisibility(View.VISIBLE);
                    }
                    break;
                case 104:  //双
                    txtDoubleBet.setVisibility(View.VISIBLE);
                    String doubleBet = txtDoubleBet.getText().toString();
                    int doubleInt = 0;
                    if (!TextUtils.isEmpty(doubleBet)) {
                        try {
                            doubleInt = Integer.parseInt(doubleBet) + money;
                        } catch (Exception e) {
                            txtDoubleBet.setVisibility(View.GONE);
                        }
                    }
                    if (doubleInt == 0) {
                        txtDoubleBet.setVisibility(View.GONE);
                    } else {
                        txtDoubleBet.setVisibility(View.VISIBLE);
                    }
                    txtDoubleBet.setText(doubleInt + "");
                    break;
            }

        } else {
            ((Bean_BetNumber) betNumberList.get(pos - 1)).betGold += money;
            betNumberAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void betMoneySuccess(int money) {
        SPUtils.put(context, Constants.USERAMOUNT, ((long) SPUtils.get(context, Constants.USERAMOUNT, 0l) - money));
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
    }

    private int i = 0;

    @Override
    public void showBonusNumAnim() {
        new CountDownTimer(10 * 1000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {

                for (int j = 0; j < numberList.size(); j++) {
                    if (j == i) {
                        ((Bean_Number) numberList.get(j)).isShine = true;
                    } else {
                        ((Bean_Number) numberList.get(j)).isShine = false;
                    }

                    if (Constants.BONUSNUM == i + 1) {
                        break;
                    }

                }
                i++;
                if (i > numberList.size() - 1) {
                    i = 0;
                }
                numberAdapter.notifyDataSetChanged();
                if (Constants.BONUSEND) {
                    cancel();
//                        endBonusAnim();
                    return;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    @Override
    public void endBonusAnim(int num) {
        for (int j = 0; j < numberList.size(); j++) {
            if (j == num - 1) {
                ((Bean_Number) numberList.get(j)).isShine = true;
            } else {
                ((Bean_Number) numberList.get(j)).isShine = false;
            }
        }
        numberAdapter.notifyDataSetChanged();
        L.e("end ----" + num);
    }

    @Override
    public void resetNumStatue() {
        for (int j = 0; j < numberList.size(); j++) {
            ((Bean_Number) numberList.get(j)).isShine = false;
        }
        numberAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBetMoney(int pos, int amount) {
        if (pos > 10) {
            switch (pos) {
                case 101: //大
                    txtBigBet.setVisibility(View.VISIBLE);
                    String bigBet = txtBigBet.getText().toString();
                    int bigBetInt = 0;
                    if (!TextUtils.isEmpty(bigBet)) {
                        try {
                            bigBetInt = Integer.parseInt(bigBet) - amount;
                        } catch (Exception e) {
                            txtBigBet.setVisibility(View.GONE);
                        }
                    }
                    txtBigBet.setText(bigBetInt + "");
                    if (bigBetInt == 0) {
                        txtBigBet.setVisibility(View.GONE);
                    } else {
                        txtBigBet.setVisibility(View.VISIBLE);
                    }
                    break;
                case 102: //小
                    txtSmallBet.setVisibility(View.VISIBLE);
                    String smallBet = txtSmallBet.getText().toString();
                    int smallInt = 0;
                    if (!TextUtils.isEmpty(smallBet)) {
                        try {
                            smallInt = Integer.parseInt(smallBet) - amount;
                        } catch (Exception e) {
                            txtSmallBet.setVisibility(View.GONE);
                        }
                    }
                    txtSmallBet.setText(smallInt + "");
                    if (smallInt == 0) {
                        txtSmallBet.setVisibility(View.GONE);
                    } else {
                        txtSmallBet.setVisibility(View.VISIBLE);
                    }
                    break;
                case 103:  //单
                    txtSingleBet.setVisibility(View.VISIBLE);
                    String singleBet = txtSingleBet.getText().toString();
                    int singleInt = 0;
                    if (!TextUtils.isEmpty(singleBet)) {
                        try {
                            singleInt = Integer.parseInt(singleBet) - amount;
                        } catch (Exception e) {
                            txtSingleBet.setVisibility(View.GONE);
                        }
                    }
                    txtSingleBet.setText(singleInt + "");
                    if (singleInt == 0) {
                        txtSingleBet.setVisibility(View.GONE);
                    } else {
                        txtSingleBet.setVisibility(View.VISIBLE);
                    }
                    break;
                case 104:  //双
                    txtDoubleBet.setVisibility(View.VISIBLE);
                    String doubleBet = txtDoubleBet.getText().toString();
                    int doubleInt = 0;
                    if (!TextUtils.isEmpty(doubleBet)) {
                        try {
                            doubleInt = Integer.parseInt(doubleBet) - amount;
                        } catch (Exception e) {
                            txtDoubleBet.setVisibility(View.GONE);
                        }
                    }
                    if (doubleInt == 0) {
                        txtDoubleBet.setVisibility(View.GONE);
                    } else {
                        txtDoubleBet.setVisibility(View.VISIBLE);
                    }
                    txtDoubleBet.setText(doubleInt + "");
                    break;
            }
        } else {
            ((Bean_BetNumber) betNumberList.get(pos - 1)).betGold -= amount;
            betNumberAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resetAmount(long amount) {
        long account = (long) SPUtils.get(context, Constants.USERAMOUNT, 0l);
        account += amount;
        SPUtils.put(this, Constants.USERAMOUNT, account);
        txtMoney.setText(account + "");
    }

    private void endBonusAnim() {
        for (int j = 0; j < numberList.size(); j++) {
            ((Bean_Number) numberList.get(j)).isShine = false;
            if (j == Constants.BONUSNUM - 1) {
                ((Bean_Number) numberList.get(j)).isSelected = true;
            } else {
                ((Bean_Number) numberList.get(j)).isSelected = false;
            }
        }
        numberAdapter.notifyDataSetChanged();
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
                startActivity(new Intent(context, GoldRecord.class));
                break;
            case R.id.img_sign:
                homePresenter.signIn();
                break;
            case R.id.txt_red_money:
                startActivity(new Intent(context, RedGoldRecord.class));
                break;
            case R.id.txt_role:
                showRolePop();
                break;
            case R.id.txt_order:
                startActivity(new Intent(context, MyOrder.class));
                break;
            case R.id.fl_single:
                if (Constants.SELECT_GOLD > (long) SPUtils.get(context, Constants.USERAMOUNT, 0l)) {
                    toastNotify("账户余额不足");
                    return;
                }
                homePresenter.betMoney(103);
                break;
            case R.id.fl_small:
                if (Constants.SELECT_GOLD > (long) SPUtils.get(context, Constants.USERAMOUNT, 0l)) {
                    toastNotify("账户余额不足");
                    return;
                }
                homePresenter.betMoney(102);
                break;
            case R.id.fl_double:
                if (Constants.SELECT_GOLD > (long) SPUtils.get(context, Constants.USERAMOUNT, 0l)) {
                    toastNotify("账户余额不足");
                    return;
                }
                homePresenter.betMoney(104);
                break;
            case R.id.fl_big:
                if (Constants.SELECT_GOLD > (long) SPUtils.get(context, Constants.USERAMOUNT, 0l)) {
                    toastNotify("账户余额不足");
                    return;
                }
                homePresenter.betMoney(101);
                break;
            case R.id.img_clear:
//                homePresenter.resetStatue();
                homePresenter.clearBetMoney();
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

    private void showRolePop() {
        if (rolePop == null) {
            rolePop = new RolePop(context);
            rolePop.setPopInterfacer(this, Constants.ROLE_POP_FLAG);
        }
        rolePop.setRole(roleString);
        rolePop.showPop(txtBigMutil);
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
            case Constants.ROLE_POP_FLAG:
                rolePop = null;
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