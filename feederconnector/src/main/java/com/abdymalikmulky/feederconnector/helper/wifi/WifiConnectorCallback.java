package com.abdymalikmulky.feederconnector.helper.wifi;

/**
 * Created by efishery on 28/11/16.
 */

public interface WifiConnectorCallback {
    void onConnected(String respon);

    void onFail(String error);
}
