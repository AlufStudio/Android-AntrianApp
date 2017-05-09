package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.app.data.pond.response.PondNewResponse;
import com.abdymalikmulky.settingqueue.app.data.pond.response.PondResponse;
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
    public Call<PondResponse> getAll(@Query("pond_id") int pondId);

    @POST(EndpointUtils.ENDPOINT_POST_SETTING)
    public Call<PondNewResponse> create(@Query("pond_id") int pondId, @Query("feedWeight") int feedWeight,
                                        @Query("fishWeight") int fishWeight, @Query("freq") long freq,
                                        @Query("client_id") int clientId);


}
