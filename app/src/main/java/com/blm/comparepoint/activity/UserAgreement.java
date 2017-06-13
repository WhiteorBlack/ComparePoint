package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.Bean_SystemConfig;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/16.
 */

public class UserAgreement extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_content)
    TextView txtContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_agreement);
        ButterKnife.bind(this);
        getSystemConfig();
    }

    /**
     * 系统配置信息
     */
    public void getSystemConfig() {
        PostTools.postData(Constants.MAIN_URL + "Config/GetSysConfig", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                Bean_SystemConfig bean_systemConfig = new Gson().fromJson(response, Bean_SystemConfig.class);
                if (bean_systemConfig != null && bean_systemConfig.Success) {
                    txtContent.setText(bean_systemConfig.Data.UserAgreement);
                }
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        AppManager.getAppManager().finishActivity(this);
    }
}
