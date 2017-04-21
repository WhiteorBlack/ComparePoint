package com.blm.comparepoint.interfacer;

import com.blm.comparepoint.bean.Bean_AppVersion;
import com.blm.comparepoint.bean.Bean_CurrentInfo;
import com.blm.comparepoint.bean.Bean_GameConfig;
import com.blm.comparepoint.bean.Bean_SystemConfig;

import java.util.List;

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
     * @param userBalance
     */
    void sigin(boolean successful, long userBalance);


    void betMoney(int pos);

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

    void setGameConfig(List<Bean_GameConfig.GameConfig> data);

    /**
     * 检查是否有新版APP
     *
     * @param appVersion
     */
    void checkUpdate(Bean_AppVersion.AppVersion appVersion);

    /**
     * 更新当前游戏进度
     *
     * @param currentInfo
     */
    void currentInfo(Bean_CurrentInfo.CurrentInfo currentInfo);

    /**
     * 更新在线人数
     *
     * @param count
     */
    void updateOnline(String count);

    /**
     * 更新用户账户金额
     *
     * @param money
     */
    void updateAmount(long money);

    /**
     * 更新开奖记录
     *
     * @param num
     */
    void updateBounsHistory(int num);

    /**
     * 更新用户红包账户金额
     *
     * @param money
     */
    void updateRedAmount(long money);

    void setNotifyData(List<String> data);

    void dismissAllPop();

    /**
     * 清除压注的金币
     */
    void clearBetMoney();


    /**
     * 重置所有的压注选择状态
     */
    void resetBetStatue();

    void updateBetMoney(int pos, int money);

    /**
     * 压注成功之后更新账户金额
     *
     * @param money
     */
    void betMoneySuccess(int money);

    void showBonusNumAnim(int num);

    void endBonusAnim(int num);

    /**
     * 重置开注的数字状态
     */
    void resetNumStatue();

    /**
     * 充值投注之后的桌面金币变化
     * @param pos
     * @param amount
     */
    void setBetMoney(int pos,int amount);

    /**
     * 如果有清除押注的情况则把清除的金额返回账号
     * @param amount
     */
    void resetAmount(long amount);
}
