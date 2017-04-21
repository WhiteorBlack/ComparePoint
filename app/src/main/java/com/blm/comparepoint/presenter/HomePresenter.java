package com.blm.comparepoint.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.blm.comparepoint.async.DataOprate;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.bean.Bean_AppVersion;
import com.blm.comparepoint.bean.Bean_BetMoney;
import com.blm.comparepoint.bean.Bean_BonusTip;
import com.blm.comparepoint.bean.Bean_Bouns;
import com.blm.comparepoint.bean.Bean_CurrentInfo;
import com.blm.comparepoint.bean.Bean_GameConfig;
import com.blm.comparepoint.bean.Bean_Msg;
import com.blm.comparepoint.bean.Bean_OnLine;
import com.blm.comparepoint.bean.Bean_Recharge;
import com.blm.comparepoint.bean.Bean_Round;
import com.blm.comparepoint.bean.Bean_Sign;
import com.blm.comparepoint.bean.Bean_SystemConfig;
import com.blm.comparepoint.interfacer.DataOprateInterfacer;
import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

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

public class HomePresenter implements DataOprateInterfacer, Observer {
    private HomeOprateView homeOprateView;
    private DataOprate dataOprate;
    private List<Bean_BetMoney> betMoneyList;
    private List<Bean_GameConfig.GameConfig> ratoList;
    private List<Bean_BetMoney> totalBetMoneyList;

    private TIMConversation conversation;

    public HomePresenter(HomeOprateView homeOprateView) {
        this.homeOprateView = homeOprateView;
        this.dataOprate = DataOprate.getInstance(this);
        betMoneyList = new ArrayList<>();
        totalBetMoneyList = new ArrayList<>();
        ratoList = new ArrayList<>();

        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, Constants.GROUP_ID);
        conversation.disableStorage();
        MessageEvent.getInstance().addObserver(this);

    }

    public void resetTotalBetMoney() {
        totalBetMoneyList.clear();
        for (int i = 0; i < 14; i++) {
            Bean_BetMoney bean_betMoney = new Bean_BetMoney();
            bean_betMoney.BetGold = 0;
            bean_betMoney.BetNum = -1;
            totalBetMoneyList.add(new Bean_BetMoney());
        }
//        homeOprateView.clearBetMoney();
    }

    public void resetBetMoney() {
        betMoneyList.clear();
        for (int i = 0; i < 14; i++) {
            Bean_BetMoney bean_betMoney = new Bean_BetMoney();
            bean_betMoney.BetGold = 0;
            bean_betMoney.BetNum = -1;
            betMoneyList.add(new Bean_BetMoney());
        }
//        homeOprateView.clearBetMoney();
    }

    public void dismissAllPop() {
        homeOprateView.dismissAllPop();
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
     * 重置所有选择状态
     */
    public void resetBetStatue() {
        homeOprateView.resetBetStatue();
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

    public void signIn() {
        dataOprate.signIn();
    }

    /**
     * 点击确定押注
     */
    public void betMoney() {
        Constants.ISBETABLE = false;
        List<Bean_BetMoney> betParams = new ArrayList<>();
        for (int i = 0; i < betMoneyList.size(); i++) {
            if (betMoneyList.get(i).BetGold > 0) {
                betParams.add(betMoneyList.get(i));
            }
        }
        String bets = new Gson().toJson(betParams);
        dataOprate.betMoney(bets);
    }

    public void betMoney(int num) {
        if (betMoneyList == null) {
            resetBetMoney();
        }
        if (!Constants.ISBETABLE) {
            return;
        }
//        homeOprateView.betMoney(num);
        switch (num) {
            case -1:
                homeOprateView.toastCommonNotify("请选择投注类别");
                break;
            case 1:
                betMoneyList.get(0).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(0).BetNum = 1;
                break;
            case 2:
                betMoneyList.get(1).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(1).BetNum = 2;
                break;
            case 3:
                betMoneyList.get(2).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(2).BetNum = 3;
                break;
            case 4:
                betMoneyList.get(3).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(3).BetNum = 4;
                break;
            case 5:
                betMoneyList.get(4).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(4).BetNum = 5;
                break;
            case 6:
                betMoneyList.get(5).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(5).BetNum = 6;
                break;
            case 7:
                betMoneyList.get(6).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(6).BetNum = 7;
                break;
            case 8:
                betMoneyList.get(7).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(7).BetNum = 8;
                break;
            case 9:
                betMoneyList.get(8).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(8).BetNum = 9;
                break;
            case 10:
                betMoneyList.get(9).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(9).BetNum = 10;
                break;
            case 101: //大
                betMoneyList.get(10).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(10).BetNum = 101;
                break;
            case 102: //小
                betMoneyList.get(11).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(11).BetNum = 102;
                break;
            case 103:  //单
                betMoneyList.get(12).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(12).BetNum = 103;
                break;
            case 104:  //双
                betMoneyList.get(13).BetGold += Constants.SELECT_GOLD;
                betMoneyList.get(13).BetNum = 104;
                break;
        }
        /*if (num > 10) {
            switch (num) {
                case 101: //大
                    homeOprateView.updateBetMoney(num, betMoneyList.get(10).BetGold);
                    break;
                case 102: //小
                    homeOprateView.updateBetMoney(num, betMoneyList.get(11).BetGold);
                    break;
                case 103:  //单
                    homeOprateView.updateBetMoney(num, betMoneyList.get(12).BetGold);
                    break;
                case 104:  //双
                    homeOprateView.updateBetMoney(num, betMoneyList.get(13).BetGold);
                    break;
            }
        } else {
            homeOprateView.updateBetMoney(num, betMoneyList.get(num - 1).BetGold);
        }*/
        homeOprateView.updateBetMoney(num, Constants.SELECT_GOLD);
        homeOprateView.resetAmount(-Constants.SELECT_GOLD);
    }

    public void endCountDown(int type) {
        switch (type) {
            case Constants.TYPE_BET_MONEY:
                clearBetMoney();
                break;
            case Constants.TYPE_OPEN_CHESS:

                break;
        }
    }

    /**
     * 清除没有提交的押注信息
     */
    public void clearBetMoney() {
        long clearMoney = 0;
        for (int i = 0; i < betMoneyList.size(); i++) {
            if (betMoneyList.get(i).BetGold > 0) {
                homeOprateView.setBetMoney(betMoneyList.get(i).BetNum, betMoneyList.get(i).BetGold);
                clearMoney += betMoneyList.get(i).BetGold;
                homeOprateView.setBetMoney(i, betMoneyList.get(i).BetGold);
            }
        }
        homeOprateView.resetAmount(clearMoney);
        resetBetMoney();
        resetBetStatue();
    }

    @Override
    public void getSystemConfigSuccess(String response) {
        homeOprateView.dimissDialog();
        Bean_SystemConfig bean_systemConfig = new Gson().fromJson(response, Bean_SystemConfig.class);
        if (bean_systemConfig.Success && bean_systemConfig.Data != null) {
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
        Bean_GameConfig bean_gameConfig = new Gson().fromJson(response, Bean_GameConfig.class);
        if (bean_gameConfig != null && bean_gameConfig.Success && bean_gameConfig.Data != null) {
            homeOprateView.setGameConfig(bean_gameConfig.Data);
            ratoList.addAll(bean_gameConfig.Data);
        }
    }

    @Override
    public void getGameCfgFail() {
        homeOprateView.dimissDialog();
    }

    @Override
    public void signSuccess(String response) {
        if (TextUtils.isEmpty(response)) {
            homeOprateView.sigin(false, -1);
            homeOprateView.toastNotify("网络错误请重试");
            return;
        }
        Bean_Sign baseBean = new Gson().fromJson(response, Bean_Sign.class);
        if (baseBean.Data != null) {
            if (baseBean.Success) {
                homeOprateView.sigin(true, baseBean.Data.UserBalance);
            } else {
                homeOprateView.sigin(false, baseBean.Data.UserBalance);
            }
        }

        homeOprateView.toastNotify(baseBean.Msg);
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
        if (bean_appVersion.Success && bean_appVersion != null && bean_appVersion.Data != null) {
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

    @Override
    public void betMoneyResult(String response) {
        if (TextUtils.isEmpty(response)) {
            homeOprateView.toastNotify("请检查后重试!");
            return;
        }
        BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
        if (baseBean.Success) {
            Constants.IS_BET = true;
//            int betCount = 0;
            for (int i = 0; i < betMoneyList.size(); i++) {
                Bean_BetMoney betMoney = betMoneyList.get(i);
                if (betMoney.BetGold > 0) {
                    totalBetMoneyList.get(i).BetGold += betMoney.BetGold;
                    totalBetMoneyList.get(i).BetNum = betMoney.BetNum;
                }
            }
//            homeOprateView.betMoneySuccess(betCount);
//            homeOprateView.resetTable();
        }
        Constants.ISBETABLE = true;
        resetBetMoney();
        homeOprateView.toastNotify(baseBean.Msg);
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
            if (TextUtils.equals(Constants.MSG_ID, msg.getMsgId())) {
                return;
            }
            Constants.MSG_ID = msg.getMsgId();
            TIMGroupSystemElem elem = null;
            try {
                elem = (TIMGroupSystemElem) msg.getElement(0);
                L.e("Msg----" + msg.getMsgId() + msg.getConversation().getType() + new String(elem.getUserData()));
            } catch (Exception e) {

            }
            if (elem == null) {
                return;
            }
            String msgString = new String(elem.getUserData());
            if (TextUtils.isEmpty(msgString)) {
                return;
            }
            Bean_Msg bean_msg = new Gson().fromJson(msgString, Bean_Msg.class);
            if (TextUtils.equals(bean_msg.Type, "OnLineNumber")) {
                //更新在线人数
                Bean_OnLine onLine = new Gson().fromJson(msgString, Bean_OnLine.class);
                homeOprateView.updateOnline((onLine.Data + 1) + "");
            }

            if (TextUtils.equals("Round", bean_msg.Type)) {
                //游戏开局信息
                Constants.BONUSEND = false;
                Constants.IS_BET = false;
                Constants.ISBETABLE = true;
                Constants.BONUSNUM = -1;
                Bean_Round round = new Gson().fromJson(msgString, Bean_Round.class);
                Constants.LOTTERYTIME = round.Data.LotteryCost;
                homeOprateView.clearBetMoney();
                homeOprateView.resetTable();
                homeOprateView.resetNumStatue();
                homeOprateView.currentInfo(round.Data);
            }

            if (TextUtils.equals("BounsTip", bean_msg.Type)) {
                //更新获奖人的滚动信息
                Bean_BonusTip bonusTip = new Gson().fromJson(msgString, Bean_BonusTip.class);
                if (bonusTip.Data != null && bonusTip.Data.size() > 0) {
                    homeOprateView.setNotifyData(bonusTip.Data);
                }
            }
            if (TextUtils.equals("Recharge", bean_msg.Type)) {
                //充值信息
                Bean_Recharge recharge = new Gson().fromJson(msgString, Bean_Recharge.class);
                if (TextUtils.equals(recharge.Data.GameUserId, Constants.USER_ID)) {
                    homeOprateView.updateAmount(recharge.Data.RechargeGold);
                }
            }
            if (TextUtils.equals("Bonus", bean_msg.Type)) {
                if (!Constants.IS_BET) {
                    homeOprateView.clearBetMoney();
                }
                Constants.ISBETABLE = false;
                Bean_Bouns bouns = new Gson().fromJson(msgString, Bean_Bouns.class);
                Constants.BONUSEND = true;
                Constants.BONUSNUM = bouns.Data.BonusNum;
                homeOprateView.updateBounsHistory(Constants.BONUSNUM);
//                countBetMoney(Constants.BONUSNUM);
                homeOprateView.showBonusNumAnim(bouns.Data.BonusNum);
            }
        }
    }


    public void countBetMoney(int bonusNum) {
        boolean isSingle = bonusNum % 2 > 0;
        boolean isBig = bonusNum > 5;
        int money = 0;
        if (ratoList != null) {
            for (int i = 0; i < totalBetMoneyList.size(); i++) {
                int betGole = totalBetMoneyList.get(i).BetGold;
                if (betGole > 0) {
                    if (totalBetMoneyList.get(i).BetNum == bonusNum) {
                        money += betGole * ratoList.get(i).Ratio;
                    }
                    switch (totalBetMoneyList.get(i).BetNum) {
                        case 101:
                            //big
                            if (isBig) {
                                money += betGole * ratoList.get(i).Ratio;
                            }
                            break;
                        case 102:
                            //small
                            if (!isBig) {
                                money += betGole * ratoList.get(i).Ratio;
                            }
                            break;
                        case 103:
                            //single
                            if (isSingle) {
                                money += betGole * ratoList.get(i).Ratio;
                            }
                            break;
                        case 104:
                            //double
                            if (!isSingle) {
                                money += betGole * ratoList.get(i).Ratio;
                            }
                            break;
                    }
                }
            }
            homeOprateView.resetTable();
            if (Constants.IS_BET) {
                if (money > 0) {
                    homeOprateView.showWinPop(money + "");
                    homeOprateView.updateRedAmount(money);
                } else {

                    homeOprateView.showGoBet("再接再厉吧!!");
                }
            }
        }
    }
}
