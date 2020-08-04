package com.guizhou.map.common.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Program: fungimap
 * @ClassName: baseconfig
 * @Author: 李腾旭
 * @Date: 2020-07-26 16:19
 * @Description: 获取配置类中的公共参数
 * @Version: V1.0
 */
@Component
@ConfigurationProperties(prefix = "map")
public class baseconfig {
    private static String imgurl;
    private static int kuaisize;
    private static String  longitudemax;
    private static String longitudemin;
    private static String latitudemax;
    private static String latitudemin;

    @Override
    public String toString() {
        return "baseconfig{}";
    }

    public static String getImgurl() {
        return imgurl;
    }

    public  void setImgurl(String imgurl) {
        baseconfig.imgurl = imgurl;
    }

    public static int getKuaisize() {
        return kuaisize;
    }

    public  void setKuaisize(int kuaisize) {
        baseconfig.kuaisize = kuaisize;
    }

    public static String getLongitudemax() {
        return longitudemax;
    }

    public  void setLongitudemax(String longitudemax) {
        baseconfig.longitudemax = longitudemax;
    }

    public static String getLongitudemin() {
        return longitudemin;
    }

    public  void setLongitudemin(String longitudemin) {
        baseconfig.longitudemin = longitudemin;
    }

    public static String getLatitudemax() {
        return latitudemax;
    }

    public  void setLatitudemax(String latitudemax) {
        baseconfig.latitudemax = latitudemax;
    }

    public static String getLatitudemin() {
        return latitudemin;
    }

    public  void setLatitudemin(String latitudemin) {
        baseconfig.latitudemin = latitudemin;
    }
}
