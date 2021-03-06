package com.zhy.http.okhttp;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFileBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.cookie.SimpleCookieJar;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils
{
    public static final String TAG = "OkHttpUtils";
    public static final long DEFAULT_MILLISECONDS = 10000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtils()
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .build();
                return chain.proceed(request);
            }
        });
        //cookie enabled
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        mDelivery = new Handler(Looper.getMainLooper());


        if (true)
        {
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            });
        }

        mOkHttpClient = okHttpClientBuilder.build();
    }

    private OkHttpUtils(final String token)
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", token)
                        .build();
                return chain.proceed(request);
            }
        });
        //cookie enabled
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        mDelivery = new Handler(Looper.getMainLooper());


        if (true)
        {
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            });
        }

        mOkHttpClient = okHttpClientBuilder.build();
    }

    private boolean debug;
    private String tag;

    public OkHttpUtils debug(String tag)
    {
        debug = true;
        this.tag = tag;
        return this;
    }

    public static OkHttpUtils getInstance(String token)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(token);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery()
    {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }


    public static GetBuilder get()
    {
        return new GetBuilder();
    }

 /*   public static PostStringBuilder postString()
    {
        return new PostStringBuilder();
    }*/

    public static PostFileBuilder postFile()
    {
        return new PostFileBuilder();
    }
    public static PostFormBuilder post()
    {
        return new PostFormBuilder();
    }


    public void execute(final RequestCall requestCall, Callback callback)
    {
        if (debug)
        {
            if(TextUtils.isEmpty(tag))
            {
                tag = TAG;
            }
            Log.d(tag, "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getOkHttpRequest().toString() + "}");
        }

        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        final Call call = requestCall.getCall();
        
        call.enqueue(new okhttp3.Callback() {
        	
			@Override
			public void onResponse(Response response) throws IOException {
				 if (response.code() >= 400 && response.code() <= 599)
	                {
	                    try
	                    {
	                        sendFailResultCallback(call, new RuntimeException(response.body().string()), finalCallback,response.headers());
	                    } catch (IOException e)
	                    {
	                        e.printStackTrace();
	                    }
	                    return;
	                }

	                try
	                {
	                    Object o = finalCallback.parseNetworkResponse(response);
	                    sendSuccessResultCallback(o, finalCallback,call,response.headers());
	                } catch (Exception e)
	                {
	                    sendFailResultCallback(call, e, finalCallback, response.headers());
	                }
			}
			
			@Override
			public void onFailure(Request arg0, IOException e) {
				 sendFailResultCallback(call, e, finalCallback, null);
				
			}
		});
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final Headers headers)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e);
                callback.onAfter(headers);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final Call call, final Headers headers)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onResponse(object,headers);
                callback.onAfter(headers);
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }


    public void setCertificates(InputStream... certificates)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
    }


    public void setConnectTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }
}

