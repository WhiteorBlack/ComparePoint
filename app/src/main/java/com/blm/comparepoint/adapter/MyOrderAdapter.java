package com.blm.comparepoint.adapter;

import android.support.v7.widget.RecyclerView;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;

import java.util.List;

/**
 * Created by 41508 on 2017/3/8.
 */

public class MyOrderAdapter extends BaseRecyAdapter {
    public MyOrderAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder= (ViewHolder) holder;
    }

    @Override
    public int getLayout() {
        return R.layout.my_order_item;
    }
}
