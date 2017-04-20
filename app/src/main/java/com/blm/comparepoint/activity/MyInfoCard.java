package com.blm.comparepoint.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.R;
import com.blm.comparepoint.async.PostTools;
import com.blm.comparepoint.bean.BaseBean;
import com.blm.comparepoint.bean.Bean_UploadAvater;
import com.blm.comparepoint.dialog.ChangeNamePop;
import com.blm.comparepoint.interfacer.PopInterfacer;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.SPUtils;
import com.blm.comparepoint.untils.T;
import com.blm.comparepoint.widget.CircleImageView;
import com.blm.comparepoint.wxapi.Constants;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 41508 on 2017/3/8.
 */

public class MyInfoCard extends TakePhotoActivity implements PopInterfacer {
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
    private ChangeNamePop changeNamePop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_card);
        ButterKnife.bind(this);
        setUserInfo();
    }

    private void setUserInfo() {
        Glide.with(this).load((String) SPUtils.get(this, Constants.AVATAR, "")).into(imgAvatar);
        txtName.setText((String) SPUtils.get(this, Constants.NICKNAME, ""));
        txtMoney.setText(SPUtils.get(this, Constants.USERAMOUNT, 0l) + "");
        txtRedMoney.setText(SPUtils.get(this, Constants.ACTIVEAMOUNT, 0l) + "");
        imgSign.setEnabled(!(boolean) SPUtils.get(this, Constants.ISSIGN, false));
        if ((boolean) SPUtils.get(this, Constants.ISSIGN, false)) {
            imgSign.setText("已签到");
        } else {
            imgSign.setText("签到");
        }
    }

    @OnClick({R.id.img_back, R.id.fl_change_avatar, R.id.fl_my_name, R.id.fl_my_sex, R.id.fl_city, R.id.img_share, R.id.img_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_change_avatar:
                takePhoto();
                break;
            case R.id.fl_my_name:
                if (changeNamePop == null) {
                    changeNamePop = new ChangeNamePop(MyInfoCard.this);
                    changeNamePop.setPopInterfacer(MyInfoCard.this, 1);
                }
                changeNamePop.showPop(txtCity);
                break;
            case R.id.fl_my_sex:
                break;
            case R.id.fl_city:
                break;
            case R.id.img_share:
                showShare("猜大小", Constants.SHARE_URL, "快来一起玩儿吧", Constants.PIC_URL);
                break;
            case R.id.img_sign:
                signIn();
                break;
            case R.id.img_back:
                finish();
                break;
        }
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
                        SPUtils.put(MyInfoCard.this, Constants.USERAMOUNT, (long) ((long) SPUtils.get(MyInfoCard.this, Constants.USERAMOUNT, 0) +
                                (int) SPUtils.get(MyInfoCard.this, Constants.SIGNBOUNS, 0)));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtMoney.setText(SPUtils.get(MyInfoCard.this, Constants.USERAMOUNT, 0) + "");
                            }
                        });
                    } else {
                        imgSign.setEnabled(true);
                        T.showShort(MyInfoCard.this, baseBean.Msg);
                    }
                }
            }
        });
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
        String picPath = result.getImage().getPath();
        if (!TextUtils.isEmpty(picPath)) {
            uploadPic(picPath);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        T.showShort(this, msg);
    }

    private void uploadPic(String picPath) {
        Map<String, String> params = new HashMap<>();
        params.put("avatarBase64Code", convertIconToString(picPath));
        PostTools.postData(Constants.MAIN_URL + "User/UploadAvatar", params, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                Bean_UploadAvater baseBean = new Gson().fromJson(response, Bean_UploadAvater.class);
                if (baseBean.Success) {
                    SPUtils.put(MyInfoCard.this, Constants.AVATAR, baseBean.Data);
                    Glide.with(MyInfoCard.this).load(baseBean.Data).into(imgAvatar);
                } else {
                    T.showShort(MyInfoCard.this, baseBean.Msg);
                }
            }
        });
    }

    public static String convertIconToString(String path) {
        if (TextUtils.isEmpty(path))
            return "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        Bitmap bitmap = compImageByPath(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    public static Bitmap compImageByPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;// 这里设置高度�?800f
        float ww = 480f;// 这里设置宽度�?480f
        int be = 1;// be=1表示不缩�?
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩�?
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩�?
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        return compressImage(bitmap);// 压缩好比例大小后再进行质量压�?
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，100表示不压缩，把压缩后的数据存放到baos
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos
            options -= 10;// 每次都减�?10
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 4;
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, bitmapOptions);// 把ByteArrayInputStream数据生成图片
        return bitmap;
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

    @Override
    public void OnDismiss(int flag) {

    }

    @Override
    public void OnConfirm(int flag, Bundle bundle) {
        if (bundle != null && bundle.getBoolean("success")) {
            SPUtils.put(this, Constants.NICKNAME, bundle.getString("name"));
            txtName.setText(bundle.getString("name"));
        }
    }

    @Override
    public void OnCancle(int flag) {

    }
}
