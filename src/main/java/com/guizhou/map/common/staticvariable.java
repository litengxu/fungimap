package com.guizhou.map.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: fungimap
 * @ClassName: staticvariable
 * @Author: 李腾旭
 * @Date: 2020-07-22 10:33
 * @Description: ${todo}
 * @Version: V1.0
 */

public class staticvariable {
    public static final Map<String,String> citymap;
    public  static  final  List<String> list30;
    static {
        citymap = new HashMap<String,String>();
        citymap.put("贵阳地区","贵阳市");
        citymap.put("六盘水地区","六盘水市");
        citymap.put("遵义市","遵义市");
        citymap.put("安顺地区","安顺市");
        citymap.put("毕节地区","毕节市");
        citymap.put("铜仁地区","铜仁市");
        citymap.put("黔西南布依族苗族自治州","黔西南布依族苗族自治州");
        citymap.put("黔东南苗族侗族自治州","黔东南苗族侗族自治州");
        citymap.put("黔南布依族苗族自治州","黔南布依族苗族自治州");

        list30 = new ArrayList<>();
        list30.add("紫花菌");
        list30.add("大红菇");
        list30.add("白牛肝");
        list30.add("棕灰口蘑");
        list30.add("马勃");
        list30.add("头状秃马勃头状马勃");
        list30.add("鸡枞");
        list30.add("红汁乳菇");
        list30.add("松乳菇");
        list30.add("新粉孢牛肝");
        list30.add("锥鳞白鹅膏");
        list30.add("巴卢圆孔牛肝菌");
        list30.add("疣柄牛肝菌");
        list30.add("糙皮侧耳");
        list30.add("乳牛肝菌");
        list30.add("黄乳牛肝菌");
        list30.add("蓝黄红菇");
        list30.add("青头菌");
        list30.add("多汁乳菇");
        list30.add("灵芝");
        list30.add("紫灵芝");
        list30.add("新古尼虫草");
        list30.add("美味齿菌");
        list30.add("香菇");
        list30.add("密环菌");
        list30.add("中华鹅膏");
        list30.add("冠锁瑚菌");
        list30.add("亚砖红沿丝伞");
        list30.add("木耳");
        list30.add("鸡油菌");



    }
}
