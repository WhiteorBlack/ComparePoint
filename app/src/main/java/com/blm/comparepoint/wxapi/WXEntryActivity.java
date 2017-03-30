package com.blm.comparepoint.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.activity.Home;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.Bean_Login;
import com.blm.comparepoint.bean.Bean_WxLogin;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.NetUtils;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMUser;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    @BindView(R.id.btn_login)
    ImageView btnLogin;
    @BindView(R.id.txt_notify)
    TextView txtNotify;

    private IWXAPI iwxapi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        iwxapi.registerApp(Constants.APP_ID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        SendAuth.Resp resp = (SendAuth.Resp) baseResp;
        switch (baseResp.errCode) {
            case 0://同意
                getToken(resp.code);
                break;
            default:
                T.showShort(WXEntryActivity.this, "授权失败,请重试!");
                btnLogin.setEnabled(true);
                break;
        }
    }

    @Override
    public void pointClick(View v) {
        super.pointClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                loginByChat();
                btnLogin.setEnabled(false);
                break;
            case R.id.txt_notify:

                break;
        }
    }

    private void loginByChat() {
        // send oauth request
        try {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_compare_point";
            iwxapi.sendReq(req);
        } catch (Exception e) {
            L.e(e.toString());
        }

    }

    private String openId = "", accessToken = "";

    private void getToken(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", Constants.APP_ID);
        params.put("secret", Constants.APP_SCECET);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        PostTools.getDataWithNone(this, "https://api.weixin.qq.com/sns/oauth2/access_token", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject object = new JSONObject(response);
                    openId = object.getString("openid");
                    accessToken = object.getString("access_token");
                    login();
                } catch (Exception e) {
                    T.showShort(WXEntryActivity.this, "获取用户信息失败,请重试!");
                }

            }
        });
    }

    private void login() {
        Map<String, String> params = new HashMap<>();
        params.put("OpenId", openId);
        PostTools.postData(Constants.MAIN_URL + "Account/Login", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    T.showShort(context, "获取用户信息失败,请重试");
                    btnLogin.setEnabled(true);
                    return;
                }
                final Bean_Login bean_login = new Gson().fromJson(response, Bean_Login.class);
                if (bean_login.Success) {
                    TIMUser user = new TIMUser();
                    user.setAccountType(Constants.ACCOUNT_TYPE);
                    user.setAppIdAt3rd(Constants.IM_APP_ID + "");
                    user.setIdentifier(bean_login.Data.GameUserId);
                    TIMManager.getInstance().login(Constants.IM_APP_ID, user, bean_login.Data.Sign, new TIMCallBack() {
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
                    SPUtils.put(context, Constants.OPENID, openId);
                    SPUtils.put(context, Constants.GAMER_ID, bean_login.Data.GameUserId);
                    SPUtils.put(context, Constants.ISSIGN, false);
                    SPUtils.put(context,Constants.USERAMOUNT,bean_login.Data.UserBalance);
                    SPUtils.put(context,Constants.ACTIVEAMOUNT,bean_login.Data.UserActive);
                    if (TextUtils.isEmpty(bean_login.Data.Avatar) || TextUtils.isEmpty(bean_login.Data.NickName)) {
                        getUserInfoFromWx();
                    } else {
                        SPUtils.put(context, Constants.NICKNAME, bean_login.Data.NickName);
                        SPUtils.put(context, Constants.AVATAR, bean_login.Data.Avatar);
                        startActivity(new Intent(context, Home.class));
                    }
                }
            }

            private void getUserInfoFromWx() {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", accessToken);
                params.put("openid", openId);
                PostTools.getDataWithNone(context, "https://api.weixin.qq.com/sns/userinfo", params, new PostCallBack() {
                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                        if (TextUtils.isEmpty(response)) {
                            T.showShort(context, "网络错误,请重试");
                            return;
                        }
                        Bean_WxLogin wxLogin = new Gson().fromJson(response, Bean_WxLogin.class);
                        SPUtils.put(context, Constants.NICKNAME, wxLogin.nickname);
                        SPUtils.put(context, Constants.AVATAR, wxLogin.headimgurl);
                        startActivity(new Intent(context, Home.class));
                    }
                });
            }

        });
    }

}