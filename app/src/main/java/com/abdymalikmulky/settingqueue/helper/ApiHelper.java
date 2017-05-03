package com.abdymalikmulky.settingqueue.helper;

import com.abdymalikmulky.settingqueue.util.EndpointUtils;
import com.abdymalikmulky.settingqueue.util.InterceptorUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abdymalikmulky on 1/27/17.
 */

public class ApiHelper {
    public static final String BASE_URL = EndpointUtils.BASE_URL;
    private static Retrofit retrofit = null;

    public static Retrofit client() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();



        //add cache to the client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(InterceptorUtils.getLoggingInterceptor())
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }



}
