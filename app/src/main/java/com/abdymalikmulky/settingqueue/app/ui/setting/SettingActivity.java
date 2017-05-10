package com.abdymalikmulky.settingqueue.app.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.SettingQueueApplication;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.helper.DateTimeHelper;
import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.birbit.android.jobqueue.JobManager;

import java.util.List;

public class SettingActivity extends AppCompatActivity implements SettingContract.View{

    TextView tvSettingResult;
    Button btnSetting;

    long pondId;

    private JobManager jobManager;

    private SettingContract.Presenter mSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        pondId = getIntent().getLongExtra("pond_id", 0);

        jobManager = SettingQueueApplication.get().getJobManager();

        mSettingPresenter = new SettingPresenter(this, jobManager);

        initView();
    }

    private void initView(){
        tvSettingResult = (TextView) findViewById(R.id.tv_setting_result);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting setting = new Setting();
                setting.setId(1);
                setting.setFishWeight((int) ((System.currentTimeMillis()%3)*1000));
                setting.setFeedWeight((int) ((System.currentTimeMillis()%3)*10));
                setting.setFreq(5);
                setting.setCreatedAt(DateTimeHelper.getCurrentDateISO());
                setting.setSyncState(AppUtils.STATE_NOT_SYNCED);

                try {
                    mSettingPresenter.saveSetting(pondId, setting);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }


    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mSettingPresenter = presenter;
    }

    @Override
    public void showSetting(List<Setting> settings) {
        tvSettingResult.setText(settings.get(0).toString());
    }

    @Override
    public void showNoSetting(String msg) {
        btnSetting.setText(getString(R.string.btn_new_setting));
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNewSetting(Setting setting) {
        btnSetting.setText(getString(R.string.btn_edit_setting));
        mSettingPresenter.loadSetting(pondId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSettingPresenter.start();
        mSettingPresenter.loadSetting(pondId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSettingPresenter.stop();
    }


}
