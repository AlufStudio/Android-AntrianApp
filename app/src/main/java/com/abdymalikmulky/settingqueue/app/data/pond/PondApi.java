package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.util.EndpointUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public interface PondApi {

    @GET(EndpointUtils.ENDPOINT_LIST_POND)
    public Call<PondResponse> getAll();

    @POST(EndpointUtils.ENDPOINT_POST_POND)
    public Call<PondNewResponse> create(@Query("name") String name, @Query("client_id") String clientId, @Query("user_id") long userId);


}
