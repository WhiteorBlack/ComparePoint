package com.blm.comparepoint.async;

import android.provider.ContactsContract;
import android.text.TextUtils;

import com.blm.comparepoint.bean.Bean_SystemConfig;
import com.blm.comparepoint.interfacer.DataOprateInterfacer;
import com.blm.comparepoint.interfacer.PostCallBack;
import com.blm.comparepoint.untils.L;
import com.blm.comparepoint.untils.NetUtils;
import com.blm.comparepoint.wxapi.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * Created by 41508 on 2017/3/16.
 */

public class DataOprate {
    private static DataOprateInterfacer dataOprateInterfacer;

    public static DataOprate getInstance(DataOprateInterfacer oprateInterfacer) {
        dataOprateInterfacer = oprateInterfacer;
        return new DataOprate();
    }

    /**
     * 系统配置信息
     */
    public void getSystemConfig() {
        PostTools.postData(Constants.MAIN_URL + "Config/GetSysConfig", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    dataOprateInterfacer.getSystemConfigFail();
                    return;
                }
                dataOprateInterfacer.getSystemConfigSuccess(response);
            }
        });
    }

    /**
     * 游戏配置
     */
    public void getGameConfig() {
        PostTools.postData(Constants.MAIN_URL + "Config/GetRatioConfig", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (TextUtils.isEmpty(response)) {
                    dataOprateInterfacer.getGameCfgFail();
                    return;
                }
                dataOprateInterfacer.getGameCfgSuccess(response);
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
            }
        });
    }

    /**
     * 用户签到
     */
    public void signIn() {
        PostTools.postData(Constants.MAIN_URL + "User/Sign", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                dataOprateInterfacer.signSuccess(response);
            }
        });
    }

    /**
     * 检查版本更新
     */
    public void getVersion() {
        PostTools.postData(Constants.MAIN_URL + "Utils/CheckUpdate", null, new PostCallBack() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                dataOprateInterfacer.getVersion(response);
            }
        });
    }

    /**
     * 获取当前游戏的信息
     */
    public void getCurrentInfo() {
        PostTools.postData(Constants.MAIN_URL + "Game/GetCurRound", null, new PostCallBack() {
            @Override
            public void onResponse(String response,Headers headers) {
                super.onResponse(response);
                L.e("okhttp--"+headers.get("OkHttp-Received-Millis"));
                L.e("headDate--"+headers.get("Date"));
                dataOprateInterfacer.getCurrentInfo(response,headers.get("Date"));

            }

            @Override
            public void onAfter(Headers headers) {
                super.onAfter(headers);
            }
        });
    }

    public void betMoney(String bets){
        Map<String,String> params=new HashMap<>();
        params.put("RoundNo",Constants.ROUNDNO);
        params.put("Bets",bets);
        PostTools.postData(Constants.MAIN_URL+"Game/Bet",params,new PostCallBack(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                dataOprateInterfacer.betMoneyResult(response);
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
            }
        });
    }

}
