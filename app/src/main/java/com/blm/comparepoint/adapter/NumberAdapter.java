package com.blm.comparepoint.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.blm.comparepoint.BaseRecyAdapter;
import com.blm.comparepoint.R;
import com.blm.comparepoint.bean.Bean_Number;

import java.util.List;

/**
 * Created by 41508 on 2017/3/6.
 */

public class NumberAdapter extends BaseRecyAdapter {
    public int height=-1;
    public NumberAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder mHolder= (ViewHolder) holder;
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mHolder.getView(R.id.txt_number).getLayoutParams();
        params.height=height/10;
        mHolder.getView(R.id.txt_number).setLayoutParams(params);
        Bean_Number number= (Bean_Number) dataList.get(position);
        mHolder.setText(number.number+"",R.id.txt_number);
        if (number.isSelected){

        }else {

        }

        if (number.isShine){

        }else {

        }
    }

    public void setHeight(int height){
        this.height=height;
    }

    @Override
    public int getLayout() {
        return R.layout.ten_number_item;
    }
}
