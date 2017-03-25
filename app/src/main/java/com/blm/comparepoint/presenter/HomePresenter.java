package com.blm.comparepoint.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.blm.comparepoint.async.DataOprate;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.bean.Bean_AppVersion;
import com.blm.comparepoint.bean.Bean_BetMoney;
import com.blm.comparepoint.bean.Bean_Bets;
import com.blm.comparepoint.bean.Bean_CurrentInfo;
import com.blm.comparepoint.bean.Bean_SystemConfig;
import com.blm.comparepoint.interfacer.DataOprateInterfacer;
import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

/**
 * Created by 41508 on 2017/3/2.
 */

public class HomePresenter implements DataOprateInterfacer,Observer {
    private HomeOprateView homeOprateView;
    private DataOprate dataOprate;
    private List<Bean_BetMoney> betMoneyList;

    private TIMConversation conversation;

    public HomePresenter(HomeOprateView homeOprateView) {
        this.homeOprateView = homeOprateView;
        this.dataOprate = DataOprate.getInstance(this);
        betMoneyList = new ArrayList<>();

        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, Constants.GROUP_ID);
        conversation.disableStorage();
        MessageEvent.getInstance().addObserver(this);

    }

    private void resetBetMoney() {
        betMoneyList.clear();
        for (int i = 0; i < 14; i++) {
            Bean_BetMoney bean_betMoney=new Bean_BetMoney();
            bean_betMoney.BetGold=0;
            bean_betMoney.BetNum=-1;
            betMoneyList.add(new Bean_BetMoney());
        }
    }

    /**
     * 重置所有投注金额
     */
    public void resetStatue() {
        Constants.BET_SELECT_NUM = -1;
        resetBetMoney();
        homeOprateView.resetTable();
    }

    public void getSystemConfig() {
        homeOprateView.showDialog();
        dataOprate.getSystemConfig();
    }

    /**
     * 获取游戏配置
     */
    public void getGameConfig() {
        dataOprate.getGameConfig();
    }

    /**
     * 获取版本号
     */
    public void getVersion() {
        dataOprate.getVersion();
    }

    /**
     * 获取当前游戏信息
     */
    public void getCurrentInfo() {
        dataOprate.getCurrentInfo();
    }

    /**
     * 签到
     */

    public void signIn(){
        dataOprate.signIn();
    }

    /**
     * 点击确定押注
     */
    public void betMoney(){
        dataOprate.betMoney("");
    }

    public void betMoney(int num) {
        if (betMoneyList==null){
           resetBetMoney();
        }
        switch (num) {
            case -1:
                homeOprateView.toastCommonNotify("请选择投注类别");
                break;
            case 1:
                betMoneyList.get(0).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(0).BetNum=1;
                break;
            case 2:
                betMoneyList.get(1).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(1).BetNum=2;
                break;
            case 3:
                betMoneyList.get(2).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(2).BetNum=3;
                break;
            case 4:
                betMoneyList.get(3).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(3).BetNum=4;
                break;
            case 5:
                betMoneyList.get(4).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(4).BetNum=5;
                break;
            case 6:
                betMoneyList.get(5).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(5).BetNum=6;
                break;
            case 7:
                betMoneyList.get(6).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(6).BetNum=7;
                break;
            case 8:
                betMoneyList.get(7).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(7).BetNum=8;
                break;
            case 9:
                betMoneyList.get(8).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(8).BetNum=9;
                break;
            case 10:
                betMoneyList.get(9).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(9).BetNum=10;
                break;
            case 101: //大
                betMoneyList.get(10).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(10).BetNum=101;
                break;
            case 102: //小
                betMoneyList.get(11).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(11).BetNum=102;
                break;
            case 103:  //单
                betMoneyList.get(12).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(12).BetNum=103;
                break;
            case 104:  //双
                betMoneyList.get(13).BetGold+=Constants.SELECT_GOLD;
                betMoneyList.get(13).BetNum=104;
                break;
        }
    }

    public void endCountDown(int type){
        switch (type){
            case Constants.TYPE_BET_MONEY:

                break;
            case Constants.TYPE_OPEN_CHESS:

                break;
        }
    }

    @Override
    public void getSystemConfigSuccess(String response) {
        homeOprateView.dimissDialog();
        Bean_SystemConfig bean_systemConfig = new Gson().fromJson(response, Bean_SystemConfig.class);
        if (bean_systemConfig.Success) {
            homeOprateView.setSystemConfig(bean_systemConfig.Data);
        }
    }

    @Override
    public void getSystemConfigFail() {
        homeOprateView.dimissDialog();
    }

    @Override
    public void getGameCfgSuccess(String response) {
        homeOprateView.dimissDialog();
    }

    @Override
    public void getGameCfgFail() {
        homeOprateView.dimissDialog();
    }

    @Override
    public void signSuccess(String response) {
        if (TextUtils.isEmpty(response)) {
            homeOprateView.sigin(false);
            homeOprateView.toastNotify("网络错误请重试");
            return;
        }
        BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
        if (baseBean.Success) {
            homeOprateView.sigin(true);
        } else {
            homeOprateView.sigin(false);
            homeOprateView.toastNotify(baseBean.Msg);
        }
    }

    @Override
    public void signFail() {

    }

    @Override
    public void getVersion(String response) {
        if (TextUtils.isEmpty(response)) {
            return;
        }
        Bean_AppVersion bean_appVersion = new Gson().fromJson(response, Bean_AppVersion.class);
        if (bean_appVersion.Success && bean_appVersion != null) {
            homeOprateView.checkUpdate(bean_appVersion.Data);
        }
    }

    @Override
    public void getCurrentInfo(String response) {
        if (TextUtils.isEmpty(response)) {
            retryGameInfo();
            return;
        }
        Bean_CurrentInfo bean_CurrentInfo = new Gson().fromJson(response, Bean_CurrentInfo.class);
        if (bean_CurrentInfo.Success && bean_CurrentInfo != null) {
            homeOprateView.currentInfo(bean_CurrentInfo.Data);
        }
        if (time == 6) {
            homeOprateView.toastNotify("获取游戏信息失败,请重试!");
            time = 1;
        }
    }

    @Override
    public void getNetDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return;
        }
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            try {
                Date serverDate = simpleDateFormat.parse(date);
                Constants.NETTIME_LOCALTIME_DELATE = serverDate.getTime() - System.currentTimeMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private int time = 1;

    private void retryGameInfo() {
        for (int i = 0; i < 5; i++) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCurrentInfo();
                }
            }, 1000 * time);
            time++;
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
        }
    }
}
