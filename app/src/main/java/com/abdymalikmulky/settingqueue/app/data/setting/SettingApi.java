package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.app.data.setting.response.SettingNewResponse;
import com.abdymalikmulky.settingqueue.app.data.setting.response.SettingResponse;
import com.abdymalikmulky.settingqueue.util.EndpointUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by efishery on 09/05/17.
 */

public interface SettingApi {

    @GET(EndpointUtils.ENDPOINT_SETTING)
    public Call<SettingResponse> getAll(@Query("pond_id") long pondId);

    @POST(EndpointUtils.ENDPOINT_POST_SETTING)
    public Call<SettingNewResponse> create(@Query("pond_id") long pondId, @Query("feedWeight") int feedWeight,
                                           @Query("fishWeight") int fishWeight, @Query("freq") long freq,
                                           @Query("client_id") String clientId);


}
