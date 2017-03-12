package com.blm.comparepoint.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.R;
import com.blm.comparepoint.widget.CircleImageView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 41508 on 2017/3/8.
 */

public class MyInfoCard extends TakePhotoActivity {
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
    @BindView(R.id.fl_change_avatar)
    FrameLayout flChangeAvatar;
    @BindView(R.id.fl_my_name)
    FrameLayout flMyName;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.fl_my_sex)
    FrameLayout flMySex;
    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.fl_city)
    FrameLayout flCity;
    @BindView(R.id.img_share)
    ImageView imgShare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_card);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_change_avatar, R.id.fl_my_name, R.id.fl_my_sex, R.id.fl_city, R.id.img_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_change_avatar:
                takePhoto();
                break;
            case R.id.fl_my_name:
                break;
            case R.id.fl_my_sex:
                break;
            case R.id.fl_city:
                break;
            case R.id.img_share:

                break;
        }
    }

    private void takePhoto() {
        final File file = new File(Environment.getExternalStorageDirectory(), "/chessCard/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        CharSequence[] items = {"手机相册", "手机拍照"};
        final TakePhoto takePhoto = getTakePhoto();
        new AlertDialog.Builder(this).setTitle("选择照片").setCancelable(true).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takePhoto.onPickFromGallery();
                        break;
                    case 1:
                        takePhoto.onPickFromCapture(imageUri);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

}
