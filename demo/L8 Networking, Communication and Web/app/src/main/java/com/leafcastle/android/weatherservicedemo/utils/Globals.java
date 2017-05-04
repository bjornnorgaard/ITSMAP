package com.leafcastle.android.weatherservicedemo.utils;

/**
 * Created by kasper on 01/05/16.
 */
public class Globals {
    public static final String CONNECT = "CONNECTIVITY";


    public static final String WEATHER_API_KEY = "ADD YOUR API KEY HERE!";

    public static final long CITY_ID_AARHUS = 2624652;

    public static final String WEATHER_API_CALL = "http://api.openweathermap.org/data/2.5/weather?id=" + CITY_ID_AARHUS + "&APPID=" + WEATHER_API_KEY;
    //private static final String WEATHER_API_CALL = "http://api.openweathermap.org/data/2.5/weather?q=aarhus,dk&APPID=" + WEATHER_API_KEY;
}
