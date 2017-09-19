package com.test.wsutils;

public class WSUtils {
    public static final String TAG = WSUtils.class.getSimpleName();

    //SECONDS
    public static final long CONNECTION_TIMEOUT_FOR_IMAGE = 300;
    //SECONDS
    public static final long CONNECTION_TIMEOUT = 120;

    public static final String STAGING_URL = "http://192.168.34.181/joy_center3708/WS/";
    //public static final String STAGING_URL = "http://192.168.39.3/projects/joy_center/WS/";
    public static final String LIVE_URL = "http://108.170.62.152/webservices/joy_center_app_003990/WS/";
    public static final String BASE_URL = LIVE_URL/*BuildConfig.API_URL*/;

    public static final String PAGE_DIRECTOR_DESK = "http://www.letstalkacademy.com/directordesk.htm";
    public static final String PAGE_ABOUT_US = "http://www.letstalkacademy.com/LTA.htm";


    public static final int REQ_FEEDBACK_TYPE = 101;

    public static String KEY_SETTINGS = "settings";
    public static String KEY_MESSAGE = "message";
    public static String KEY_DATA = "data";
    public static String KEY_SUCCESS = "success";

}
