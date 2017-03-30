package com.blm.comparepoint.bean;

/**
 * Created by 41508 on 2017/3/16.
 */

public class Bean_Login extends BaseBean{
    public Login Data;
    public static class Login{

        public String GameUserId;
        public String OpenId;
        public String NickName;
        public String Avatar;
        public String Province;
        public long UserBalance;
        public long UserActive;
        public String Token;
        public boolean IsSignToday;

        public String IMSign;
    }
}
