package com.blm.comparepoint.bean;

import java.util.List;

/**
 * Created by 41508 on 2017/3/22.
 */

public class Bean_Order extends BaseBean {
    public List<Order> Data;

    public static class Order {
        public String Id;
        public String RoundNo;
        public String UserId;
        public Bean_GamerInfo.GamerInfo UserInfo;
        public String BetNumber;
        public String BetNumberName;
        public String Ratio;
        public String BetGold;
        public String BounsGold;
        public boolean IsBouns;
        public String CreateTime;
    }
}
