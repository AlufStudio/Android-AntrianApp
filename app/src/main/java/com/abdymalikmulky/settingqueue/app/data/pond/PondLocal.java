package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public class PondLocal implements PondDataSource {

    @Override
    public void load(LoadPondCallback callback) {
        List<Pond> ponds = SQLite.select()
                .from(Pond.class)
                .orderBy(Pond_Table.id,false)
                .queryList();
        if(ponds.size() > 0){
            callback.onLoaded((ArrayList<Pond>) ponds);
        }else {
            callback.onNoData("No Data");
        }
    }

    @Override
    public void save(Pond newPond, SavePondCallback callback) {
        //TODO :: Bedain idLocal samaa remote

        long pondId = 0;
        Pond pond = new Pond();
        pond = newPond;
        pondId = pond.insert();

        if(pondId > 0){
            pond.setId((int) pondId);
            callback.onSaved(pond);
        }else{
            callback.onFailed(new Throwable());
        }
    }

    @Override
    public void save(Pond pond, SaveRemotePondCallback callback) {

    }

    public void delete(Pond pond){
        SQLite.delete(Pond.class)
                .where(Pond_Table.clientId.is(pond.getClientId()))
                .async()
                .execute();
    }

    public void updateSyncState(Pond pondUpdate, SavePondCallback callback){
        SQLite.update(Pond.class)
                .set(Pond_Table.syncState.eq(AppUtils.STATE_SYNCED))
                .where(Pond_Table.clientId.is(pondUpdate.getClientId()))
                .async()
                .execute();

        callback.onSaved(pondUpdate);
    }
}
