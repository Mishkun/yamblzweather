package com.kondenko.yamblzweather;

public abstract class Const {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public static final String API_KEY = "55b2afa5241f9e7efe29e0c11fd124be";

    public static final String PARAM_API_KEY = "APPID";

    public static final String KEY_UNIT_TEMP_DEFAULT = "default";
    public static final String VALUE_UNIT_TEMP_IMPERIAL = "F";
    public static final String VALUE_UNIT_TEMP_METRIC = "C";
    public static final String VALUE_UNIT_TEMP_DEFAULT = "K";

    public static final String PREF_REFRESH_RATE_DEFAULT_HOURS = "2";

    public static final String ID_MOSCOW = "524901"; // STOPSHIP Task #2 only

    public static int DEFAULT_CACHING_TIME_SECONDS = 60 * 10;

}
