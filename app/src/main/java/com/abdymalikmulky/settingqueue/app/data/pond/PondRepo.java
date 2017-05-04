package com.abdymalikmulky.settingqueue.app.data.pond;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/3/17.
 */

public class PondRepo implements PondDataSource {

    PondRemote pondRemote;
    PondLocal pondLocal;

    public PondRepo(PondRemote pondRemote, PondLocal pondLocal) {
        this.pondRemote = pondRemote;
        this.pondLocal = pondLocal;
    }

    @Override
    public void load(final LoadPondCallback callback) {
        pondLocal.load(new LoadPondCallback() {
            @Override
            public void onLoaded(List<Pond> ponds) {
                callback.onLoaded(ponds);
            }

            @Override
            public void onNoData(String msg) {
                callback.onNoData(msg);
            }
        });

/*

        pondRemote.load(new LoadPondCallback() {
            @Override
            public void onLoaded(List<Pond> ponds) {
                callback.onLoaded(ponds);
            }

            @Override
            public void onNoData(String msg) {
                callback.onNoData(msg);
            }
        });
*/

    }

    @Override
    public void save(Pond pond, final SavePondCallback callback) {
        pondLocal.save(pond, new SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                callback.onSaved(pond);
            }

            @Override
            public void onFailed(String msg) {
                callback.onFailed(msg);
            }
        });
    }

    public void saveRemote(Pond pond, final SavePondCallback callback) {
        pondRemote.save(pond, new SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                callback.onSaved(pond);
            }

            @Override
            public void onFailed(String msg) {
                callback.onFailed(msg);
            }
        });
    }

}
