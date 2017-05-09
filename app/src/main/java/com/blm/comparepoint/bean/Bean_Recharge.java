package com.blm.comparepoint.bean;/**
 * Created by Administrator on 2017/4/1.
 */

/**
 * author:${白曌勇} on 2017/4/1
 * TODO:
 */
public class Bean_Recharge {
    public String Type;
    public Recharge Data;
    public static class Recharge {
        public long RechargeGold;
        public String GameUserId;
        public int PayMentType;
    }

}
