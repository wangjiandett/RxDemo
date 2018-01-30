package com.moa.rxdemo.mvp.bean;

import java.util.List;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/20 16:08
 */
public class Weather {
    
    public YesterdayBean yesterday;
    public String city;
    public String aqi;
    public String ganmao;
    public String wendu;
    public List<ForecastBean> forecast;
    
    public static class YesterdayBean {
        
        public String date;
        public String high;
        public String fx;
        public String low;
        public String fl;
        public String type;
        
    }
    
    public static class ForecastBean {
        
        public String date;
        public String high;
        public String fengli;
        public String low;
        public String fengxiang;
        public String type;
        
        
    }
}
