package com.abdymalikmulky.feederconnector.helper.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.abdymalikmulky.feederconnector.util.ConnectionUtil;

import java.util.List;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/20/17.
 */

public class WifiConnector {
    private int tryToConnect;
    public int maxTryToConnect = 5; //second * 2

    String ssid;
    String password;
    String type;

    Activity activity;
    Context context;

    public WifiConnector(Activity activity, String ssid) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.ssid = ssid;
    }

    public WifiConnector(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }


    public int getMaxTryToConnect() {
        return maxTryToConnect;
    }

    public void setMaxTryToConnect(int maxTryToConnect) {
        this.maxTryToConnect = maxTryToConnect;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void connect(final WifiConnectorCallback fcCallback) {
        tryToConnect = 0;
        if (initConnection()) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (!isConnected() && tryToConnect < maxTryToConnect) {
                        try {
                            Thread.sleep(2000);
                            Timber.d("Try to Connect %s",tryToConnect);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tryToConnect++;
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tryToConnect == maxTryToConnect) {
                                ConnectionUtil.disconnect(activity);
                                fcCallback.onFail("Fail Connect");
                            } else {
                                fcCallback.onConnected("Success");
                            }
                        }
                    });
                }
            };
            t.start();
        }else{
            fcCallback.onFail("Fail Connect");
        }
    }

    /**
     * Reference on http://stackoverflow.com/questions/36098871/how-to-search-and-connect-to-a-specific-wifi-network-in-android-programmatically
     * https://infoqueue.wordpress.com/2014/07/14/join-a-wifi-network-programmatically-in-android/
     * @return
     */
    public boolean initConnection() {
        int wifiId = -1;
        boolean connectionState = false;

        String networkSSID = getSsid();
        String networkPass = getPassword();

        //Wifi Manager
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        //Wifi Configuration
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";

        if(!getPassword().equals("")){
            conf.preSharedKey = "\""+ networkPass +"\"";
        }else{
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        conf.hiddenSSID = false;

        //Add wifi configuration to device
        //Adds to the list of network and returns the network id which can be used to enable it later.
        wifiId = wifiManager.addNetwork(conf);

        if (wifiId != -1) {
            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);

                    boolean res = wifiManager.reconnect();
                    boolean changeHappen = wifiManager.saveConfiguration();

                    if (res && changeHappen) {
                        //true
                        connectionState = true;
                        Timber.d("Success Connect");
                    } else {
                        Timber.e("Failed To Connect");
                    }
                    break;
                } else {
                    Timber.e("SSID null or Not Equal : %s",i.SSID);
                }
            }
        } else {
            Timber.e("SSID %s -1",networkSSID);
        }
        Timber.e("STATE %s ",connectionState);
        return connectionState;
    }

    public void disconnect(){
        ConnectionUtil.disconnect(activity);
    }

    public boolean isConnected() {
        boolean state = false;
        String ssidConnect = "";
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        ssidConnect = "\"" + getSsid() + "\"";
        try {
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.getExtraInfo().equals(ssidConnect)) {
                state = true;
            } else {
                state = false;
            }
            Timber.d("%s - %s: %s",ssidConnect,getPassword(),state);
        } catch (NullPointerException e) {
            Timber.e("%s : %s",e.toString());
            state = false;
        }
        return state;
    }
}
