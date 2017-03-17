package com.blm.comparepoint.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.blm.comparepoint.async.DataOprate;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.bean.Bean_AppVersion;
import com.blm.comparepoint.bean.Bean_BetMoney;
import com.blm.comparepoint.bean.Bean_CurrentInfo;
import com.blm.comparepoint.bean.Bean_SystemConfig;
import com.blm.comparepoint.interfacer.DataOprateInterfacer;
import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by 41508 on 2017/3/2.
 */

public class HomePresenter implements DataOprateInterfacer {
    private HomeOprateView homeOprateView;
    private DataOprate dataOprate;
    private List<Bean_BetMoney> betMoneyList;

    public HomePresenter(HomeOprateView homeOprateView) {
        this.homeOprateView = homeOprateView;
        this.dataOprate = DataOprate.getInstance(this);
        betMoneyList = new ArrayList<>();

    }

    private void resetBetMoney() {
        betMoneyList.clear();
        for (int i = 0; i < 14; i++) {
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

    public void getGameConfig() {
        dataOprate.getGameConfig();
    }

    public void getVersion() {
        dataOprate.getVersion();
    }

    public void getCurrentInfo() {
        dataOprate.getCurrentInfo();
    }

    public void betMoney(int money) {
        switch (Constants.BET_SELECT_NUM) {
            case -1:
                homeOprateView.toastCommonNotify("请选择投注类别");
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 101: //大

                break;
            case 102: //小

                break;
            case 103:  //单

                break;
            case 104:  //双

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
}
