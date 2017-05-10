package com.abdymalikmulky.settingqueue.app.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.SettingQueueApplication;
import com.birbit.android.jobqueue.JobManager;

public class SettingActivity extends AppCompatActivity implements SettingContract.View{

    private JobManager jobManager;

    private SettingContract.Presenter mSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        String clientId = getIntent().getStringExtra("pond_id");
        Toast.makeText(this, String.valueOf(clientId), Toast.LENGTH_SHORT).show();

        jobManager = SettingQueueApplication.get().getJobManager();

        mSettingPresenter = new SettingPresenter(this, jobManager);
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mSettingPresenter = presenter;
    }

    @Override
    public void showSetting(long pondId) {

    }

    @Override
    public void showNoSetting() {

    }

    @Override
    public void showNewSetting() {

    }
}
