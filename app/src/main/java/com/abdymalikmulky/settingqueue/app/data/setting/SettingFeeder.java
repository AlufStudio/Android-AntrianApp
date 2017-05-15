package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.feederconnector.FeederApiCallback;
import com.abdymalikmulky.feederconnector.FeederApiClient;
import com.abdymalikmulky.feederconnector.pojo.FeederApiSetting;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by efishery on 15/05/17.
 */

public class SettingFeeder implements SettingDataSource{

    private FeederApiClient feederApiClient;

    public SettingFeeder(FeederApiClient feederApiClient) {
        this.feederApiClient = feederApiClient;
    }

    @Override
    public void load(long pondId, LoadSettingCallback callback) {
        feederApiClient.getSetting(new FeederApiCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(String e) {
            }
        });
    }

    @Override
    public void getLast(final long pondId, final GetLastSettingCallback callback) {
        feederApiClient.getSetting(new FeederApiCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject settingResponse = new JSONObject(response);
                    int fishWeight = settingResponse.getInt("beratIkan");
                    int feedWeight = settingResponse.getInt("beratPakan");
                    int freq = settingResponse.getInt("frekuensi");

                    Setting setting = new Setting();
                    setting.setFishWeight(fishWeight);
                    setting.setFeedWeight(feedWeight);
                    setting.setFreq(freq);

                    callback.onGet(setting);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailed(e.toString());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            @Override
            public void onFailed(String e) {
                callback.onFailed(e);
            }
        });
    }


    @Override
    public void save(final Setting setting, final SaveSettingCallback callback) throws Throwable {

        FeederApiSetting feederApiSetting = new FeederApiSetting();
        feederApiSetting.setFeedingProgram(false);
        feederApiSetting.setFishType(1);
        feederApiSetting.setFishCount(1);
        feederApiSetting.setFeedType(9300);
        feederApiSetting.setFishWeight(setting.getFishWeight());
        feederApiSetting.setFrequency(setting.getFreq());
        feederApiSetting.setFeedWeight(setting.getFeedWeight());

        feederApiClient.postSetting(feederApiSetting, new FeederApiCallback() {
            @Override
            public void onSuccess(String result) {
              callback.onSaved(setting);
            }

            @Override
            public void onFailed(String e) {
                callback.onFailed(new Throwable(e));
            }
        });

    }

    @Override
    public void save(Setting setting, SaveRemoteSettingCallback callback) throws Throwable {

    }
}
