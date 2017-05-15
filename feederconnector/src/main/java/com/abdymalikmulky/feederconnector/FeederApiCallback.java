package com.abdymalikmulky.feederconnector;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/20/17.
 */

public interface FeederApiCallback {
    void onSuccess(String result);

    void onFailed(String e);
}
