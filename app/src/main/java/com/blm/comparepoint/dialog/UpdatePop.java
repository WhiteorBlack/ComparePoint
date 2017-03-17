package com.blm.comparepoint.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blm.comparepoint.BasePopupWindow;
import com.blm.comparepoint.R;

/**
 * Created by 41508 on 2017/3/17.
 */

public class UpdatePop extends BasePopupWindow {
    private View view;
    private TextView txtContent;

    public UpdatePop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.update_pop, null);
        }
        txtContent = (TextView) view.findViewById(R.id.txt_content);
        view.findViewById(R.id.btn_update).setOnClickListener(this);
        this.setContentView(view);
        this.setOutsideTouchable(true);
    }

    public void setContent(String content){
        txtContent.setText(content);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (popInterfacer != null) {
            popInterfacer.OnConfirm(flag, null);
        }
    }
}
