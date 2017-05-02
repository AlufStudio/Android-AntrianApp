package com.abdymalikmulky.settingqueue.app.data.pond;

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
        void onFailed(String msg);
    }


    void load(LoadPondCallback callback);

    void save(Pond pond, SavePondCallback callback);

}
