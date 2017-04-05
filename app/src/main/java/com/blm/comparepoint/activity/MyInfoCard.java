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
import com.blm.comparepoint.wxapi.Constants;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
                showShare("", Constants.SHARE_URL,"",Constants.PIC_URL);
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

    private void showShare(String title, String url, String text, String imgurl) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imgurl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(this);
    }

}
