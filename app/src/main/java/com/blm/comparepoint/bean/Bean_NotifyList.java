package com.blm.comparepoint.bean;

import java.util.List;

/**
 * Created by 41508 on 2017/5/5.
 */

public class Bean_NotifyList extends BaseBean {
    public  List<NotifyDetail> Data;
    public static class NotifyDetail{
        public String SystemMsgRecordId;
        public String MsgTitle;
        public String MsgContent;
        public String SendTime;
    }


}
