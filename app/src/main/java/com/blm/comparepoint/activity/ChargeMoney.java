package com.blm.comparepoint.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.DensityUtils;
import com.blm.comparepoint.untils.SDCardUtils;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.ScreenUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class ChargeMoney extends BaseActivity {
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
    @BindView(R.id.img_code_pic)
    ImageView imgCodePic;
    @BindView(R.id.txt_notify)
    TextView txtNotify;
    @BindView(R.id.img_download)
    ImageView imgDownload;
    @BindView(R.id.img_alipay_pic)
    ImageView imgAlipayPic;
    @BindView(R.id.img_alipay_download)
    ImageView imgAlipayDownload;
    @BindView(R.id.ll_wechat_content)
    LinearLayout llWechatContent;
    @BindView(R.id.ll_alipay_content)
    LinearLayout llAlipayContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_money);
        ButterKnife.bind(this);
        initView();
        setUserInfo();
    }

    private void initView() {
        int width = ScreenUtils.getScreenWidth(this);
        LinearLayout.LayoutParams wechatParams = (LinearLayout.LayoutParams) llWechatContent.getLayoutParams();
        width = (width - DensityUtils.dp2px(this, 60)) / 2;
        wechatParams.width = wechatParams.height = width;
        llWechatContent.setLayoutParams(wechatParams);

        LinearLayout.LayoutParams alipayParams= (LinearLayout.LayoutParams) llAlipayContent.getLayoutParams();
        alipayParams.width=alipayParams.height=width;
        llAlipayContent.setLayoutParams(alipayParams);

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l) + "");
    }

    @Override
    public void setRedAmount() {
        super.setRedAmount();
        txtRedMoney.setText(SPUtils.get(context, Constants.ACTIVEAMOUNT, 0l) + "");
    }

    private void setUserInfo() {
        Glide.with(this).load(SPUtils.get(context, Constants.AVATAR, "")).into(imgAvatar);
        txtName.setText((String) SPUtils.get(context, Constants.NICKNAME, ""));
        txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0l) + "");
        txtNotify.setText((String) SPUtils.get(context, Constants.CHARGEDESC, ""));
        Glide.with(this).load(SPUtils.get(context, Constants.CHARGEALIURL, "")).into(imgAlipayPic);

        imgSign.setEnabled(!(boolean) SPUtils.get(context, Constants.ISSIGN, false));
        Glide.with(this).load(SPUtils.get(context, Constants.CHARGEURL, "")).into(imgCodePic);
    }


    /**
     * 用户签到
     */
    public void signIn() {
        PostTools.postData(Constants.MAIN_URL + "User/Sign", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (!TextUtils.isEmpty(response)) {
                    BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                    if (baseBean.Success) {
                        imgSign.setEnabled(false);
                        SPUtils.put(context, Constants.USERAMOUNT, (long) ((long) SPUtils.get(context, Constants.USERAMOUNT, 0) +
                                (int) SPUtils.get(context, Constants.SIGNBOUNS, 0)));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtMoney.setText(SPUtils.get(context, Constants.USERAMOUNT, 0) + "");
                            }
                        });
                    } else {
                        imgSign.setEnabled(true);
                        T.showShort(context, baseBean.Msg);
                    }
                }
            }
        });
    }

    @OnClick({R.id.img_back, R.id.img_sign, R.id.img_download, R.id.img_alipay_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_sign:
                signIn();
                break;
            case R.id.img_download:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Bitmap bitmap = Glide.with(context).load(SPUtils.get(context, Constants.CHARGEURL, "")).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SDCardUtils.saveImageToGallery(context, bitmap);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.img_alipay_download:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Bitmap bitmap = Glide.with(context).load(SPUtils.get(context, Constants.CHARGEALIURL, "")).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SDCardUtils.saveImageToGallery(context, bitmap);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

}
