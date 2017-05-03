package com.abdymalikmulky.settingqueue.app.pond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PondActivity extends AppCompatActivity implements PondContract.View{

    PondAdapter pondAdapter;
    RecyclerView rvPond;

    private PondContract.Presenter mPondPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        pondAdapter = new PondAdapter(pondList);
        rvPond.setAdapter(pondAdapter);
    }

    public void addPond(View view){
        int  idRand = new Random().nextInt(50) + 1;
        Pond pond = new Pond();
        pond.setName("Pond "+idRand);
        mPondPresenter.savePond(pond);
    }

    @Override
    public void setPresenter(PondContract.Presenter presenter) {
        mPondPresenter = presenter;
    }

    @Override
    public void showPonds(List<Pond> ponds) {
        pondAdapter.replace(ponds);
    }

    @Override
    public void showNoPond() {
        Toast.makeText(this, "NO POND", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showNewPondAdded(Pond pond) {
        pondAdapter.add(pond);
        Toast.makeText(this, "Added "+pond.getName(), Toast.LENGTH_SHORT).show();

    }
}
