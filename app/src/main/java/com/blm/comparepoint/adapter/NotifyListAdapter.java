package com.blm.comparepoint.adapter;

import android.support.v7.widget.RecyclerView;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_NotifyList;

import java.util.List;

/**
 * Created by 41508 on 2017/5/5.
 */

public class NotifyListAdapter extends BaseRecyAdapter {
    public NotifyListAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mholder = (ViewHolder) holder;
        Bean_NotifyList.NotifyDetail notifyDetail = (Bean_NotifyList.NotifyDetail) dataList.get(position);
        mholder.setText(notifyDetail.SendTime, R.id.txt_time);
        mholder.setText(notifyDetail.MsgTitle, R.id.txt_title);
        mholder.setText(notifyDetail.MsgContent, R.id.txt_notify_detial);
    }

    @Override
    public int getLayout() {
        return R.layout.notify_list_item;
    }
}
