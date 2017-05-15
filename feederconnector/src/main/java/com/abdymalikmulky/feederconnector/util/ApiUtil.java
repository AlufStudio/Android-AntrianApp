package com.abdymalikmulky.feederconnector.util;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/20/17.
 */

public class ApiUtil {

    //BASE URL
    public static String PROTOCOL = "http://";
    public static String IP = PROTOCOL + "192.168.4.1";
    public static String PORT = "2516";
    public static String BASE_URL = IP + ":" + PORT;

    //ENDPOINT
    public static String ENDPOINT_TIME = "/time";
    public static String ENDPOINT_WIFI_STATE = "/wifi";
    public static String ENDPOINT_INFO = "/info";
    public static String ENDPOINT_SETTING = "/sett";
    public static String ENDPOINT_SCHEDULE = "/schedule";
    public static String ENDPOINT_RUN = "/run";
    public static String ENDPOINT_UPDATE = "/update";
    public static String ENDPOINT_DOWNLOAD = "/download";

    //PARAM SETTING
    public static String PARAM_STATUS = "status";
    public static String PARAM_FEEDING_PROGRAM = "program";
    public static String PARAM_FISH_TYPE = "jenis";
    public static String PARAM_FISH_WEIGHT = "beratIkan";
    public static String PARAM_FISH_COUNT = "jumlah";
    public static String PARAM_FREQUENCY = "frekuensi";
    public static String PARAM_FEED_TYPE = "tipePakan";
    public static String PARAM_FEED_WEIGHT = "beratPakan";

    //PARAM SETTING
    public static String PARAM_TIMESTAMP = "timestamp";

    //VALUE SETTING
    public static String VALUE_FEEDING_PROGRAM = "1";
    public static String VALUE_FEEDING_MANUAL = "0";
}
