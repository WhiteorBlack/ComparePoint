package com.blm.comparepoint.activity;/**
 * Created by Administrator on 2017/3/30.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.Bean_Login;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Headers;

/**
 * author:${白曌勇} on 2017/3/30
 * TODO:
 */
public class Register extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.edt_nick)
    EditText edtNick;
    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_pwd_two)
    EditText edtPwdTwo;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.txt_user_agreement)
    TextView txtUserAgreement;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交数据中...");
        progressDialog.setCancelable(false);
    }

    private String userName, userPwd, pwdTwo, nickName;

    @OnClick({R.id.img_back, R.id.btn_register,R.id.txt_user_agreement})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                userName = edtAccount.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    T.showShort(context, "请输入账号");
                    return;
                }
                userPwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(userPwd)) {
                    T.showShort(context, "请输入密码");
                    return;
                }
                pwdTwo = edtPwdTwo.getText().toString();
                if (TextUtils.isEmpty(pwdTwo)) {
                    T.showShort(context, "请再次输入密码");
                    return;
                }
                if (!TextUtils.equals(userPwd, pwdTwo)) {
                    T.showShort(context, "两次输入的密码不一致");
                    return;

                }
                nickName = edtNick.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    T.showShort(context, "请输入昵称");
                    return;
                }
                login();
                break;
            case R.id.img_back:
                finish();
                startActivity(new Intent(context, Login.class));
                break;
            case R.id.txt_user_agreement:
                startActivity(new Intent(context,UserAgreement.class));
                break;
        }
    }

    private void login() {
        progressDialog.show();
        btnRegister.setClickable(false);
        Constants.USERTOKEN = "";
        Map<String, String> params = new HashMap<>();
        params.put("UserName", userName);
        params.put("PassWord", userPwd);
        params.put("ConfirmPass", userPwd);
        params.put("NickName", nickName);
        PostTools.postData(Constants.MAIN_URL + "Account/Register", params, new PostCallBack() {
            @Override
            public void onAfter(Headers headers) {
                super.onAfter(headers);
                progressDialog.dismiss();
                btnRegister.setClickable(true);
            }

            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    T.showShort(context, "用户注册失败,请重试");
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
                            TIMGroupManager.getInstance().applyJoinGroup(Constants.GROUP_ID, "", new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    L.e("applay error--" + i + s);
                                }

                                @Override
                                public void onSuccess() {
                                    L.e("join success");
                                }
                            });
                            startActivity(new Intent(context, Home.class));
                        }
                    });
                    Constants.USERTOKEN = bean_login.Data.Token;
                    SPUtils.put(context, Constants.TOKEN, bean_login.Data.Token);
                    SPUtils.put(context, Constants.GAMER_ID, bean_login.Data.GameUserId);
                    SPUtils.put(context, Constants.IS_LOGIN, true);
                    SPUtils.put(context, Constants.ISSIGN, bean_login.Data.IsSignToday);
                    SPUtils.put(context, Constants.USERAMOUNT, bean_login.Data.UserBalance);
                    SPUtils.put(context, Constants.ACTIVEAMOUNT, bean_login.Data.UserActive);
                    SPUtils.put(context, Constants.NICKNAME, bean_login.Data.NickName);
                    SPUtils.put(context, Constants.AVATAR, bean_login.Data.Avatar == null ? "" : bean_login.Data.Avatar);
                    SPUtils.put(context, Constants.USERNAME, bean_login.Data.UserName);
                    finish();
                    startActivity(new Intent(context, Home.class));
                } else {
                    T.showShort(context, bean_login.Msg);
                }
            }
        });
    }


}
