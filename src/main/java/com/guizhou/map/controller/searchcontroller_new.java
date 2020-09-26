package com.guizhou.map.controller;

import com.alibaba.fastjson.JSON;
//import com.guizhou.map.domain.coordinatetoimgid;
import com.guizhou.map.common.domain.baseconfig;
import com.guizhou.map.common.staticvariable;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.idtoimg;
import com.guizhou.map.domain.predict;
import com.guizhou.map.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.guizhou.map.common.commonfun.deepCopy;

@RestController
public class searchcontroller_new {

    @Autowired
    private FungusService fungusService;
    @Autowired
    private PredictService predictService;
    @Autowired
    private IdtoimgService IdtoimgService;
//    @Autowired
//    private CoordinatetoimgidService coordinatetoimgidService;

//    public static int only = 0;//是否为单个种类（0为否）
//    private int size = baseconfig.getKuaisize();//分块图为40*40
    private int size = 40;




    @RequestMapping("/search/{city}/{country}/{datakind}/{fungil}/{style}")
    public Object search(@PathVariable(value = "city") String city,
                         @PathVariable(value = "country") String country,
                         @PathVariable(value = "datakind") String datakind,
                         @PathVariable(value = "fungil") String funginame,
                         @PathVariable(value = "style") String style) {
        ModelAndView modelAndView = new ModelAndView();
        String cityCountry = null;
        int zoom = 0;//控制地图的初始大小（市的缩小等级更高，因为市面积大于县）

        if(country.equals("all")){//找市内所有地区
            cityCountry = staticvariable.citymap.get(city);
            zoom = 9;
        }else{//找单个县
            cityCountry = country;
            zoom  = 11;
        }
        if (style.equals("1")) {//散点图
            modelAndView.addObject("city",city);
            modelAndView.addObject("country",country);
            modelAndView.addObject("datakind",datakind);
            modelAndView.addObject("funginame",funginame);
            modelAndView.addObject("citycountry", cityCountry);
            modelAndView.addObject("zoom", zoom);
            modelAndView.setViewName("map/map_effectScatter_search");
            return modelAndView;
        } else {//分块图
            // modelAndView.addObject("list",list.toString());
            //不使用ajax时
//            double[][] arr2 = null;
//            if (listzhu != null && listzhu2 != null) {//根据要显示的数据类型进行筛选
//                arr2 = predictService.listconverttoarr_p(listzhu2, listzhu);
//            } else if (listzhu != null) {
//                arr2 = fungusService.listconverttoarr(listzhu);
//            } else if (listzhu2 != null) {
//                arr2 = predictService.listconverttoarr_p(listzhu2);
//            } else {
//            }
            //初始化要赋值的数组
//            StringBuffer sb = fungusService.fillarr(arr2);
//            modelAndView.addObject("only", only);
            modelAndView.addObject("city",city);
            modelAndView.addObject("country",country);
            modelAndView.addObject("datakind",datakind);
            modelAndView.addObject("funginame",funginame);
            modelAndView.addObject("citycountry", cityCountry);
            modelAndView.addObject("zoom", zoom);
//            modelAndView.addObject("arr", sb.toString());
            modelAndView.setViewName("map/map_kuai_search");
            return modelAndView;
        }
    }
    //分块图ajax接口
    @RequestMapping(value = "/map-kuai-ajax-sea" ,method = RequestMethod.POST)
    @ResponseBody
    public StringBuffer getkuaiajax(@RequestParam("city") String city,
                                    @RequestParam("country") String country,
                                    @RequestParam("funginame") String funginame,
                                    @RequestParam("datakind") String  datakind){

        double[][] arr2 = null;

//        City = city;
//        Country = country;
//        Datakind = datakind;
//        Funginame = funginame;
//        only = 0;
        List<fungus> list = new ArrayList<>();
        List<predict> list2 = new ArrayList<>();
        if (datakind.equals("2")) {//实际数据
            list = fungusService.selectdata(city, country, funginame);
            arr2 = fungusService.listconverttoarr(list);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else if (datakind.equals("1")) {//混合图
            list = fungusService.selectdata(city, country, funginame);
            list2 = predictService.selectdata(city, country, funginame);
            arr2 = predictService.listconverttoarr_p(list2, list);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else { //预测
            list2 = predictService.selectdata(city, country, funginame);
            arr2 = predictService.listconverttoarr_p(list2);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        }
//        if (listzhu != null && listzhu2 != null) {//根据要显示的数据类型进行筛选
//            arr2 = predictService.listconverttoarr_p(listzhu2, listzhu);
//        } else if (listzhu != null) {
//            arr2 = fungusService.listconverttoarr(listzhu);
//        } else if (listzhu2 != null) {
//            arr2 = predictService.listconverttoarr_p(listzhu2);
//        } else {
//        }
        StringBuffer sb = fungusService.fillarr(arr2);
        return sb;
    }

    //柱状图ajax接口
    @RequestMapping(value = "/map-one-zhu-sea", method = RequestMethod.POST)
    @ResponseBody
    public Object getonezhusea(@RequestParam("column") int column,
                               @RequestParam("row") int row,
                               @RequestParam("city") String city,
                               @RequestParam("country") String country,
                               @RequestParam("funginame") String funginame,
                               @RequestParam("datakind") String  datakind) {
        List<fungus> list = new ArrayList<>();
        List<predict> list2 = new ArrayList<>();
////        list = fungusService.getAllFungi();
////        list2 = predictService.getAllFungi();
//        if (Country.equals("all")) {//找市内所有地区，缩小list范围
////            list = fungusService.findbycity(list, City);
//            list = fungusService.findbycity(City);
////            list2 = predictService.findbycity_p(list2, City);
//            list2 = predictService.findbycity_p(City);
//        } else {//找单个县
////            list = fungusService.findbycountry(list, Country);
//            list = fungusService.findbycountry(Country);
////            list2 = predictService.findbycountry_p(list2, Country);
//            list2 = predictService.findbycountry_p(Country);
//        }
        //根据蘑菇种类筛选
        Map<Object,Object> twolist = new HashMap<>();
        if (datakind.equals("2")) {//实际数据
            list = fungusService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else if (datakind.equals("1")) {//混合图
            list = fungusService.selectdata(city, country, funginame);
            list2 = predictService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else { //预测
            list2 = predictService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        }

        if (!funginame.equals("allkind")) {//使柱状图只显示所选择的种类
            if (funginame.equals("30")) {
                if(list!= null) {
                    list = fungusService.find30Fungi(list);
                }
                if(list2!=null){
                    list2 = predictService.find30Fungi(list2);
                }
            } else {
                if (list != null) {
                    list = fungusService.findbyfungusname(list, funginame);
                }
                if (list2 != null) {
                    list2 = predictService.findbyfungusname_p(list2, funginame);
                }
            }
        }
        int w = 0;

        if (datakind.equals("2")) {//只选择选择的数据源
            twolist.put("list1",null);
            twolist.put("list2",null);

            list = fungusService.findinthisfungi(list, column, row);
            //找到共多少种
//            List<fungus> listnew = deepCopy(list);
//            x1 = fungusService.removeDuplicateCasemNonull(listnew);
            w = list.size();

            twolist.put("list1",list);
            twolist.put("list2","实际");

        } else if (datakind.equals("3")) {
            twolist.put("list1",null);
            twolist.put("list2",null);
            list2 = predictService.findinthisfungi_p(list2, column, row);
//            List<predict> listnewp = deepCopy(list2);
//            x2 = predictService.removeDuplicateCase_p(list2).size();
            w = list2.size();
            twolist.put("list1",list2);
            twolist.put("list2","预测");

        } else {
//            twolist = null;
            twolist = predictService.findinthisfungi_fp(list, list2, column, row);
            w = (int)twolist.get("w");

        }


        return twolist;
    }
    //柱状图宽度+数据
    @RequestMapping(value = "/map-one-zhu-sea-wid", method = RequestMethod.POST)
    @ResponseBody
    public Object getonezhuseawid(@RequestParam("column") int column,
                               @RequestParam("row") int row,
                               @RequestParam("city") String city,
                               @RequestParam("country") String country,
                               @RequestParam("funginame") String funginame,
                               @RequestParam("datakind") String  datakind) throws Exception{
        List<fungus> list = new ArrayList<>();
        List<predict> list2 = new ArrayList<>();
////        list = fungusService.getAllFungi();
////        list2 = predictService.getAllFungi();
//        if (Country.equals("all")) {//找市内所有地区，缩小list范围
////            list = fungusService.findbycity(list, City);
//            list = fungusService.findbycity(City);
////            list2 = predictService.findbycity_p(list2, City);
//            list2 = predictService.findbycity_p(City);
//        } else {//找单个县
////            list = fungusService.findbycountry(list, Country);
//            list = fungusService.findbycountry(Country);
////            list2 = predictService.findbycountry_p(list2, Country);
//            list2 = predictService.findbycountry_p(Country);
//        }
        //根据蘑菇种类筛选
        Map<Object,Object> twolist = new HashMap<>();
        if (datakind.equals("2")) {//实际数据
            list = fungusService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else if (datakind.equals("1")) {//混合图
            list = fungusService.selectdata(city, country, funginame);
            list2 = predictService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else { //预测
            list2 = predictService.selectdata(city, country, funginame);
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        }

        if (!funginame.equals("allkind")) {//使柱状图只显示所选择的种类
            if (funginame.equals("30")) {
                if(list!= null) {
                    list = fungusService.find30Fungi(list);
                }
                if(list2!=null){
                list2 = predictService.find30Fungi(list2);
                }
            } else {
                if (list != null) {
                    list = fungusService.findbyfungusname(list, funginame);
                }
                if (list2 != null) {
                    list2 = predictService.findbyfungusname_p(list2, funginame);
                }
            }
        }
        int w = 0;

        if (datakind.equals("2")) {//只选择选择的数据源
            twolist.put("list1",null);
            twolist.put("list2",null);

            list = fungusService.findinthisfungi(list, column, row);
            //找到共多少种
//            List<fungus> listnew = deepCopy(list);
//            x1 = fungusService.removeDuplicateCasemNonull(listnew);
            w = list.size();

            twolist.put("list1",list);
            twolist.put("list2","实际");
            twolist.put("w",w);

        } else if (datakind.equals("3")) {
            twolist.put("list1",null);
            twolist.put("list2",null);
            list2 = predictService.findinthisfungi_p(list2, column, row);
//            List<predict> listnewp = deepCopy(list2);
//            x2 = predictService.removeDuplicateCase_p(list2).size();
            w = list2.size();
            twolist.put("list1",list2);
            twolist.put("list2","预测");
            twolist.put("w",w);

        } else {
            twolist = predictService.findinthisfungi_fp(list, list2, column, row);
//            w = (int)twolist.get("w");

        }
//        System.out.println(w);
//        System.out.println(datakind);
////        System.out.println(only);
//        System.out.println(list.size());
        return twolist;
    }

    //散点图searchajax接口
    @RequestMapping(value = "/map-scatter-sea", method = RequestMethod.POST)
    @ResponseBody
    public Object getscattersea(@RequestParam("city") String city,
                                @RequestParam("country") String country,
                                @RequestParam("funginame") String funginame,
                                @RequestParam("datakind") String  datakind) {
        List<fungus> list = new ArrayList<>();
        List<predict> list2 = new ArrayList<>();
        Map<Object, Object> twolist = new HashMap<>();
        if (datakind.equals("2")) {//实际数据
            list = fungusService.selectdata(city, country, funginame);
            twolist = fungusService.listtoarrmap(list);
            twolist.put("list1","实际");
//            twolist.put("list2",list);
            return twolist;
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else if (datakind.equals("1")) {//混合图
            list = fungusService.selectdata(city, country, funginame);
            list2 = predictService.selectdata(city, country, funginame);
            Map<Object, Object> twolist_p = new HashMap<>();
            twolist = fungusService.listtoarrmap(list);
            twolist_p = predictService.listtoarrmap(list2);
//        maplist.put("namearr",name);
            twolist.put("namearr_p",twolist_p.get("namearr_p"));
            twolist.put("shuarr_p",twolist_p.get("shuarr_p"));
            twolist.put("longitudearr_p",twolist_p.get("longitudearr_p"));
            twolist.put("latitudearr_p",twolist_p.get("latitudearr_p"));
            twolist.put("valuearr_p",twolist_p.get("valuearr_p"));
//            twolist.put("list1", list);
//            twolist.put("list2", list2);

//            String twolist2 = JSON.toJSONString(twolist);
            return twolist;
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        } else { //预测
            list2 = predictService.selectdata(city, country, funginame);
            twolist = predictService.listtoarrmap(list2);
            twolist.put("list1", "预测");
//            twolist.put("list2", list2);
//            String twolist2 = JSON.toJSONString(twolist);
            return twolist;
            //还要再加上混合和预测数据
            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
        }
//        //listzhu2预测数据，listzhu实际数据
//        if (listzhu2 == null) {
//            return listzhu;
//        } else if (listzhu == null) {
//            Map<Object, Object> twolist = new HashMap<>();
//            twolist.put("list1", "预测");
//            twolist.put("list2", listzhu2);
//            String twolist2 = JSON.toJSONString(twolist);
//            return twolist2;
//        } else {
//            Map<Object, Object> twolist = new HashMap<>();
//            twolist.put("list1", listzhu);
//            twolist.put("list2", listzhu2);
//            String twolist2 = JSON.toJSONString(twolist);
//            return twolist2;
//        }
    }

    //下拉框的ajax接口
    @RequestMapping("/selectkind/{selectvalue}")
    @ResponseBody
    public Object selectkind(@PathVariable(value = "selectvalue") String selectvalue) {

        if (selectvalue.equals("2")) {//查询实际数据
            List<fungus> list = new ArrayList<>();
            list = fungusService.getAllFungi();
            list = fungusService.removeDuplicateCase(list);
            //去掉第一个空值,当为null时查询不到
            list.remove(0);
            return list;
        } else if (selectvalue.equals("1")) {//查询混合数据

            List<fungus> list = new ArrayList<>();
            list = fungusService.getAllFungi();
            list = fungusService.removeDuplicateCase(list);
            //去掉第一个空值,当为null时查询不到
            list.remove(0);
            return list;
        } else {//c查询预测数据
            List<predict> list = new ArrayList<>();
            list = predictService.getAllFungi();
            list = predictService.removeDuplicateCase_p(list);
            //去掉第一个空值,当为null时查询不到
            list.remove(0);
            return list;
        }

    }

    //散点图获取照片ajax接口
    @RequestMapping(value = "/getphotos")
    @ResponseBody
    public Object getphotos(@RequestParam("longitude") String longitude1, @RequestParam("latitude") String latitude1, @RequestParam("name") String name, @RequestParam("objectid") String objectid) {
        String longitude = longitude1;
        String latitude = latitude1;
        String Objectid = objectid;
        fungus fungus = new fungus();
//        List<coordinatetoimgid> list1 = coordinatetoimgidService.getall();
//        String Objectid = coordinatetoimgidService.findObjectidbycoor(list1, longitude, latitude);
        List<idtoimg> list3 = new ArrayList<>();
        List<idtoimg> list2 = IdtoimgService.getall();

        if (Objectid.equals("预测")) {//说明显示的图片是预测数据，找到对应的属，再去找这个属的图片
//            List<predict> listp = predictService.getAllFungi();
//            for (int i = 0; i < listp.size(); i++) {
//                if (listp.get(i).getLongitude().equals(longitude) && listp.get(i).getLatitude().equals(latitude)) {
//                    for (int j = 0; j < list1.size(); j++) {
//                        if (list1.get(j).getGenus1().equals(listp.get(i).getGenus1())) {
//                            Objectid = list1.get(j).getId();
//                            list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
//                            if(list3.size()>0){
//                                break;
//                            }
//
//
//                        }
//                    }
//                    break;
//                }
//            }
            List<fungus> listf = fungusService.getAllFungi();
            for (int i = 0; i <listf.size() ; i++) {
                //先查属
                if(listf.get(i).getShorttext1567761455594().equals(name)){

                    Objectid = listf.get(i).getId();

                    list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
                    if(list3.size()>0){

                        fungus = fungusService.findbyobjectid(Objectid);

                        break;
                    }

                }
                //属查不到时，查真实的菌种名称
                if(list3.size() == 0){
                    if(listf.get(i).getShorttext1567761353834().equals(name)){

                        Objectid = listf.get(i).getId();

                        list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
                        if(list3.size()>0){

                            fungus = fungusService.findbyobjectid(Objectid);;

                            break;
                        }
                    }
                }
            }


        }else{
            fungus = fungusService.findbyobjectid(objectid);

            list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
        }


        return IdtoimgService.createImgJson(list3,fungus,name);
    }

}

