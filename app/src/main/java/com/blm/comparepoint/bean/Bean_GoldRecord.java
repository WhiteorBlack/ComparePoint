package com.blm.comparepoint.bean;

import java.util.List;

/**
 * Created by 41508 on 2017/3/22.
 */

public class Bean_GoldRecord extends BaseBean {
    public List<Order> Data;

    public static class Order {
        public String Id;
        public String RoundNo;
        public String UserId;
        public String CreateTime;
        public String Desc;
        public int Type;
        public String GoldAmount;
    }
}
