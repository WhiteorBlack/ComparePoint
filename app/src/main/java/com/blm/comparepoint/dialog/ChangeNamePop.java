package com.blm.comparepoint.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blm.comparepoint.BasePopupWindow;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 41508 on 2017/4/17.
 */

public class ChangeNamePop extends BasePopupWindow {
    private View view;
    private EditText edtName;
    private Button btnConfirm;

    public ChangeNamePop(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.change_name_pop, null);
        }
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
        view.findViewById(R.id.img_close).setOnClickListener(this);
        edtName = (EditText) view.findViewById(R.id.edt_name);
        edtName.setHint((String) SPUtils.get(context, Constants.NICKNAME, ""));
        this.setContentView(view);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_close:
                dismiss();
                break;
            case R.id.btn_confirm:
                String name = edtName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    T.showShort(context, "请输入要修改的昵称");
                    return;
                }
                changeName(name);
                break;
        }
    }

    private void changeName(final String name) {
        btnConfirm.setClickable(false);
        Map<String, String> params = new HashMap<>();
        params.put("nickName", name);
        PostTools.postData(Constants.MAIN_URL + "User/UpdateNickeName", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                btnConfirm.setClickable(true);
                if (!TextUtils.isEmpty(response)) {
                    BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                    Bundle bundle = new Bundle();
                    if (baseBean.Success) {
                        bundle.putString("name",name);
                        bundle.putBoolean("success", true);
                        dismiss();
                    } else {
                        bundle.putBoolean("success", false);
                        T.showShort(context, baseBean.Msg);
                    }
                    if (popInterfacer != null) {
                        popInterfacer.OnConfirm(flag, bundle);
                    }
                }
            }
        });
    }
}
