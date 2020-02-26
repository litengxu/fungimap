package com.guizhou.map.controller;


import com.alibaba.fastjson.JSON;
import com.guizhou.map.domain.coordinatetoimgid;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.idtoimg;
import com.guizhou.map.service.CoordinatetoimgidService;
import com.guizhou.map.service.FungusService;
import com.guizhou.map.service.IdtoimgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

//后台跳转逻辑代码
@Controller
public class admincontroller_new {

    @Autowired
    private FungusService fungusService;
    @Autowired
    private IdtoimgService IdtoimgService;
    @Autowired
    private CoordinatetoimgidService coordinatetoimgidService;

    //主页跳转
    @RequestMapping("/home")
    public ModelAndView getadmin() {
        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.removeDuplicateCase(list);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("funginamelist", list);
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    //根据name来跳转并获得相应的数据来渲染
    @RequestMapping("/map-zhu/{name}")
    public ModelAndView getmap(@PathVariable("name") String name) {

        List<fungus> list = fungusService.selectByname(name);
        double[][] arr2 = fungusService.listconverttoarr(list);
        //初始化要赋值的数组
        StringBuffer sb = fungusService.fillarr(arr2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("arr", sb.toString());
        modelAndView.addObject("funginame", name);
        modelAndView.setViewName("map/map_zhu");
        return modelAndView;
    }

    //单种类散点图
    @RequestMapping("/map-effectScatter/{name}")
    public ModelAndView geteffectScatter(@PathVariable("name") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", name);
        modelAndView.setViewName("map/map_effectScatter");
        return modelAndView;
    }

    //单种类散点图ajax接口
    @RequestMapping("/oneScatter/{name}")
    @ResponseBody
    public Object getoneScaatterimpl(@PathVariable("name") String name) {
        List<fungus> list = fungusService.selectByname(name);
        return list;
    }

    //所有种类分块图
    @RequestMapping("/allFungi-zhu")
    public ModelAndView getallFungi() {

        List<fungus> list = fungusService.getAllFungi();
        double[][] arr2 = fungusService.listconverttoarr(list);
//        int [][]arr = new int[size*size][3];
//        int k=0;
//        for(int i = 0;i<arr.length;i++) {
//            arr[i][0] = i%size;
//            arr[i][1] = k;
//            if((i+1)%size==0){
//                k++;
//            }
//            arr[i][2] = 0;
//        }
//        //把十万条数据放到arr中，相同方格的数目要相加
//        //float y = (float) ((109.672039 - 103.299969)/100);
//        float y = (float) ((109.672039 - 103.299969)/size);
//
//        //真正使用时要用上面这句
//       // float x = (float) ((29.443776 - 24.191224)/100);
//        float x = (float) ((29.443776 - 24.191224)/size);
//        for(int i=0;i<arr2.length;i++) {
//            //列方向第g1块行 对应row
//            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
//            int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
//            //double g1 = (arr2[i][0] - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
//
//            if(g1<0 || g2<0){
//                continue;
//            }
//            //double g2 = (arr2[i][1] - 103.622947)/y;
////            int c = (int) (g1*100+g2);
//            int c = (int) (g1*size+g2);
//            arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
//
//        }
//        StringBuffer sb  = new StringBuffer();
//
//        for(int i = 0;i<arr.length;i++){
//            sb.append(arr[i][0]);
//            sb.append(",");
//            sb.append(arr[i][1]);
//            sb.append(",");
//            sb.append(arr[i][2]);
//            if(i!=arr.length-1){
//                sb.append(";");
//            }
//        }
        StringBuffer sb = fungusService.fillarr(arr2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("arr", sb.toString());
        modelAndView.setViewName("map/map_allFungi");
        return modelAndView;

    }

    //柱状图ajax接口所有
    @RequestMapping(value = "/map-one-zhu", method = RequestMethod.GET)
    @ResponseBody
    public Object getonezhu(@RequestParam("column") int column, @RequestParam("row") int row) {

        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.findinthisfungi(list, column, row);
        return list;
    }

    //柱状图ajax接口
    @RequestMapping(value = "/map-one-zhu_onefungi", method = RequestMethod.GET)
    @ResponseBody
    public Object getonezhuone(@RequestParam("column") int column, @RequestParam("row") int row, @RequestParam("funginame") String funginame) {
        List<fungus> list = fungusService.selectByname(funginame);
        list = fungusService.findinthisfungi(list, column, row);
        return list;
    }

    //所有种类散点图
    @RequestMapping(value = "/toallScatter")
    public String toallScatter() {
        return "map/map_alleffectScatter";
    }

    //散点图ajax接口
    @RequestMapping(value = "/allScatter")
    @ResponseBody
    public Object allScatter() {
        List<fungus> list = fungusService.getAllFungi();
        Map<Object, Object> twolist = new HashMap<>();
        twolist.put("list1", list);
        String twolist2 = JSON.toJSONString(twolist);
        return twolist2;
    }

    @RequestMapping(value = "/getphotos_all")
    @ResponseBody
    public Object getphotos(@RequestParam("longitude") String longitude1, @RequestParam("latitude") String latitude1, @RequestParam("name") String name) {
        String longitude = longitude1;
        String latitude = latitude1;
        List<coordinatetoimgid> list1 = coordinatetoimgidService.getall();
        String Objectid = coordinatetoimgidService.findObjectidbycoor(list1, longitude, latitude);
        List<idtoimg> list2 = IdtoimgService.getall();
        List<idtoimg> list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (int i = 0; i < list3.size(); i++) {
            sb.append("{\"alt\":\"" + name + "\",\"pid\":" + count + ",\"src\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\",\"thumb\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\"}");
            count++;
            if (i != list3.size() - 1) {
                sb.append(",");
            }
        }
        String str = "{\"title\":\"" + name + "\",\"id\":1,\"start\":0,\"data\":[" + sb + "]}";
        return str;

    }


    //用于随机添加图片
    @RequestMapping(value = "/randomimg")
    @ResponseBody
    public String insertimg() {
        List<fungus> list = fungusService.getAllFungi();
        list.forEach(data -> {
            System.out.println(data.getId());
            fungusService.updateByid(data.getId());

        });
        return "ok";
    }
}
