package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class ConvertMoney extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.img_sign)
    TextView imgSign;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.txt_red_money)
    TextView txtRedMoney;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.chb_alipay)
    RadioButton chbAlipay;
    @BindView(R.id.chb_wechat)
    RadioButton chbWechat;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.chb_card)
    RadioButton chbCard;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private String type = "银行卡";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conver_money);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chb_alipay:
                        aliPaySelected();
                        break;
                    case R.id.chb_card:
                        cardSelected();
                        break;
                    case R.id.chb_wechat:
                        wechatSelected();
                        break;
                }
            }
        });
    }

    private void wechatSelected() {
        edtAccount.setHint("请输入微信账号");
        edtName.setHint("请输入微信昵称");
        type = "微信";
    }

    private void cardSelected() {
        edtAccount.setHint("请输入银行卡账号");
        edtName.setHint("请输入银行卡开卡人姓名");
        type = "银行卡";
    }

    private void aliPaySelected() {
        edtAccount.setHint("请输入支付宝账号");
        edtName.setHint("请输入支付宝昵称");
        type = "支付宝";
    }

    private String name, account, money;

    @OnClick({R.id.img_back, R.id.img_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.img_confirm:
                name = edtName.getText().toString();
                account = edtAccount.getText().toString();
                money = edtMoney.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    T.showShort(context, "请输入账户名称");
                    return;
                }

                if (TextUtils.isEmpty(account)) {
                    T.showShort(context, "请输入提现账户");
                    return;
                }
                if (TextUtils.isEmpty(money)) {
                    T.showShort(context, "请输入提现金额");
                    return;
                }

                if (Integer.parseInt(money) > (long) SPUtils.get(context, Constants.ACTIVEAMOUNT, 0)) {
                    T.showShort(context, "账户余额不足");
                    return;
                }

                convertMoney();
                break;
        }
    }

    private void convertMoney() {
        Map<String, String> params = new HashMap<>();
        params.put("WithdrawName", name);
        params.put("WithdrawType", type);
        params.put("WithdrawAccount", account);
        params.put("Amount", money);
        PostTools.postData(Constants.MAIN_URL + "User", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    T.showShort(context, "网络错误,请重试!");
                    return;
                }
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.Success) {
                    T.showShort(context, "申请成功,我们会尽快处理");
                } else {
                    T.showShort(context, baseBean.Msg);
                }
            }
        });
    }

}
