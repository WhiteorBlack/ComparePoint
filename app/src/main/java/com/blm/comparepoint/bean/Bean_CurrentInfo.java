package com.blm.comparepoint.bean;

import java.util.List;

/**
 * Created by 41508 on 2017/3/17.
 */

public class Bean_CurrentInfo extends BaseBean {
    public CurrentInfo Data;
    public static class CurrentInfo{
        public String RoundNo;
        public long StartTime;
        public int BonusNum;
        public long BonusTime;
        public int RoundCost;
        public int LotteryCost;
        public int BetAmount;
        public int BonusAmount;
        public int BetUserCount;
        public List<BonusNumList> LastBonusNum;
        public List<BetRecords> BetRecords;
    }

    public static class BonusNumList{
        public int BonusNum;
        public String RoundNo;
    }

    public static class BetRecords{
        public String RoundNo;
        public int BetNumber;
        public int BetGold;
    }
}
