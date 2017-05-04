package com.blm.comparepoint.bean;

/**
 * Created by 41508 on 2017/3/16.
 */

public class Bean_SystemConfig extends BaseBean {
    public SystemConfig Data;
    public static class SystemConfig{
        public double GoldRange;
        public int RoundTime;
        public int LotteryTime;
        public String UserAgreement;
        public String GameRules;
        public int SignBonus;
        public int MinWithdramAmount;
        public String RechargeQrCode;
        public String CustomerQrCode;
        public String ReChargeRules;
    }
}
