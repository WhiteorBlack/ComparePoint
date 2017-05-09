package com.blm.comparepoint.interfacer;

import java.util.Date;

/**
 * Created by 41508 on 2017/3/16.
 */

public interface DataOprateInterfacer {
    /**
     * 获取系统配置的回调
     * @param response
     */
    void getSystemConfigSuccess(String response);
    void getSystemConfigFail();

    /**
     * 获取玩儿家配置的回调
     * @param response
     */
    void getGameCfgSuccess(String response);
    void getGameCfgFail();

    /**
     * 用户签到
     */
    void signSuccess(String response);
    void signFail();

    /**
     * 版本更新
     * @param response
     */
    void getVersion(String response);

    /**
     * 获取当前游戏信息,以作同步进程
     * @param response
     */
    void getCurrentInfo(String response,String date);


    void betMoneyResult(String response);
}
