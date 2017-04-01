package com.blm.comparepoint.async;


import android.content.Context;

import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.NetUtils;
import com.blm.comparepoint.wxapi.Constants;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PostTools {

    public static void postData(final String url, Map<String, String> params, final PostCallBack postCallBack) {
        if (params == null)
            params = new HashMap<>();
        long timeStamp=System.currentTimeMillis();
        params.put("Sign", NetUtils.get32MD5Str(timeStamp));
        params.put("Token", Constants.USERTOKEN);
        params.put("timestamp",timeStamp+"");
        OkHttpUtils.post().url(url).params(params).build().execute(postCallBack);
    }

    public static void postFile(String url, String uri, Map<String, String> params, PostCallBack postCallBack) {
        File file = new File(uri);
        if (file.exists()) {
            OkHttpUtils.postFile().url(url).file(file).params(params).build().execute(postCallBack);
        }
    }

    public static void getDataWithNone(Context context, String url, Map<String, String> params, PostCallBack postCallBack) {
        OkHttpUtils.getInstance().setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        OkHttpUtils.get().url(url).params(params).build().execute(postCallBack);
    }

    public static void getData(final String url, Map<String, String> params, final PostCallBack postCallBack) {
        if (params == null)
            params = new HashMap<>();
        long timeStamp=System.currentTimeMillis();
        params.put("Sign", NetUtils.get32MD5Str(timeStamp));
        params.put("Token", Constants.USERTOKEN);
        params.put("timestamp",timeStamp+"");
        OkHttpUtils.get().url(url).params(params).build().execute(postCallBack);
    }
}
