package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.util.AppUtils;

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
        pondRemote.load(new LoadPondCallback() {
            @Override
            public void onLoaded(List<Pond> ponds) {
                for (Pond pond :ponds) {
                    pond.setSyncState(AppUtils.STATE_SYNCED);
                    pondLocal.save(pond, new SaveRemotePondCallback() {
                        @Override
                        public void onSaved(Pond pond) {
                        }
                        @Override
                        public void onFailed(Throwable t) throws Throwable {
                        }
                    });
                }
                callback.onLoaded(ponds);
            }

            @Override
            public void onNoData(String msg) {
                callback.onNoData(msg);
            }
        });
    }

    public void loadLocal(final LoadPondCallback callback) {
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
    }

    @Override
    public void save(Pond pond, final SavePondCallback callback) {
        pondLocal.save(pond, new SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                callback.onSaved(pond);
            }

            @Override
            public void onFailed(Throwable t) {
                callback.onFailed(t);
            }
        });
    }

    @Override
    public void save(Pond pond, SaveRemotePondCallback callback) {

    }

}
