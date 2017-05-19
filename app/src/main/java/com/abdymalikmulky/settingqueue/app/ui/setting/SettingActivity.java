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

import com.abdymalikmulky.feederconnector.FeederApiClient;
import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.SettingQueueApplication;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingSp;
import com.abdymalikmulky.settingqueue.helper.DateTimeHelper;
import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.birbit.android.jobqueue.JobManager;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import timber.log.Timber;

public class SettingActivity extends AppCompatActivity implements SettingContract.View{

    //layout
    private TextView tvSettingResult,tvProcess;
    private Button btnSetting;

    //entity data
    private long pondId;
    private String pondClientId = "";
    private String pondName = "Pond Name";

    //Storage
    private SettingSp settingSp;

    //Job
    private JobManager jobManager;

    //presenter
    private SettingContract.Presenter mSettingPresenter;

    //Feeder
    private FeederApiClient feederApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        pondId = getIntent().getLongExtra("pond_id", 2);
        pondClientId = getIntent().getStringExtra("pond_client_id");
        pondName = getIntent().getStringExtra("pond_name");

        if(pondClientId==null){
            pondClientId = "d699ed17-ba0e-4d60-b232-8f401263ba53";
        }
        if(pondName==null){
            pondName = "Nama Pond";
        }
        setTitle(pondName);

        initModule();
        initView();
    }

    private void initModule(){
        settingSp = new SettingSp(getApplicationContext());

        jobManager = SettingQueueApplication.get().getJobManager();


        feederApiClient = new FeederApiClient(this);
        feederApiClient.setSsid(AppUtils.DUMMY_SSID);
        feederApiClient.setToken(AppUtils.DUMMY_TOKEN);


        mSettingPresenter = new SettingPresenter(this, jobManager, feederApiClient);

    }

    private void initView(){
        tvProcess = (TextView) findViewById(R.id.tv_setting_process);
        tvSettingResult = (TextView) findViewById(R.id.tv_setting_result);
        btnSetting = (Button) findViewById(R.id.btn_setting);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            tvProcess.append("\nMelakukan Setting....");
            btnSetting.setEnabled(false);

            Setting setting = new Setting();
            setting.setId(settingSp.getLastSettingId());
            setting.setClientId(UUID.randomUUID().toString());
            setting.setPondClientId(pondClientId);
                setting.setFishWeight((new Random().nextInt(19) + 1) * 1000);
                setting.setFeedWeight((new Random().nextInt(19) + 10));
                setting.setFreq((new Random().nextInt(38) + 1));
            setting.setCreatedAt(DateTimeHelper.getCurrentDateISO());
            setting.setSyncState(AppUtils.STATE_NOT_SYNCED);
                Timber.d("SETTING %s", setting.toString());

                try {
                    mSettingPresenter.saveSetting(pondId, setting);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            /*try {
                mSettingPresenter.saveSettingWithFeeder(pondId, setting);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }*/
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
                mSettingPresenter.loadSettingRemote(pondId);
                return true;
            case R.id.menu_sync_feeder:
                tvProcess.append("\nMengambil Setting Dari Feeder....");
                mSettingPresenter.loadSettingFeeder(pondId);
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
    public void showSettings(List<Setting> settings) {
        String readablePlainText = makeSettingStringReadble(settings.get(0));
        tvSettingResult.setText(readablePlainText);
        tvProcess.append("\n Berhasil");

        btnSetting.setEnabled(true);
        btnSetting.setText(getString(R.string.btn_edit_setting));
    }


    @Override
    public void showNoSetting(String msg) {
        btnSetting.setText(getString(R.string.btn_new_setting));
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSettingFeeder(Setting setting) {
        try {
            Timber.d("SettingData %s", setting.toString());

            //modify data feeder
            setting.setId(settingSp.getLastSettingId());
            setting.setClientId(UUID.randomUUID().toString());
            setting.setPondClientId(pondClientId);
            setting.setCreatedAt(DateTimeHelper.getCurrentDateISO());
            setting.setSyncState(AppUtils.STATE_NOT_SYNCED);

            mSettingPresenter.saveSetting(pondId, setting);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    @Override
    public void showFailSetting(int code, String message) {
        tvProcess.append("\n Gagals");
        btnSetting.setEnabled(true);

        Toast.makeText(this, code + " - " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogMessageType(String type) {
        tvProcess.append(" " + type);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSettingPresenter.start();
        mSettingPresenter.loadSettingRemote(pondId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSettingPresenter.stop();
    }


    //Hanya kepentingan view
    private String makeSettingStringReadble(Setting setting){
        String plain = "SETTING";
        plain += "\nBerat Pakan : "+setting.getFeedWeight();
        plain += "\nBerat Ikan : "+setting.getFishWeight();
        plain += "\nFrekuensi : "+setting.getFreq();
        plain += "\nSTATE : "+setting.getSyncState();
        return plain;
    }

}
