package com.abdymalikmulky.settingqueue.util;

import java.util.Random;

/**
 * Created by efishery on 04/05/17.
 */

public class AppUtils {

    //STATE
    public final static String STATE_SYNCED = "SYNCED";
    public final static String STATE_NOT_SYNCED = "NOTSYNCED";

    //FEEDER DUMMY
    public final static String DUMMY_SSID = "efishery_0003C";
    public final static String DUMMY_TOKEN = "4-SB7wWr77Xv77";


    public static String getSaltString() {
        String SALTCHARS = "efisheryEFISHERY2516";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
