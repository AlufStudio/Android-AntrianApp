package com.abdymalikmulky.settingqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abdymalikmulky.settingqueue.pond.Pond;
import com.abdymalikmulky.settingqueue.pond.PondAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvPond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRv();
    }

    private void initRv(){
        rvPond = (RecyclerView) findViewById(R.id.rv_list_pond);
        rvPond.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rvPond.setLayoutManager(llm);

        ArrayList<Pond> ponds = new ArrayList<>();
        for (int i=0; i<10; i++){
            ponds.add(new Pond(i+1,(i+1)*23,"Kolam "+i,"Wawan "+i));
        }
        PondAdapter adapter = new PondAdapter(ponds);
        rvPond.setAdapter(adapter);
    }
}
