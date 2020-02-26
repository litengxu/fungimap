package com.guizhou.map.controller;

import com.alibaba.fastjson.JSON;
import com.guizhou.map.domain.coordinatetoimgid;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.idtoimg;
import com.guizhou.map.domain.predict;
import com.guizhou.map.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
public class searchcontroller_new {

    @Autowired
    private FungusService fungusService;
    @Autowired
    private PredictService predictService;
    @Autowired
    private IdtoimgService IdtoimgService;
    @Autowired
    private CoordinatetoimgidService coordinatetoimgidService;

    public static int only = 0;//是否为单个种类（0为否）
    private int size = 40;//分块图为40*40
    List<fungus> listzhu = new ArrayList<>();
    List<predict> listzhu2 = new ArrayList<>();
    public static String cityCountry;
    public static int zoom = 0;//控制地图的初始大小（市的缩小等级更高，因为市面积大于县）
    private String City;//市
    private String Country;//县
    private String Datakind;//所选搜索种类
    private String Funginame;//菌种的属

    @RequestMapping("/search/{city}/{country}/{datakind}/{fungil}/{style}")
    public Object search(@PathVariable(value = "city") String city, @PathVariable(value = "country") String country, @PathVariable(value = "datakind") String datakind, @PathVariable(value = "fungil") String funginame, @PathVariable(value = "style") String style) {
        ModelAndView modelAndView = new ModelAndView();
        listzhu = null;
        listzhu2 = null;
        City = city;
        Country = country;
        Datakind = datakind;
        Funginame = funginame;
        only = 0;
        List<fungus> list = new ArrayList<>();
        List<predict> list2 = new ArrayList<>();
        if (datakind.equals("2")) {//实际数据
            listzhu = fungusService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else if (datakind.equals("1")) {//混合图
            listzhu = fungusService.selectdata(city, country, funginame);
            listzhu2 = predictService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else { //预测
            listzhu2 = predictService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        }
        if (style.equals("1")) {//散点图
            modelAndView.addObject("citycountry", cityCountry);
            modelAndView.addObject("zoom", zoom);
            modelAndView.setViewName("map/map_effectScatter_search");
            return modelAndView;
        } else {//分块图
            // modelAndView.addObject("list",list.toString());
            double[][] arr2 = null;
            if (listzhu != null && listzhu2 != null) {//根据要显示的数据类型进行筛选
                arr2 = predictService.listconverttoarr_p(listzhu2, listzhu);
            } else if (listzhu != null) {
                arr2 = fungusService.listconverttoarr(listzhu);
            } else if (listzhu2 != null) {
                arr2 = predictService.listconverttoarr_p(listzhu2);
            } else {
            }
            //初始化要赋值的数组
            StringBuffer sb = fungusService.fillarr(arr2);
            modelAndView.addObject("only", only);
            modelAndView.addObject("citycountry", cityCountry);
            modelAndView.addObject("zoom", zoom);
            modelAndView.addObject("arr", sb.toString());
            modelAndView.setViewName("map/map_kuai_search");
            return modelAndView;
        }
    }

    //柱状图ajax接口
    @RequestMapping(value = "/map-one-zhu-sea", method = RequestMethod.POST)
    @ResponseBody
    public Object getonezhusea(@RequestParam("column") int column, @RequestParam("row") int row) {
        List<fungus> list = new ArrayList<>();
        List<predict> list2 = new ArrayList<>();
        list = fungusService.getAllFungi();
        list2 = predictService.getAllFungi();
        if (Country.equals("all")) {//找市内所有地区
            list = fungusService.findbycity(list, City);
            list2 = predictService.findbycity_p(list2, City);
        } else {//找单个县
            list = fungusService.findbycountry(list, Country);
            list2 = predictService.findbycountry_p(list2, Country);
        }
        //根据蘑菇种类筛选
        if (!Funginame.equals("allkind")) {

            list = fungusService.findbyfungusname(list, Funginame);
            list2 = predictService.findbyfungusname_p(list2, Funginame);
        }

        if (Datakind.equals("2")) {
            list = fungusService.findinthisfungi(list, column, row);
            return list;
        } else if (Datakind.equals("3")) {
            list2 = predictService.findinthisfungi_p(list2, column, row);
            return list2;
        } else {
            return predictService.findinthisfungi_fp(list, list2, column, row);

        }
    }

    //散点图searchajax接口
    @RequestMapping(value = "/map-scatter-sea")
    @ResponseBody
    public Object getscattersea() {

        if (listzhu2 == null) {
            return listzhu;
        } else if (listzhu == null) {
            Map<Object, Object> twolist = new HashMap<>();
            twolist.put("list1", "预测");
            twolist.put("list2", listzhu2);
            String twolist2 = JSON.toJSONString(twolist);
            return twolist2;
        } else {
            Map<Object, Object> twolist = new HashMap<>();
            twolist.put("list1", listzhu);
            twolist.put("list2", listzhu2);
            String twolist2 = JSON.toJSONString(twolist);
            return twolist2;
        }
    }

    //下拉框的ajax接口
    @RequestMapping("/selectkind/{selectvalue}")
    @ResponseBody
    public Object selectkind(@PathVariable(value = "selectvalue") String selectvalue) {


        if (selectvalue.equals("2")) {//查询实际数据
            List<fungus> list = new ArrayList<>();
            list = fungusService.getAllFungi();
            list = fungusService.removeDuplicateCase(list);
            return list;
        } else if (selectvalue.equals("1")) {//查询混合数据

            List<fungus> list = new ArrayList<>();
            list = fungusService.getAllFungi();
            list = fungusService.removeDuplicateCase(list);
            return list;
        } else {//c查询预测数据
            List<predict> list = new ArrayList<>();
            list = predictService.getAllFungi();
            list = predictService.removeDuplicateCase_p(list);
            return list;
        }

    }

    @RequestMapping(value = "/getphotos")
    @ResponseBody
    public Object getphotos(@RequestParam("longitude") String longitude1, @RequestParam("latitude") String latitude1, @RequestParam("name") String name) {
        String longitude = longitude1;
        String latitude = latitude1;
        List<coordinatetoimgid> list1 = coordinatetoimgidService.getall();
        String Objectid = coordinatetoimgidService.findObjectidbycoor(list1, longitude, latitude);
        if (Objectid == null) {//说明显示的图片是预测数据，找到对应的属，再去找这个属的图片
            List<predict> listp = predictService.getAllFungi();
            for (int i = 0; i < listp.size(); i++) {
                if (listp.get(i).getLongitude().equals(longitude) && listp.get(i).getLatitude().equals(latitude)) {
                    for (int j = 0; j < list1.size(); j++) {
                        if (list1.get(j).getGenus1().equals(listp.get(i).getGenus1())) {
                            Objectid = list1.get(j).getId();
                            break;
                        }
                    }
                    break;
                }
            }
        }
        List<idtoimg> list2 = IdtoimgService.getall();
        List<idtoimg> list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
        return IdtoimgService.createImgJson(list3,name);
    }

}

