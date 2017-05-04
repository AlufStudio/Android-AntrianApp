package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.helper.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public class PondRemote implements PondDataSource {

    PondApi api;

    public PondRemote() {
        api = ApiHelper.client().create(PondApi.class);
    }

    @Override
    public void load(final LoadPondCallback callback) {
        Call<PondResponse> call = api.getAll();
        call.enqueue(new Callback<PondResponse>() {
            @Override
            public void onResponse(Call<PondResponse> call, Response<PondResponse> response) {
                List<Pond> ponds = response.body().getPonds();
                callback.onLoaded(ponds);

                Timber.d("DataSuccess : %s", response.body().getPonds().toString());
            }

            @Override
            public void onFailure(Call<PondResponse> call, Throwable t) {
                callback.onNoData(t.toString());

                Timber.d("DataFail : %s", t.toString());
            }
        });
    }

    @Override
    public void save(final Pond pond, final SavePondCallback callback) {
        Call<PondNewResponse> call = api.create(pond.getName(), pond.getClientId(), pond.getUserId());
        call.enqueue(new Callback<PondNewResponse>() {
            @Override
            public void onResponse(Call<PondNewResponse> call, Response<PondNewResponse> response) {
                if(response.isSuccessful()){
                    Timber.d("DataSuccess : %s", response.body().getPond().toString());
                    Pond pond = response.body().getPond();
                    callback.onSaved(pond);
                }else{
                    Timber.d("DataFail : %s", response.errorBody());
                    callback.onFailed("Fail");
                }

            }

            @Override
            public void onFailure(Call<PondNewResponse> call, Throwable t) {
                Timber.d("DataFail : %s", t.toString());
                callback.onFailed("Fail");
            }
        });
    }
}
