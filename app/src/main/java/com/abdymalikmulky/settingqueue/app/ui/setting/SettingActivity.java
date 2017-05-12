package com.abdymalikmulky.settingqueue.app.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.SettingQueueApplication;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingSp;
import com.abdymalikmulky.settingqueue.helper.DateTimeHelper;
import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.birbit.android.jobqueue.JobManager;

import java.util.List;
import java.util.UUID;

public class SettingActivity extends AppCompatActivity implements SettingContract.View{

    private TextView tvSettingResult;
    private Button btnSetting;

    private long pondId;
    private String pondClientId;

    private SettingSp settingSp;

    private JobManager jobManager;

    private SettingContract.Presenter mSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        pondId = getIntent().getLongExtra("pond_id", 0);
        pondClientId = getIntent().getStringExtra("pond_client_id");

        jobManager = SettingQueueApplication.get().getJobManager();

        settingSp = new SettingSp(getApplicationContext());

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
                setting.setId(settingSp.getLastSettingId());
                setting.setClientId(UUID.randomUUID().toString());
                setting.setPondClientId(pondClientId);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sync:
                mSettingPresenter.syncSetting(pondId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mSettingPresenter = presenter;
    }

    @Override
    public void showSetting(List<Setting> settings) {
        tvSettingResult.setText(settings.get(0).toString());
        btnSetting.setText(getString(R.string.btn_edit_setting));
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
        mSettingPresenter.syncSetting(pondId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSettingPresenter.stop();
    }


}
