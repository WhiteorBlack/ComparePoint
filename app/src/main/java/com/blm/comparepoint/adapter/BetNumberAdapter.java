package com.blm.comparepoint.adapter;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_BetNumber;
import com.blm.comparepoint.untils.DensityUtils;
import com.blm.comparepoint.untils.ScreenUtils;

import java.util.List;

/**
 * Created by 41508 on 2017/3/6.
 */

public class BetNumberAdapter extends BaseRecyAdapter {

    public BetNumberAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder = (ViewHolder) holder;
        Bean_BetNumber betNumber = (Bean_BetNumber) dataList.get(position);
        mHolder.setText(betNumber.betMutil, R.id.txt_bet_muitl);
        mHolder.setText(betNumber.number + "", R.id.txt_number);
        if (betNumber.isSelected) {
            mHolder.getView(R.id.fl_parent).setBackgroundColor(mHolder.parent.getContext().getResources().getColor(R.color.betSelected));
        } else {
            mHolder.getView(R.id.fl_parent).setBackgroundColor(mHolder.parent.getContext().getResources().getColor(R.color.tableIn));
        }
        if (position==4||position==9){
            mHolder.getView(R.id.img_line).setVisibility(View.GONE);
        }else {
            mHolder.getView(R.id.img_line).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.bet_number_item;
    }
}
