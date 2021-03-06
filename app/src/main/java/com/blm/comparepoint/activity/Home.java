package com.blm.comparepoint.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.MyReceiver;
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
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.AppUtils;
import com.blm.comparepoint.untils.DensityUtils;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.ScreenUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.AutoHorizontalScrollTextView;
import com.blm.comparepoint.widget.AutoScrollTextView;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.widget.MSGCountTimeView;
import com.blm.comparepoint.widget.RecycleViewDivider;
import com.blm.comparepoint.widget.ScrollTextView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.tencent.TIMManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class Home extends BaseActivity implements HomeOprateView, PopInterfacer {

    @BindView(R.id.txt_notify)
    AutoScrollTextView txtNotify;
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
    //    @BindView(R.id.txt_count_down)
//    MSGCountTimeView txtCountDown;
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
//    private List<String> notifyData;

    //popwindow
    private WinPop winPop;
    private GoOnBetPop goOnBetPop;
    private NotifyPop notifyPop;
    private ProgressDialog progressDialog;
    private UpdatePop updatePop;
    private RolePop rolePop;
    private String roleString;
    private String chargeString;
    private RolePop chargeNotifyPop;

    private int historyCount = 15;
    private int unselectedHeight = 0;
    private int selectedHeight = 0;
    private List gameConfig;
    private MSGCountTimeView txtCountDown;

    public static boolean isForeground;
    private long getInfoTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        homePresenter = new HomePresenter(this);
        Constants.USER_ID = (String) SPUtils.get(context, Constants.GAMER_ID, "-1");
        Constants.USERTOKEN = (String) SPUtils.get(context, Constants.TOKEN, "");
        initView();
        initNumData();
        initBetStatue();
        initBetNumData();
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, (String) SPUtils.get(context, Constants.USERNAME, "")));
//        JPushInterface.setAliasAndTags(getApplicationContext(), (String) SPUtils.get(context, Constants.USERNAME, ""),null, mAliasCallback);
        registerMessageReceiver();
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

        Constants.ISSTART = true;

        selectTen();
        clearBetMoney();
        homePresenter.resetStatue();
        homePresenter.getSystemConfig();
        homePresenter.getGameConfig();
        homePresenter.getVersion();
        homePresenter.getCurrentInfo();
        getInfoTime = System.currentTimeMillis();
    }

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("Jpush", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i("Jpush", "Unhandled msg - " + msg.what);
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("Jpush", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("Jpush", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 10);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("Jpush", logs);
            }
        }
    };

    private void initBetNumData() {
        for (int j = 0; j < 10; j++) {
            Bean_BetNumber betNumber = new Bean_BetNumber();
            betNumber.number = j + 1;
            betNumber.isSelected = false;
            betNumberList.add(betNumber);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        L.e("level--" + level);
        if (level > TRIM_MEMORY_MODERATE) {
            if (TextUtils.isEmpty(Constants.USERTOKEN)){
                Constants.USERTOKEN = (String) SPUtils.get(context, Constants.TOKEN, "");
            }
            if (TextUtils.isEmpty(Constants.USER_ID)){
                Constants.USER_ID = (String) SPUtils.get(context, Constants.GAMER_ID, "-1");
            }
            homePresenter.checkConversion();
//            AppManager.getAppManager().AppExit(this);
        }
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

    //for receive customer msg from jpush server
    private MyReceiver myReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.blm.comparepoint.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(myReceiver, filter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
        this.isForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.isForeground = false;
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

                Bean_BetNumber betNumber = (Bean_BetNumber) betNumberList.get(i);
                betNumber.betMutil = config.Ratio;
                betNumber.number = i + 1;
                betNumber.isSelected = false;
//                betNumber.betGold = 0;
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
        txtCountDown = (MSGCountTimeView) findViewById(R.id.txt_count_down);
        txtCountDown.setTextSize(14f);
        txtCountDown.setSuffixRuntext("秒");//设置运行时的文字的后缀
        txtCountDown.setInittext("准备中");

        txtNotify.init(getWindowManager());
        betHistoryList = new ArrayList();
        betNumberList = new ArrayList();
        numberList = new ArrayList();
        gameConfig = new ArrayList();

        betHistoryAdapter = new BetHistoryAdapter(betHistoryList);
        recyHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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


    @Override
    public void countDown(final int type, int time) {

        txtCountDown.setFinishtext("开奖中");
        txtCountDown.setTotaltime(time * 1000);
        txtCountDown.startCount();

        switch (type) {
            case Constants.TYPE_BET_MONEY:
                Constants.ISCOUNTDOWN = true;
                break;
            case Constants.TYPE_OPEN_CHESS:
                break;
        }

        txtCountDown.onDownTime(new MSGCountTimeView.onDownTime() {
            @Override
            public void onDown(final int time) {
                if (time > 0) {
                    Constants.ISCOUNTDOWN = true;
                }
                if (time == 53) {
                    dismissBetPop();
                }
                if (time < 7) {
                    if (time % 2 > 0) {
                        txtCountDown.setTextColor(Color.RED);
                    } else {
                        txtCountDown.setTextColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onFinish() {
                Constants.ISCOUNTDOWN = false;
                if (type == Constants.TYPE_BET_MONEY) {
                    if (Constants.BONUSNUM > 0) {
                        showBonusNumAnim(Constants.BONUSNUM);
                    }
                }
                homePresenter.endCountDown(type);
            }
        });

    }

    /**
     * 延迟2s时间消除赢和输的弹窗
     */
    private void dismissBetPop() {
        if (goOnBetPop != null && goOnBetPop.isShowing()) {
            goOnBetPop.dismiss();
        }
        if (winPop != null && winPop.isShowing()) {
            winPop.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        TIMManager.getInstance().logout();
        homePresenter.onDestory();
        txtCountDown.destoryed();
        AppManager.getAppManager().AppExit(this);
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
        chargeString = systemConfig.ReChargeRules;
        SPUtils.put(context, Constants.MINEXCHANGEAMOUNT, systemConfig.MinWithdramAmount);
        SPUtils.put(context, Constants.SIGNBOUNS, systemConfig.SignBonus);
        Constants.ROUNDTIME = systemConfig.RoundCost;
        Constants.LOTTERYTIME = systemConfig.LotteryTime;
        Constants.GOldRANGE = systemConfig.GoldRange;
        SPUtils.put(context, Constants.CHARGEURL, Constants.PIC_URL + systemConfig.RechargeWePayQrCode);
        SPUtils.put(context, Constants.CHARGEALIURL, Constants.PIC_URL + systemConfig.RechargeAliPayQrCode);
        SPUtils.put(context, Constants.CHARGEDESC, TextUtils.isEmpty(systemConfig.RechargeDesc)?"":systemConfig.RechargeDesc);
        SPUtils.put(context, Constants.SERVICEURL, Constants.PIC_URL + systemConfig.CustomerQrCode);
        SPUtils.put(context,Constants.CONVERTDESC,TextUtils.isEmpty(systemConfig.WithdrawDesc)?"":systemConfig.WithdrawDesc);
    }

    @Override
    public void setGameConfig(List<Bean_GameConfig.GameConfig> data) {
        this.gameConfig.addAll(data);
        initData();
    }


    private String downUrl;

    @Override
    public void checkUpdate(Bean_AppVersion.AppVersion appVersion) {
        if (!TextUtils.equals(appVersion.VisionName, AppUtils.getVersionName(context))) {
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
    public void currentInfo(Bean_CurrentInfo.CurrentInfo currentInfo, long time) {
        if (currentInfo.LastBonusNum != null && currentInfo.LastBonusNum.size() > 0) {
            betHistoryList.clear();
            for (int i = 0; i < currentInfo.LastBonusNum.size(); i++) {
                if (i < historyCount) {
                    betHistoryList.add(currentInfo.LastBonusNum.get(i));
                } else {
                    break;
                }

            }
            betHistoryAdapter.notifyDataSetChanged();
        }
        Constants.ROUNDNO = currentInfo.RoundNo;
//        Constants.NETTIME_LOCALTIME_DELATE=currentInfo.BonusTime;
//
//        long systemTime = (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE) / 1000;
//
//        if (currentInfo.LotteryCost == 0 && Constants.LOTTERYTIME > 0) {
//            currentInfo.LotteryCost = Constants.LOTTERYTIME;
//        }
//        if (currentInfo.LotteryCost == 0) {
//            currentInfo.LotteryCost = 5;
//        }
//        Constants.COUNTDOWNTIME = (int) (currentInfo.BonusTime - systemTime);
//        Constants.BONUSDOWNTIME = (int) (currentInfo.BonusTime - systemTime);
        getInfoTime=System.currentTimeMillis()-getInfoTime;
        L.e("getinfoTime-- "+getInfoTime);
        Constants.COUNTDOWNTIME = (int) (currentInfo.BonusTime - time / 1000-getInfoTime/1000);
        Constants.ISBETABLE = true;
        if (Constants.COUNTDOWNTIME < 2) {
            txtCountDown.setFinishtext("准备中");
            Constants.ISSTART = true;
        } else {
            Constants.ISSTART = false;
            countDown(Constants.TYPE_BET_MONEY, Constants.COUNTDOWNTIME);
        }
//        initNumber();
//        numberAdapter.notifyDataSetChanged();
        if (currentInfo.BetRecords != null && currentInfo.BetRecords.size() > 0) {
            for (int j = 0; j < currentInfo.BetRecords.size(); j++) {
                Bean_CurrentInfo.BetRecords betRecords = currentInfo.BetRecords.get(j);
                if (betRecords.BetNumber > 10) {
                    switch (betRecords.BetNumber) {
                        case 101: //大
                            txtBigBet.setText(betRecords.BetGold + "");
                            txtBigBet.setVisibility(View.VISIBLE);
                            break;
                        case 102: //小
                            txtSmallBet.setText(betRecords.BetGold + "");
                            txtSmallBet.setVisibility(View.VISIBLE);
                            break;
                        case 103: //单
                            txtSingleBet.setText(betRecords.BetGold + "");
                            txtSingleBet.setVisibility(View.VISIBLE);
                            break;
                        case 104:  //双
                            txtDoubleBet.setText(betRecords.BetGold + "");
                            txtDoubleBet.setVisibility(View.VISIBLE);
                            break;
                    }
                } else if (betNumberList.size() > 0) {
                    if (betNumberList != null && betNumberList.size() > 0) {
                        ((Bean_BetNumber) betNumberList.get(betRecords.BetNumber - 1)).betGold = betRecords.BetGold;
                    }
                }
            }
            betNumberAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void gameInfo(Bean_CurrentInfo.CurrentInfo currentInfo) {
        Constants.ISSTART = false;
        Constants.ROUNDNO = currentInfo.RoundNo;
        long systemTime = (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE) / 1000;
        if (currentInfo.LotteryCost == 0 && Constants.LOTTERYTIME > 0) {
            currentInfo.LotteryCost = Constants.LOTTERYTIME;
        }
        if (currentInfo.LotteryCost == 0) {
            currentInfo.LotteryCost = 5;
        }
        Constants.COUNTDOWNTIME = (int) (currentInfo.BonusTime - systemTime);
        Constants.BONUSDOWNTIME = (int) (currentInfo.BonusTime - systemTime);

        Constants.ISBETABLE = true;
        //这里把时间写死,防止有些手机出现时间少或者多的问题
        countDown(Constants.TYPE_BET_MONEY, Constants.ROUNDTIME);

    }

    @Override
    public void updateOnline(String count) {
        txtOnline.setText("在线人数: " + count);
    }

    @Override
    public void updateAmount(long money) {
        SPUtils.put(context, Constants.USERAMOUNT, (long) SPUtils.get(context, Constants.USERAMOUNT, 0l) + money);
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
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
        Intent intent = new Intent();
        intent.setAction("action.UpdateRedAmount");
        sendBroadcast(intent);
    }

    @Override
    public void setNotifyData(List<String> data) {
        if (data != null && data.size() > 0) {
            txtNotify.setVisibility(View.VISIBLE);
            String notifyString = "";
            for (int j = 0; j < data.size(); j++) {
                notifyString += data.get(j) + "                          ";
            }
            L.e("notify---" + notifyString);
            notifyString += notifyString;
            txtNotify.setText(notifyString);
            txtNotify.startScroll();
        }
    }

    @Override
    public void dismissAllPop() {
//        if (goOnBetPop != null && goOnBetPop.isShowing()) {
//            goOnBetPop.dismiss();
//        }
        if (notifyPop != null && notifyPop.isShowing()) {
            notifyPop.dismiss();
        }
//        if (winPop != null && winPop.isShowing()) {
//            winPop.dismiss();
//        }
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
    private boolean isStop = false;

    @Override
    public void showBonusNumAnim(final int num) {
        if (Constants.ISCOUNTDOWN) {
            return;
        }
        isStop = false;
        i = 0;
        if (Constants.LOTTERYTIME == 0) {
            Constants.LOTTERYTIME = 5;
        }
        new CountDownTimer(Constants.LOTTERYTIME * 1000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isStop) {
                    return;
                }
                if (isFinishing()) {
                    cancel();
                }
                initNumber();

                ((Bean_Number) numberList.get(i)).isShine = true;
                numberAdapter.notifyDataSetChanged();
                if (millisUntilFinished < 2000 && i == num - 1) {
                    isStop = true;
                    homePresenter.countBetMoney(num);
                    cancel();
                    return;
                }
                i++;
                if (i > numberList.size() - 1) {
                    i = 0;
                }

            }

            @Override
            public void onFinish() {
//                initNumber();
//                numberAdapter.notifyDataSetChanged();
            }
        }.start();

    }

    private void initNumber() {
        for (int j = 0; j < numberList.size(); j++) {
            ((Bean_Number) numberList.get(j)).isShine = false;
        }
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
        L.e("resetNumStatue");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initNumber();
                numberAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setBetMoney(int pos, int amount) {
        L.e("amount---" + amount);
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

    @Override
    public void currentInfoAfater(long time) {
//        Constants.COUNTDOWNTIME= (int) (Constants.NETTIME_LOCALTIME_DELATE-ti/1000);
//        Constants.ISBETABLE = true;
//        if (Constants.COUNTDOWNTIME < 2) {
//            txtCountDown.setFinishtext("准备中");
//            Constants.ISSTART = true;
//        } else {
//            Constants.ISSTART = false;
//            countDown(Constants.TYPE_BET_MONEY, Constants.COUNTDOWNTIME);
//        }
    }


    @OnClick({R.id.txt_online, R.id.txt_notify, R.id.txt_name, R.id.txt_money, R.id.img_sign, R.id.txt_red_money, R.id.img_avatar,
            R.id.txt_role, R.id.txt_order, R.id.fl_single, R.id.fl_small, R.id.fl_double, R.id.fl_big,
            R.id.img_clear, R.id.img_confirm, R.id.img_ten, R.id.img_fifty, R.id.img_han, R.id.img_fifty_han})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_online:
//                startActivity(new Intent(context, ServiceCenter.class));
                showChargePop();
                break;
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
                if (Constants.TOTALBETMONEY > (long) SPUtils.get(context, Constants.USERAMOUNT, 0l)) {
                    toastNotify("账户余额不足");
                    return;
                }
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

    private void showChargePop() {
        if (chargeNotifyPop == null) {
            chargeNotifyPop = new RolePop(context);
            chargeNotifyPop.setPopInterfacer(this, Constants.ROLE_POP_FLAG);
        }
        chargeNotifyPop.setRole(chargeString);
        chargeNotifyPop.showPop(txtBigMutil);
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
                if (updatePop!=null){
                    updatePop.dismiss();
                }
                break;
        }
    }

    @Override
    public void OnCancle(int flag) {

    }
}