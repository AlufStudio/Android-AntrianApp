package com.abdymalikmulky.settingqueue.app.ui.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        String clientId = getIntent().getStringExtra("pond_id");
        Toast.makeText(this, String.valueOf(clientId), Toast.LENGTH_SHORT).show();
    }
}
