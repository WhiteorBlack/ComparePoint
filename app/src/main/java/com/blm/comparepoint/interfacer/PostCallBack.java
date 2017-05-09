package com.blm.comparepoint.interfacer;


import com.blm.comparepoint.untils.L;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

public class PostCallBack extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        // TODO Auto-generated method stub
        String result = response.body().string();
        L.d(result);
        return result;
    }

    @Override
    public void onError(okhttp3.Call call, Exception e) {
        L.e("error---"+e.toString());
    }


    @Override
    public void onResponse(String response) {
        // TODO Auto-generated method stub
        L.e(response);
    }

    @Override
    public void onResponse(String response,Headers headers) {
        // TODO Auto-generated method stub
        L.e(response);
    }

    @Override
    public void onBefore(Request request) {
        // TODO Auto-generated method stub
        super.onBefore(request);
    }

    @Override
    public void onAfter(Headers headers) {
        // TODO Auto-generated method stub
        super.onAfter(headers);
    }

}
