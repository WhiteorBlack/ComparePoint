package com.blm.comparepoint.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_Number;
import com.blm.comparepoint.untils.L;

import java.util.List;

/**
 * Created by 41508 on 2017/3/6.
 */

public class NumberAdapter extends BaseRecyAdapter {
    public int height = -1;

    public NumberAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder = (ViewHolder) holder;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHolder.getView(R.id.fl_content).getLayoutParams();
        params.height = height / 10;
        mHolder.getView(R.id.fl_content).setLayoutParams(params);
        Bean_Number number = (Bean_Number) dataList.get(position);
        mHolder.setText(number.number + "", R.id.txt_number);
//        if (number.isSelected) {
//            mHolder.getView(R.id.img_select).setVisibility(View.VISIBLE);
//        } else {
//            mHolder.getView(R.id.img_select).setVisibility(View.GONE);
//        }

        if (number.isShine) {
            mHolder.getView(R.id.img_select).setVisibility(View.VISIBLE);
        } else {
            mHolder.getView(R.id.img_select).setVisibility(View.GONE);
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getLayout() {
        return R.layout.ten_number_item;
    }
}
