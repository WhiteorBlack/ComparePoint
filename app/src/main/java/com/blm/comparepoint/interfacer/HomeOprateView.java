package com.blm.comparepoint.interfacer;

import com.blm.comparepoint.bean.Bean_AppVersion;
import com.blm.comparepoint.bean.Bean_CurrentInfo;
import com.blm.comparepoint.bean.Bean_GameConfig;
import com.blm.comparepoint.bean.Bean_SystemConfig;

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


    void betMoney();

    void toastNotify(String notify);


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

    void showDialog();
    void dimissDialog();

    void setSystemConfig(Bean_SystemConfig.SystemConfig systemConfig);
    void setGameConfig(Bean_GameConfig.GameConfig gameConfig);

    void checkUpdate(Bean_AppVersion.AppVersion appVersion);

    void currentInfo(Bean_CurrentInfo.CurrentInfo currentInfo);
}
