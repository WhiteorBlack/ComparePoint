package com.blm.comparepoint.adapter;

import android.support.v7.widget.RecyclerView;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_GoldRecord;
import com.blm.comparepoint.bean.Bean_Order;

import java.util.List;

/**
 * Created by 41508 on 2017/3/8.
 */

public class ChargeConvertAdapter extends BaseRecyAdapter {
    public ChargeConvertAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder = (ViewHolder) holder;
        Bean_GoldRecord.Order order = (Bean_GoldRecord.Order) dataList.get(position);
        if (order.Type == 1) {
            mHolder.setText("充值", R.id.txt_name);
            mHolder.setText("充值 " + order.GoldAmount+" 成功到账", R.id.txt_get_money);
        }
        if (order.Type == 2) {
            mHolder.setText("提现", R.id.txt_name);
            mHolder.setText("提现 " + order.GoldAmount+" 成功到账", R.id.txt_get_money);
        }
        mHolder.setText(order.CreateTime, R.id.txt_time);
    }

    @Override
    public int getLayout() {
        return R.layout.my_order_item;
    }
}
