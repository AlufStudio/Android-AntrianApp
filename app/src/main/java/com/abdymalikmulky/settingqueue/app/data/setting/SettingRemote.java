package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.app.data.setting.response.SettingNewResponse;
import com.abdymalikmulky.settingqueue.app.data.setting.response.SettingResponse;
import com.abdymalikmulky.settingqueue.app.jobs.util.NetworkException;
import com.abdymalikmulky.settingqueue.helper.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by efishery on 09/05/17.
 */

public class SettingRemote implements SettingDataSource {
    SettingApi api;

    public SettingRemote() {
        api = ApiHelper.client().create(SettingApi.class);
    }

    @Override
    public void load(long pondId, final LoadSettingCallback callback) {
        Call<SettingResponse> call = api.getAll(pondId);
        call.enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(Call<SettingResponse> call, Response<SettingResponse> response) {
                List<Setting> settings = response.body().getSettings();
                if(settings.size() > 0){
                    callback.onLoaded(settings);
                }else{
                    callback.onNoData("No Data");
                }
            }
            @Override
            public void onFailure(Call<SettingResponse> call, Throwable t) {
                callback.onNoData(t.toString());
                Timber.d("DataFail-Setting : %s", t.toString());
            }
        });
    }

    @Override
    public void save(Setting setting, SaveSettingCallback callback) {

    }

    @Override
    public void save(Setting setting, final SaveRemoteSettingCallback callback) throws Throwable {
        Response<SettingNewResponse> response = api.create(setting.getPondId(), setting.getFeedWeight(),
                setting.getFishWeight(), setting.getFreq(), setting.getClientId()).execute();

        if(response.isSuccessful()){
            Setting newSetting = response.body().getSetting();
            callback.onSaved(newSetting);
        }else{
            callback.onFailed(new NetworkException(response.code()));
        }
        /*Call<SettingNewResponse> call = api.create(setting.getPondId(), setting.getFeedWeight(),
                setting.getFishWeight(), setting.getFreq(), UUID.randomUUID().toString());
        call.enqueue(new Callback<SettingNewResponse>() {
            @Override
            public void onResponse(Call<SettingNewResponse> call, Response<SettingNewResponse> response) {
                Setting setting = response.body().getSetting();
                callback.onSaved(setting);
            }
            @Override
            public void onFailure(Call<SettingNewResponse> call, Throwable t) {
                try {
                    callback.onFailed(t);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Timber.d("DataFail-Setting : %s", t.toString());
            }
        });*/
    }
}
