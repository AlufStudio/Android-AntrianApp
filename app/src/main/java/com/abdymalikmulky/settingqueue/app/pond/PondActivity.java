package com.abdymalikmulky.settingqueue.app.pond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.SettingQueueApplication;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

public class PondActivity extends AppCompatActivity implements PondContract.View{

    RecyclerView rvPond;

    private PondContract.Presenter mPondPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SettingQueueApplication)getApplication()).getAppComponent().inject(this);

        initRv();


        mPondPresenter = new PondPresenter(this);
        mPondPresenter.loadPonds();
    }

    private void initRv(){
        rvPond = (RecyclerView) findViewById(R.id.rv_list_pond);
        rvPond.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rvPond.setLayoutManager(llm);

        ArrayList<Pond> pondList = new ArrayList<>();
        for (int i=0; i<10; i++){
            Pond pond = new Pond();
            pond.setName("Kolam "+(i+1));
            pond.setId((i+1));
            pondList.add(pond);
        }
        PondAdapter adapter = new PondAdapter(pondList);
        rvPond.setAdapter(adapter);
    }

    public void addPond(View view){
        int  idRand = new Random().nextInt(50) + 1;
        Pond pond = new Pond();
        pond.setId(idRand);
        pond.setName("Pond "+idRand);
        mPondPresenter.savePond(pond);
    }

    @Override
    public void setPresenter(PondContract.Presenter presenter) {
        mPondPresenter = presenter;
    }

    @Override
    public void showPonds(List<Pond> ponds) {
        Toast.makeText(this, ponds.toString(), Toast.LENGTH_SHORT).show();
        Timber.d(ponds.toString());
    }

    @Override
    public void showNoPond() {
        Toast.makeText(this, "NO POND", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showNewPondAdded(Pond pond) {
        Toast.makeText(this, "Added "+pond.getName(), Toast.LENGTH_SHORT).show();
    }
}
