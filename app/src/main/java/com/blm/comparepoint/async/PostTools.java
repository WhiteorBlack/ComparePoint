package com.blm.comparepoint.async;


import com.blm.comparepoint.interfacer.PostCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostTools {

    public static void postData(final String url, Map<String, String> params, final PostCallBack postCallBack) {
        if (params == null)
            params = new HashMap<>();
        params.put("timestamp",System.currentTimeMillis()+"");
        OkHttpUtils.post().url(url).params(params).build().execute(postCallBack);
    }

    public static void postFile(String url, String uri, Map<String, String> params, PostCallBack postCallBack) {
        File file = new File(uri);
        if (file.exists()) {
            OkHttpUtils.postFile().url(url).file(file).params(params).build().execute(postCallBack);
        }
    }
}
