package com.abdymalikmulky.settingqueue.app.ui.pond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.SettingQueueApplication;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondSp;
import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.birbit.android.jobqueue.JobManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import timber.log.Timber;

public class PondActivity extends AppCompatActivity implements PondContract.View{

    JobManager jobManager;

    PondSp pondSp;


    PondAdapter pondAdapter;
    RecyclerView rvPond;


    private ArrayList<Pond> pondList;
    private PondContract.Presenter mPondPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pondList = new ArrayList<>();

        initRv();



        jobManager = SettingQueueApplication.get().getJobManager();

        pondSp = new PondSp(getApplicationContext());

        mPondPresenter = new PondPresenter(this, jobManager);

        mPondPresenter.loadPonds();


    }

    private void initRv(){
        rvPond = (RecyclerView) findViewById(R.id.rv_list_pond);
        rvPond.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rvPond.setLayoutManager(llm);
        pondAdapter = new PondAdapter(pondList);
        rvPond.setAdapter(pondAdapter);
    }

    public void addPond(View view){
        int  idRand = new Random().nextInt(50) + 1;
        Pond pond = new Pond();
        pond.setId(pondSp.getLastPondId());
        pond.setName("Pond "+AppUtils.getSaltString());
        pond.setUserId(1);
        pond.setClientId(UUID.randomUUID().toString());
        pond.setSyncState(AppUtils.STATE_NOT_SYNCED);


        mPondPresenter.savePond(pond);
    }

    @Override
    public void setPresenter(PondContract.Presenter presenter) {
        mPondPresenter = presenter;
    }

    @Override
    public void showPonds(List<Pond> ponds) {
        Timber.d("LISTPONDS %s", ponds.toString());

        pondAdapter.replace(ponds);
    }

    @Override
    public void showNoPond() {
        Toast.makeText(this, "NO POND", Toast.LENGTH_SHORT).show();
        pondAdapter.replace(pondList);
    }

    @Override
    public void showDeletedPond(Pond pond) {
        Toast.makeText(this, "Sync Failure : Pond "+pond.getClientId()+"-"+pond.getName()+" Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNewPondAdded(Pond pond) {
        pondAdapter.add(pond);
        Toast.makeText(this, "Added "+pond.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPondPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPondPresenter.stop();
    }
}
