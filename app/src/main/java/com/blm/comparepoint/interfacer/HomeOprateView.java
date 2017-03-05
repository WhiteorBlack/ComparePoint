package com.blm.comparepoint.interfacer;

/**
 * Created by 41508 on 2017/3/2.
 */

public interface HomeOprateView {
    /**
     * show top notify data
     */
    void setNotify();

    /**
     *
     * @param successful
     */
    void sigin(boolean successful);

    /**
     * 展示规则详情
     */
    void showRoleDetial();

    /**
     * 展示订单信息
     */
    void showOrderInfo();

    void betMoney();

    void toastNotify(String notify);

    /**
     * 清楚押注金币
     */
    void clearBetMoney();

    void getResult();

    void countDown(int type,int time);

}
