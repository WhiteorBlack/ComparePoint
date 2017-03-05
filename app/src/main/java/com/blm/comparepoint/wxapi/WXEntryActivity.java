package com.blm.comparepoint.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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
        }
    }

    @Override
    public void pointClick(View v) {
        super.pointClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                btnLogin.setEnabled(false);
                break;
            case R.id.txt_notify:

                break;
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
                } catch (Exception e) {
                }

            }
        });
    }

}