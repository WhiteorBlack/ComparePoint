package com.blm.comparepoint.presenter;

import com.blm.comparepoint.interfacer.HomeOprateView;
import com.blm.comparepoint.wxapi.Constants;

/**
 * Created by 41508 on 2017/3/2.
 */

public class HomePresenter {
    private HomeOprateView homeOprateView;

    public HomePresenter(HomeOprateView homeOprateView) {
        this.homeOprateView = homeOprateView;
    }

    /**
     * 重置所有投注金额
     */
    public void resetStatue() {
        Constants.BET_SELECT_NUM = -1;
        Constants.BET_SINGLE_MONEY = -1;
        Constants.BET_DOUBLE_MONEY = -1;
        Constants.BET_BIG_MONEY = -1;
        Constants.BET_SMALL_MONEY = -1;
        Constants.BET_ONE_MONEY = -1;
        Constants.BET_TWO_MONEY = -1;
        Constants.BET_THREE_MONEY = -1;
        Constants.BET_FOUR_MONEY = -1;
        Constants.BET_FIVE_MONEY = -1;
        Constants.BET_SIX_MONEY = -1;
        Constants.BET_SEVEN_MONEY = -1;
        Constants.BET_EIGHT_MONEY = -1;
        Constants.BET_NINE_MONEY = -1;
        Constants.BET_TEN_MONEY = -1;
        homeOprateView.resetTable();
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
            case 11:

                break;
            case 12:

                break;
            case 13:

                break;
            case 14:

                break;
        }
    }
}
