package com.abdymalikmulky.feederconnector.util;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by abdymalikmulky on 26/04/2016.
 */
public class ConnectionUtil {
    /**
     * Melakukan force wifi enable terhadap device
     * @param context
     */
    public static void setEnable(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * Mengambil ip yang sedang digunakan oleh device
     * @param activity
     * @return IP used
     */
    public static String getIPAccess(Activity activity) {
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(activity.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return formatedIpAddress;
    }


    /**
     * Untuk memutus dan melupakan semua wifi yang telah initConnection
     * @param context
     */
    public static void disconnect(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.disconnect();
        forgetAllNetwork(context);
    }

    public static void forgetAllNetwork(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.saveConfiguration();
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        if (list != null) {
            if (list.size() > 0) {
                for (WifiConfiguration k : list) {
                    wifiManager.removeNetwork(k.networkId);
                    wifiManager.saveConfiguration();
                }
            }
        }
    }
}
