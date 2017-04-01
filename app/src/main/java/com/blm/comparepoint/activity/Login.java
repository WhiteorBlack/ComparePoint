package com.blm.comparepoint.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.Bean_Login;
import com.blm.comparepoint.bean.Bean_WxLogin;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Login extends BaseActivity {


    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new);
        ButterKnife.bind(this);
    }


    private void login() {
        Map<String, String> params = new HashMap<>();
        params.put("UserName", userName);
        params.put("PassWord", userPwd);
        PostTools.postData(Constants.MAIN_URL + "Account/Login", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    T.showShort(context, "获取用户信息失败,请重试");
                    return;
                }
                final Bean_Login bean_login = new Gson().fromJson(response, Bean_Login.class);
                if (bean_login.Success) {
                    TIMUser user = new TIMUser();
                    user.setAccountType(Constants.ACCOUNT_TYPE);
                    user.setAppIdAt3rd(Constants.IM_APP_ID + "");
                    user.setIdentifier(bean_login.Data.GameUserId);
                    TIMManager.getInstance().login(Constants.IM_APP_ID, user, bean_login.Data.IMSign, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            L.e("IM error--" + s);
                        }

                        @Override
                        public void onSuccess() {
                            L.e("IM login success--");
                        }
                    });
                    Constants.USERTOKEN = bean_login.Data.Token;
                    SPUtils.put(context, Constants.TOKEN, bean_login.Data.Token);
                    SPUtils.put(context, Constants.GAMER_ID, bean_login.Data.GameUserId);
                    SPUtils.put(context, Constants.IS_LOGIN, true);
                    SPUtils.put(context,Constants.ISSIGN,bean_login.Data.IsSignToday);
                    SPUtils.put(context, Constants.USERAMOUNT, bean_login.Data.UserBalance);
                    SPUtils.put(context, Constants.ACTIVEAMOUNT, bean_login.Data.UserActive);
                    SPUtils.put(context, Constants.NICKNAME, bean_login.Data.NickName);
                    SPUtils.put(context, Constants.AVATAR, bean_login.Data.Avatar == null ? "" : bean_login.Data.Avatar);
                    startActivity(new Intent(context, Home.class));
                    AppManager.getAppManager().finishActivity();
                } else {
                    T.showShort(context, bean_login.Msg);
                }
            }
        });
    }


    private String userName, userPwd;

    @OnClick({R.id.txt_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_register:
                startActivity(new Intent(context, Register.class));
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.btn_login:
                userName = edtAccount.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    T.showShort(context, "请输入用户名");
                    return;
                }
                userPwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(userPwd)) {
                    T.showShort(context, "请输入密码");
                    return;
                }
                login();
                break;
        }
    }
}