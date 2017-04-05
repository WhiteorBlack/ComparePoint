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
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.ScreenUtils;
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

    private int historyCount = 10;
    private int unselectedHeight = 0;
    private int selectedHeight = 0;
    private List gameConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter = new HomePresenter(this);
        Constants.USER_ID = (String) SPUtils.get(context, Constants.GAMER_ID, "-1");
        initView();
        initNumData();
        initBetStatue();
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
                historyCount = recyHistory.getHeight() / DensityUtils.dp2px(context, 26);
            }
        });

        selectTen();

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

    private CountDownTimer countDownTimer;

    @Override
    public void countDown(final int type, int time) {
        countDownTimer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinishing()) {
                    cancel();
                }
                int leftTIme = (int) (millisUntilFinished / 1000);
                if (leftTIme < 6) {
                    if (leftTIme % 2 > 0) {
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
        SPUtils.put(context, Constants.SERVICEURL, systemConfig.RechargeQrCode);
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
                Bean_CurrentInfo.BonusNumList num = new Bean_CurrentInfo.BonusNumList();
                num.BonusNum = currentInfo.LastBonusNum[i];
                betHistoryList.add(num);
            }
            betHistoryAdapter.notifyDataSetChanged();
        }
        Constants.ROUNDNO = currentInfo.RoundNo;
        int timeDelate = (int) (currentInfo.StartTime - (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE) / 1000);
        Constants.COUNTDOWNTIME = timeDelate + (int) (currentInfo.BonusTime - currentInfo.LotteryCost - (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE) / 1000);
        Constants.BONUSDOWNTIME = (int) (currentInfo.BonusTime - (System.currentTimeMillis() + Constants.NETTIME_LOCALTIME_DELATE) / 1000);
        L.e("time--" + currentInfo.StartTime + "-system-" + System.currentTimeMillis() + "--" + Constants.COUNTDOWNTIME);
        if (Constants.COUNTDOWNTIME > 1) {
            countDown(Constants.TYPE_BET_MONEY, Constants.COUNTDOWNTIME);
            Constants.ISBETABLE = true;
        } else {
            Constants.ISBETABLE = false;
//            countDown(Constants.TYPE_OPEN_CHESS, Constants.BONUSDOWNTIME);
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
    public void updateBounsHistory(int num) {

    }

    @Override
    public void updateRedAmount(long money) {
        txtRedMoney.setText(money + "");
    }

    @Override
    public void setNotifyData(List<String> data) {
        if (data != null && data.size() > 0) {
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
                    txtBigBet.setText(money + "");
                    break;
                case 102: //小
                    txtSmallBet.setVisibility(View.VISIBLE);
                    txtSmallBet.setText(money + "");
                    break;
                case 103:  //单
                    txtSingleBet.setVisibility(View.VISIBLE);
                    txtSingleBet.setText(money + "");
                    break;
                case 104:  //双
                    txtDoubleBet.setVisibility(View.VISIBLE);
                    txtDoubleBet.setText(money + "");
                    break;
            }
        } else {
            ((Bean_BetNumber) betNumberList.get(pos - 1)).betGold = money;
            betNumberAdapter.notifyDataSetChanged();
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