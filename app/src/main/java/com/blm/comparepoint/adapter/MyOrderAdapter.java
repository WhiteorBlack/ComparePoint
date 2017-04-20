package com.blm.comparepoint.adapter;

import android.support.v7.widget.RecyclerView;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_Order;

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
        ViewHolder mHolder = (ViewHolder) holder;
        Bean_Order.Order order = (Bean_Order.Order) dataList.get(position);
        mHolder.setText("充值 " + order.RechargeAmout + " 元", R.id.txt_name);
        mHolder.setText(order.CreateTime, R.id.txt_time);
        mHolder.setText("获得金币 " + order.GoldAmout + " 个", R.id.txt_get_money);
    }

    @Override
    public int getLayout() {
        return R.layout.my_order_item;
    }
}
