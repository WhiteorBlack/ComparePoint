package com.blm.comparepoint.adapter;

import android.support.v7.widget.RecyclerView;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;

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

    }

    @Override
    public int getLayout() {
        return R.layout.notify_list_item;
    }
}
