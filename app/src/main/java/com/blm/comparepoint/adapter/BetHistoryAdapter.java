package com.blm.comparepoint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_CurrentInfo;

import java.util.List;

/**
 * Created by 41508 on 2017/3/6.
 */

public class BetHistoryAdapter extends BaseRecyAdapter {

    public BetHistoryAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder = (ViewHolder) holder;
        if (position == 0) {
            mHolder.getView(R.id.img_line_top).setVisibility(View.VISIBLE);
        } else {
            mHolder.getView(R.id.img_line_top).setVisibility(View.GONE);
        }
        Bean_CurrentInfo.BonusNumList bonusNumList = (Bean_CurrentInfo.BonusNumList) dataList.get(position);
        mHolder.setText(bonusNumList.BonusNum + "", R.id.txt_num);
        if (bonusNumList.BonusNum > 5) {
            mHolder.setText("大", R.id.txt_size);
        } else {
            mHolder.setText("小", R.id.txt_size);
        }
        if (bonusNumList.BonusNum % 2 > 0) {
            mHolder.setText("单", R.id.txt_single_double);
        } else {
            mHolder.setText("双", R.id.txt_single_double);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.bet_history_item;
    }
}
