package com.abdymalikmulky.feederconnector;

import android.app.Activity;
import android.content.Context;

import com.abdymalikmulky.feederconnector.helper.volley.VolleyCallback;
import com.abdymalikmulky.feederconnector.helper.volley.VolleyHelper;
import com.abdymalikmulky.feederconnector.helper.wifi.WifiConnector;
import com.abdymalikmulky.feederconnector.helper.wifi.WifiConnectorCallback;
import com.abdymalikmulky.feederconnector.pojo.FeederApiSetting;
import com.abdymalikmulky.feederconnector.util.ApiUtil;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/20/17.
 */

public class FeederApiClient {

    private Context context;

    private VolleyHelper volleyHelper;
    private WifiConnector wifiConnector;

    private String ssid;
    private String token;

    public FeederApiClient(Activity activity) {
        this.context = activity.getApplicationContext();

        this.wifiConnector = new WifiConnector(activity);

        this.volleyHelper = new VolleyHelper(context);

//        wifiConnector.disconnect();

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
        wifiConnector.setSsid(ssid);
        wifiConnector.setPassword("");
    }



    /**
     * Get Wifi State
     *
     * @param callback
     */
    public void getWifiState(final FeederApiCallback callback) {
        final String url = ApiUtil.BASE_URL + ApiUtil.ENDPOINT_WIFI_STATE;

        wifiConnector.connect(new WifiConnectorCallback() {
            @Override
            public void onConnected(String respon) {
                volleyHelper.getResponseFeeder(Request.Method.GET, url, getToken(), null, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        callback.onSuccess(result);
                        wifiConnector.disconnect();
                    }

                    @Override
                    public void onFailedResponse(String e) {
                        callback.onFailed(e);
                        wifiConnector.disconnect();
                    }
                }, true);
            }
            @Override
            public void onFail(String error) {
                callback.onFailed(error);
            }
        });

    }



    /**
     * Get Info Feeder
     *
     * @param callback
     */
    public void getInfo(final FeederApiCallback callback) {
        final String url = ApiUtil.BASE_URL + ApiUtil.ENDPOINT_INFO;

        wifiConnector.connect(new WifiConnectorCallback() {
            @Override
            public void onConnected(String respon) {
                volleyHelper.getResponseFeeder(Request.Method.GET, url, getToken(), null, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        callback.onSuccess(result);
                        wifiConnector.disconnect();
                    }

                    @Override
                    public void onFailedResponse(String e) {
                        callback.onFailed(e);
                        wifiConnector.disconnect();
                    }
                }, true);
            }
            @Override
            public void onFail(String error) {
                callback.onFailed(error);
            }
        });

    }



    /**
     * Get Info FeederApiSetting
     * @param callback
     * @Result Beratpakan, dll
     */
    public void getSetting(final FeederApiCallback callback) {
        final String url = ApiUtil.BASE_URL + ApiUtil.ENDPOINT_SETTING;
        wifiConnector.connect(new WifiConnectorCallback() {
            @Override
            public void onConnected(String respon) {
                volleyHelper.getResponseFeeder(Request.Method.GET, url, getToken(), null, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        callback.onSuccess(result);
                        wifiConnector.disconnect();
                    }

                    @Override
                    public void onFailedResponse(String e) {
                        callback.onFailed(e);
                        wifiConnector.disconnect();
                    }
                }, true);
            }

            @Override
            public void onFail(String error) {
                callback.onFailed(error);
            }
        });

    }

    /**
     * Post Setting Feeder, Mengirimkan Data Setting Pada Feeder
     * @param setting (feedingProgram, fishWeight, fishCount, fishType, feedWeight, frequency, feedType)
     * @param callback
     */
    public void postSetting(FeederApiSetting setting, final FeederApiCallback callback) {
        final String url = ApiUtil.BASE_URL + ApiUtil.ENDPOINT_SETTING;

        final Map<String, String> postParam = new HashMap<String, String>();

        //Param Setting
        postParam.put(ApiUtil.PARAM_FEEDING_PROGRAM, (setting.isFeedingProgram()) ? ApiUtil.VALUE_FEEDING_PROGRAM : ApiUtil.VALUE_FEEDING_MANUAL);
        postParam.put(ApiUtil.PARAM_FISH_WEIGHT, String.valueOf(setting.getFishWeight()));
        postParam.put(ApiUtil.PARAM_FISH_COUNT, String.valueOf(setting.getFishCount()));
        postParam.put(ApiUtil.PARAM_FISH_TYPE, String.valueOf(setting.getFishType()));
        postParam.put(ApiUtil.PARAM_FEED_WEIGHT, String.valueOf(setting.getFeedWeight()));
        postParam.put(ApiUtil.PARAM_FEED_TYPE, String.valueOf(setting.getFeedType()));
        postParam.put(ApiUtil.PARAM_FREQUENCY, String.valueOf(setting.getFrequency()));



        wifiConnector.connect(new WifiConnectorCallback() {
            @Override
            public void onConnected(String respon) {
                volleyHelper.getResponseFeeder(Request.Method.POST, url, getToken(), postParam, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        callback.onSuccess(result);
                        wifiConnector.disconnect();
                    }

                    @Override
                    public void onFailedResponse(String e) {
                        callback.onFailed(e);
                        wifiConnector.disconnect();
                    }
                }, true);
            }

            @Override
            public void onFail(String error) {
                callback.onFailed(error);
            }
        });

    }



    /**
     * Get Current Time Feeder
     * @param callback
     * @Result current time, status
     */
    public void getTime(final FeederApiCallback callback) {
        final String url = ApiUtil.BASE_URL + ApiUtil.ENDPOINT_TIME;
        wifiConnector.connect(new WifiConnectorCallback() {
            @Override
            public void onConnected(String respon) {
                volleyHelper.getResponseFeeder(Request.Method.GET, url, getToken(), null, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        callback.onSuccess(result);
                        wifiConnector.disconnect();
                    }

                    @Override
                    public void onFailedResponse(String e) {
                        callback.onFailed(e);
                        wifiConnector.disconnect();
                    }
                }, true);
            }

            @Override
            public void onFail(String error) {
                callback.onFailed(error);
            }
        });

    }

    /**
     * Post Setting Feeder, Mengirimkan Data Setting Pada Feeder
     * @param timestamp , ex. 1490745021
     * @param callback
     */
    public void postTime(long timestamp, final FeederApiCallback callback) {
        final String url = ApiUtil.BASE_URL + ApiUtil.ENDPOINT_TIME;

        final Map<String, String> postParam = new HashMap<String, String>();

        //Param
        postParam.put(ApiUtil.PARAM_TIMESTAMP, String.valueOf(timestamp));

        wifiConnector.connect(new WifiConnectorCallback() {
            @Override
            public void onConnected(String respon) {
                volleyHelper.getResponseFeeder(Request.Method.POST, url, getToken(), postParam, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        callback.onSuccess(result);
                        wifiConnector.disconnect();
                    }

                    @Override
                    public void onFailedResponse(String e) {
                        callback.onFailed(e);
                        wifiConnector.disconnect();
                    }
                }, true);
            }

            @Override
            public void onFail(String error) {
                callback.onFailed(error);
            }
        });

    }



}

