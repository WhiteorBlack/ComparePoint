package com.blm.comparepoint.bean;

import java.util.List;

/**
 * Created by 41508 on 2017/3/17.
 */

public class Bean_CurrentInfo extends BaseBean {
    public CurrentInfo Data;
    public static class CurrentInfo{
        public String RoundNo;
        public int StartTime;
        public int BonusNum;
        public int BonusTime;
        public List<BonusNumList>LastBonusNum;
    }

    public static class BonusNumList{
        public int BonusNum;
    }
}
