package com.blm.comparepoint.bean;

/**
 * Created by 41508 on 2017/3/17.
 */

public class Bean_GamerInfo extends BaseBean {
    public GamerInfo Data;
    public static class GamerInfo{
        public String GameUserId;
        public String OpenId;
        public String NickName;
        public String Avatar;
        public String Province;
        public long UserBalance;
        public long UserActive;
        public String Token;
        public int Status;
        public boolean IsSignToday;
        public String Sign;
    }
}
