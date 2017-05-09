package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.app.job.NetworkException;
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

public class PondRemote {

    PondApi api;

    public PondRemote() {
        api = ApiHelper.client().create(PondApi.class);
    }

    public void load(final PondDataSource.LoadPondCallback callback) {
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

    public void save(final Pond pond, final PondDataSource.SaveRemotePondCallback callback) throws Throwable {
        //TODO :: Pas on success mending langsung update local,
        Response<PondNewResponse> response = api.create(pond.getName(), pond.getClientId(), pond.getUserId()).execute();
        if(response.isSuccessful()){
            Pond newPondSynced = response.body().getPond();
            callback.onSaved(newPondSynced);
        }else{
            callback.onFailed(new NetworkException(response.code()));
        }
    }
}
