package com.moa.rxdemo.mvp.bean;

import java.util.List;

public class ForecastBean {

    public String message;

    public int status;

    public String date;

    public String time;

    public CityInfo cityInfo;

    public Data data;

    public static class CityInfo {
        public String city;

        public String citykey;

        public String parent;

        public String updateTime;
    }

    public static class Yesterday {
        public String date;

        public String high;

        public String low;

        public String ymd;

        public String week;

        public String sunrise;

        public String sunset;

        public int aqi;

        public String fx;

        public String fl;

        public String type;

        public String notice;
    }

    public static class Forecast {
        public String date;

        public String high;

        public String low;

        public String ymd;

        public String week;

        public String sunrise;

        public String sunset;

        public int aqi;

        public String fx;

        public String fl;

        public String type;

        public String notice;
    }

    public static class Data {
        public String shidu;

        public double pm25;

        public double pm10;

        public String quality;

        public String wendu;

        public String ganmao;

        public List<Forecast> forecast;

        public Yesterday yesterday;
    }

}

