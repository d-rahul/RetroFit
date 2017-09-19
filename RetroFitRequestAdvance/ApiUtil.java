package com.test.webservice;

public class ApiUtil {

    public class Config {
        public static final String STAGING_URL = "http://readyandroidlocal/WS/";
        public static final String LIVE_URL = "http://readyandroid/WS/";
        public static final String ENDPOINT_URL = STAGING_URL;
    }

    public class Api {
        public static final String USER_LOGIN = "user_login";
    }

    public class Request {
        public static final int USER_LOGIN = 101;
    }

    public class InputParams {
        public static final String COUNTRYCODE = "countrycode";//(Ex. India = IN)
        public static final String USERID = "userid";
    }

}
