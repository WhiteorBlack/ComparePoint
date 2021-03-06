package com.blm.comparepoint.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blm.comparepoint.BasePopupWindow;
import com.blm.comparepoint.R;

/**
 * Created by 41508 on 2017/3/7.
 */

public class NotifyPop extends BasePopupWindow {

    private View view;
    private TextView txtMoney;

    public NotifyPop(Context context) {
        super(context);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.notify_pop, null);
        }
        txtMoney = (TextView) view.findViewById(R.id.txt_money);
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        this.setContentView(view);
    }

    public void setNotify(String notify) {
        txtMoney.setText(notify);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }
}
