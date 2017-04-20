package com.blm.comparepoint.dialog;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blm.comparepoint.BasePopupWindow;
import com.blm.comparepoint.R;

/**
 * Created by 41508 on 2017/4/17.
 */

public class RolePop extends BasePopupWindow {
    private View view;
    private TextView txtRole;
    public RolePop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.role_pop,null);
        }
        view.findViewById(R.id.img_confirm).setOnClickListener(this);
        txtRole=(TextView)view.findViewById(R.id.txt_role);
        this.setContentView(view);
    }
    public void setRole(String role){
        if (!TextUtils.isEmpty(role)){
            txtRole.setText(role);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }
}
