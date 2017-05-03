package com.abdymalikmulky.settingqueue.app.data.pond;

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
        Pond pond = new Pond();
        pond = newPond;
        if(pond.save()){
            callback.onSaved(pond);
        }else{
            callback.onFailed("Fail");
        }
    }
}
