package com.abdymalikmulky.settingqueue.app.data.pond;

import java.io.IOException;
import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public interface PondDataSource {

    interface LoadPondCallback {
        void onLoaded(List<Pond> ponds);
        void onNoData(String msg);
    }
    interface SavePondCallback {
        void onSaved(Pond pond);
        void onFailed(Throwable t);
    }
    interface SaveRemotePondCallback {
        void onSaved(Pond pond);
        void onFailed(Throwable t) throws Throwable;
    }


    void load(LoadPondCallback callback);

    void save(Pond pond, SavePondCallback callback);

    void save(Pond pond, SaveRemotePondCallback callback) throws IOException, Throwable;


}
