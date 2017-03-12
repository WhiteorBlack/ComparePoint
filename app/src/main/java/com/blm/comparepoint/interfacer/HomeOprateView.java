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

    /**
     * 获取结果
     */
    void getResult();

    /**
     * 倒计时
     *
     * @param type
     * @param time
     */
    void countDown(int type, int time);

    /**
     * 赢得金币弹窗
     *
     * @param money
     */
    void showWinPop(String money);

    /**
     * 继续押注弹窗
     *
     * @param notify
     */
    void showGoBet(String notify);

    /**
     * 展示信息弹窗
     *
     * @param notify
     */
    void showNotify(String notify);

    /**
     * 选择押注金额
     *
     * @param pos
     */
    void betClick(int pos);

    /**
     * 清除桌面押注数据
     */
    void resetTable();

    void toastCommonNotify(String notify);
}
