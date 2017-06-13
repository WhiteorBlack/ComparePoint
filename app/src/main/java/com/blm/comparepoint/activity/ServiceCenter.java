package com.blm.comparepoint.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;
import com.blm.comparepoint.untils.AppManager;
import com.blm.comparepoint.untils.SDCardUtils;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class ServiceCenter extends BaseActivity {
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_code_pic)
    ImageView imgCodePic;
    @BindView(R.id.img_download)
    ImageView imgDownload;
    @BindView(R.id.txt_notify)
    TextView txtNotify;
    @BindView(R.id.txt_phone)
    TextView txtPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_center);
        ButterKnife.bind(this);
        setUserInfo();
    }

    private void setUserInfo() {
        Glide.with(this).load(SPUtils.get(context, Constants.SERVICEURL, "")).into(imgCodePic);
    }

    @OnClick({R.id.img_close, R.id.img_code_pic, R.id.img_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.img_code_pic:
                break;
            case R.id.img_download:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Bitmap bitmap = Glide.with(context).load(SPUtils.get(context, Constants.SERVICEURL, "")).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
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
