package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.util.EndpointUtils;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public interface PondApi {

    public static final String URI_GET = EndpointUtils.ENDPOINT_LIST_POND;

    @GET(URI_GET)
    public Call<PondResponse> getAll();


}
