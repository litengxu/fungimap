package com.guizhou.map.controller;


import com.alibaba.fastjson.JSON;
//import com.guizhou.map.domain.coordinatetoimgid;
import com.guizhou.map.common.commonfun;
import com.guizhou.map.common.domain.baseconfig;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.idtoimg;
//import com.guizhou.map.service.CoordinatetoimgidService;
import com.guizhou.map.domain.predict;
import com.guizhou.map.service.FungusService;
import com.guizhou.map.service.IdtoimgService;
import com.guizhou.map.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.guizhou.map.common.commonfun.deepCopy;

//后台跳转逻辑代码
@Controller
public class admincontroller_new {

    @Autowired
    private FungusService fungusService;
    @Autowired
    private IdtoimgService IdtoimgService;
    @Autowired
    private PredictService predictService;

     //柱状图传到前端的list
//    List<fungus> listz = new ArrayList<>();
//    @Autowired
//    private CoordinatetoimgidService coordinatetoimgidService;

    //主页跳转
//    private String imgurl = baseconfig.getImgurl();
    private String imgurl = "http://gzbdi-yunshu.oss-cn-beijing.aliyuncs.com/";

//    private String imgurl = null;
    @RequestMapping(value={"/home","/"})
    public ModelAndView getadmin() {
        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.removeDuplicateCase(list);
        //去掉第一个空值,当为null时查询不到

        list.remove(0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("funginamelist", list);
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    //根据name来跳转并获得相应的数据来渲染 单种类柱状图(不使用ajax可以用这种方法直接传串渲染)
//    @RequestMapping("/map-zhu/{name}")
//    public ModelAndView getmap(@PathVariable("name") String name) {
//
//        List<fungus> list = fungusService.selectByname(name);
//        double[][] arr2 = fungusService.listconverttoarr(list);
////        //初始化要赋值的数组
//        StringBuffer sb = fungusService.fillarr(arr2);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("arr", sb.toString());
//        modelAndView.addObject("funginame", name);
//        modelAndView.setViewName("map/map_zhu");
//        return modelAndView;
//    }
    //根据name来跳转并获得相应的数据来渲染 单种类柱状图
    @RequestMapping("/map-zhu/{name}")
    public ModelAndView getmap(@PathVariable("name") String name) {

//        List<fungus> list = fungusService.selectByname(name);
//        double[][] arr2 = fungusService.listconverttoarr(list);
//        //初始化要赋值的数组
//        StringBuffer sb = fungusService.fillarr(arr2);
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("arr", sb.toString());
        modelAndView.addObject("funginame", name);
        modelAndView.setViewName("map/map_zhu");
        return modelAndView;
    }
    //分块图ajax接口，直接传的话不需要这个，但是没有加载页面
    @RequestMapping("/map-zhu-ajax/{name}")
    @ResponseBody
    public StringBuffer getmap2(@PathVariable("name") String name) {

        List<fungus> list = fungusService.selectByname(name);
        double[][] arr2 = fungusService.listconverttoarr(list);
        //初始化要赋值的数组
        StringBuffer sb = fungusService.fillarr(arr2);

        return sb;
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

        List<fungus> list = fungusService.selectBySpecificname(name);

        //由于预测数据中不知道30种数据的属，所以这里直接只查专有的名字
//        List<predict> list2 = predictService.selectByname(name);
        List<predict> list2 = predictService.selectByspecialname(name);

//        List<predict> list2 = predictService.getAllFungi();
        Map<Object, Object> twolist_p = new HashMap<>();
        Map<Object, Object> twolist = new HashMap<>();
        twolist = fungusService.listtoarrmap(list);
        twolist_p = predictService.listtoarrmap(list2);
        twolist.put("namearr_p",twolist_p.get("namearr_p"));
        twolist.put("shuarr_p",twolist_p.get("shuarr_p"));
        twolist.put("longitudearr_p",twolist_p.get("longitudearr_p"));
        twolist.put("latitudearr_p",twolist_p.get("latitudearr_p"));
        twolist.put("valuearr_p",twolist_p.get("valuearr_p"));
//        twolist.put("list1", list);
//        String twolist2 = JSON.toJSONString(twolist);
        return twolist;
    }

    //单种类柱状图ajax接口
    @RequestMapping(value = "/map-one-zhu_onefungi", method = RequestMethod.GET)
    @ResponseBody
    public Object getonezhuone(@RequestParam("column") int column, @RequestParam("row") int row, @RequestParam("funginame") String funginame) {
        List<fungus> list = fungusService.selectByname(funginame);
        list = fungusService.findinthisfungi(list, column, row);
        return list;
    }

//    //所有种类分块图(不使用ajax时用这个方法)
//    @RequestMapping("/allFungi-zhu")
//    public ModelAndView getallFungi() {
//
//        List<fungus> list = fungusService.getAllFungi();
//        double[][] arr2 = fungusService.listconverttoarr(list);
////        int [][]arr = new int[size*size][3];
////        int k=0;
////        for(int i = 0;i<arr.length;i++) {
////            arr[i][0] = i%size;
////            arr[i][1] = k;
////            if((i+1)%size==0){
////                k++;
////            }
////            arr[i][2] = 0;
////        }
////        //把十万条数据放到arr中，相同方格的数目要相加
////        //float y = (float) ((109.672039 - 103.299969)/100);
////        float y = (float) ((109.672039 - 103.299969)/size);
////
////        //真正使用时要用上面这句
////       // float x = (float) ((29.443776 - 24.191224)/100);
////        float x = (float) ((29.443776 - 24.191224)/size);
////        for(int i=0;i<arr2.length;i++) {
////            //列方向第g1块行 对应row
////            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
////            int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
////            //double g1 = (arr2[i][0] - 24.631894)/x;
////            //行方向第g2块 对应column
////            int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
////
////            if(g1<0 || g2<0){
////                continue;
////            }
////            //double g2 = (arr2[i][1] - 103.622947)/y;
//////            int c = (int) (g1*100+g2);
////            int c = (int) (g1*size+g2);
////            arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
////
////        }
////        StringBuffer sb  = new StringBuffer();
////
////        for(int i = 0;i<arr.length;i++){
////            sb.append(arr[i][0]);
////            sb.append(",");
////            sb.append(arr[i][1]);
////            sb.append(",");
////            sb.append(arr[i][2]);
////            if(i!=arr.length-1){
////                sb.append(";");
////            }
////        }
//        StringBuffer sb = fungusService.fillarr(arr2);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("arr", sb.toString());
//        //是否为30种 0否1是
//        modelAndView.addObject("is30",0);
//        modelAndView.setViewName("map/map_allFungi");
//        return modelAndView;
//
//    }
    //所有种类分块图
    @RequestMapping("/allFungi-zhu")
    public ModelAndView getallFungi() {


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
        ModelAndView modelAndView = new ModelAndView();
        //是否为30种 0否1是
        modelAndView.addObject("is30",0);
        modelAndView.setViewName("map/map_allFungi");
        return modelAndView;

    }
    //所有种类分块图ajax接口
    @RequestMapping("/allFungi-zhu-ajax")
    @ResponseBody
    public StringBuffer getallFungiajax() {

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
        return sb;

    }

    //柱状图ajax接口所有
    @RequestMapping(value = "/map-one-zhu", method = RequestMethod.GET)
    @ResponseBody
    public Object getonezhu(@RequestParam("column") int column, @RequestParam("row") int row) {

        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.findinthisfungi(list, column, row);
//        System.out.println(listz.size());
//        List<fungus> listnewz = deepCopy(listz);
//        int x = fungusService.removeDuplicateCasemNonull(listnewz);

        return list;
    }
    //柱状图ajax接口求弹出框宽度
    @RequestMapping(value = "/map-one-zhu-width", method = RequestMethod.GET)
    @ResponseBody
    public Object getonezhuwid(@RequestParam("column") int column, @RequestParam("row") int row) throws  Exception{
        Map<Object,Object> twolist = new HashMap<>();
        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.findinthisfungi(list, column, row);
//        System.out.println(listz.size());
//        List<fungus> listnewz = deepCopy(listz);
//        int x = fungusService.removeDuplicateCasemNonull(listnewz);
        twolist.put("w",list.size());
        twolist.put("list1",list);
        return twolist;
    }
    //所有种类散点图
    @RequestMapping(value = "/toallScatter")
    public ModelAndView toallScatter() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("is30",0);
        modelAndView.setViewName("map/map_alleffectScatter");
        return modelAndView;
    }

    //散点图ajax接口所有
    @RequestMapping(value = "/allScatter")
    @ResponseBody
    public Object allScatter() {
//        List<fungus> list = fungusService.getAllFungi();
//        Map<Object, Object> twolist = new HashMap<>();
//        twolist = fungusService.listtoarrmap(list);
////        twolist.put("list1", list);
//
////        String twolist2 = JSON.toJSONString(twolist);
//
//        return twolist;
        List<fungus> list = fungusService.getAllFungi();
//        list = fungusService.find30Fungi(list);
        List<predict> list2 = predictService.getAllFungi();
//        list2 = predictService.find30Fungi(list2);
        Map<Object, Object> twolist_p = new HashMap<>();
        Map<Object, Object> twolist = new HashMap<>();
        twolist = fungusService.listtoarrmap(list);
        twolist_p = predictService.listtoarrmap(list2);
        twolist.put("namearr_p",twolist_p.get("namearr_p"));
        twolist.put("shuarr_p",twolist_p.get("shuarr_p"));
        twolist.put("longitudearr_p",twolist_p.get("longitudearr_p"));
        twolist.put("latitudearr_p",twolist_p.get("latitudearr_p"));
        twolist.put("valuearr_p",twolist_p.get("valuearr_p"));
        return twolist;
    }

//    //30种分块图(不使用ajax时)
//    @RequestMapping("/allFungi-zhu-30")
//    public ModelAndView get30Fungi() {
//
//        List<fungus> list = fungusService.getAllFungi();
//        list = fungusService.find30Fungi(list);
//        double[][] arr2 = fungusService.listconverttoarr(list);
////        int [][]arr = new int[size*size][3];
////        int k=0;
////        for(int i = 0;i<arr.length;i++) {
////            arr[i][0] = i%size;
////            arr[i][1] = k;
////            if((i+1)%size==0){
////                k++;
////            }
////            arr[i][2] = 0;
////        }
////        //把十万条数据放到arr中，相同方格的数目要相加
////        //float y = (float) ((109.672039 - 103.299969)/100);
////        float y = (float) ((109.672039 - 103.299969)/size);
////
////        //真正使用时要用上面这句
////       // float x = (float) ((29.443776 - 24.191224)/100);
////        float x = (float) ((29.443776 - 24.191224)/size);
////        for(int i=0;i<arr2.length;i++) {
////            //列方向第g1块行 对应row
////            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
////            int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
////            //double g1 = (arr2[i][0] - 24.631894)/x;
////            //行方向第g2块 对应column
////            int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
////
////            if(g1<0 || g2<0){
////                continue;
////            }
////            //double g2 = (arr2[i][1] - 103.622947)/y;
//////            int c = (int) (g1*100+g2);
////            int c = (int) (g1*size+g2);
////            arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
////
////        }
////        StringBuffer sb  = new StringBuffer();
////
////        for(int i = 0;i<arr.length;i++){
////            sb.append(arr[i][0]);
////            sb.append(",");
////            sb.append(arr[i][1]);
////            sb.append(",");
////            sb.append(arr[i][2]);
////            if(i!=arr.length-1){
////                sb.append(";");
////            }
////        }
//        StringBuffer sb = fungusService.fillarr(arr2);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("arr", sb.toString());
//        modelAndView.addObject("is30",1);
//        modelAndView.setViewName("map/map_allFungi");
//        return modelAndView;
//
//    }
//30种分块图
    @RequestMapping("/allFungi-zhu-30")
    public ModelAndView get30Fungi() {
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("is30",1);
        modelAndView.setViewName("map/map_allFungi");
        return modelAndView;

    }
    //30种分块图ajax
    @RequestMapping("/allFungi-zhu-30-ajax")
    @ResponseBody
    public StringBuffer get30Fungiajax() {

        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.find30Fungi(list);
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

        return sb;

    }
    //柱状图ajax接口30种
    @RequestMapping(value = "/map-one-zhu-30", method = RequestMethod.GET)
    @ResponseBody
    public Object  get30zhu(@RequestParam("column") int column, @RequestParam("row") int row) throws Exception {

//        List<fungus> list = fungusService.getAllFungi();
//        list = fungusService.find30Fungi(list);
//        listz = fungusService.findinthisfungi(list, column, row);
//        List<fungus> listnewz = deepCopy(listz);
//        int x = fungusService.removeDuplicateCasemNonull(listnewz);

        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.find30Fungi(list);
        list = fungusService.findinthisfungi(list, column, row);
//        List<fungus> listnewz = deepCopy(listz);
//        int x = fungusService.removeDuplicateCasemNonull(listnewz);

        return list.size();

    }
    //柱状图ajax接口30种柱状图宽度
    @RequestMapping(value = "/map-one-zhu-30-wid", method = RequestMethod.GET)
    @ResponseBody
    public Object  get30zhuwid(@RequestParam("column") int column, @RequestParam("row") int row) throws Exception {
//        listz = null;
        Map<Object,Object> twolist = new HashMap<>();
        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.find30Fungi(list);
        list = fungusService.findinthisfungi(list, column, row);
//        List<fungus> listnewz = deepCopy(listz);
//        int x = fungusService.removeDuplicateCasemNonull(listnewz);
        twolist.put("w",list.size());
        twolist.put("list1",list);

        return twolist;

    }
    //30种类散点图
    @RequestMapping(value = "/to30Scatter")
    public ModelAndView to30Scatter() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("is30",1);
        modelAndView.setViewName("map/map_alleffectScatter");
        return modelAndView;
    }

    //散点图ajax接口30种
    @RequestMapping(value = "/30Scatter")
    @ResponseBody
    public Object ajax30Scatter() {
        List<fungus> list = fungusService.getAllFungi();
        list = fungusService.find30Fungi(list);
        List<predict> list2 = predictService.getAllFungi();
        list2 = predictService.find30Fungi(list2);
        Map<Object, Object> twolist_p = new HashMap<>();
        Map<Object, Object> twolist = new HashMap<>();
        twolist = fungusService.listtoarrmap(list);
        twolist_p = predictService.listtoarrmap(list2);
        twolist.put("namearr_p",twolist_p.get("namearr_p"));
        twolist.put("shuarr_p",twolist_p.get("shuarr_p"));
        twolist.put("longitudearr_p",twolist_p.get("longitudearr_p"));
        twolist.put("latitudearr_p",twolist_p.get("latitudearr_p"));
        twolist.put("valuearr_p",twolist_p.get("valuearr_p"));
        return twolist;
    }
    //获取图片接口
    @RequestMapping(value = "/getphotos_all")
    @ResponseBody
    public Object getphotos(@RequestParam("longitude") String longitude1, @RequestParam("latitude") String latitude1, @RequestParam("name") String name,@RequestParam("objectid") String objectid) {
        String longitude = longitude1;
        String latitude = latitude1;
        String Objectid = objectid;

//        List<coordinatetoimgid> list1 = coordinatetoimgidService.getall();
//        String Objectid = coordinatetoimgidService.findObjectidbycoor(list1, longitude, latitude);
        List<idtoimg> list2 = IdtoimgService.getall();
        List<idtoimg> list3 = IdtoimgService.findbybizObjectid(list2, Objectid);
        StringBuffer sb = new StringBuffer();

        int count = 0;
        for (int i = 0; i < list3.size(); i++) {
//            sb.append("{\"alt\":\"" + name + "\",\"pid\":" + count + ",\"src\":\"http://gzbdi-yunshu.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\",\"thumb\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\"}");
            sb.append("{\"alt\":\"" + name + "\",\"pid\":" + count + ",\"src\":\""+imgurl + list3.get(i).getRefid() + "\",\"thumb\":\""+imgurl + list3.get(i).getRefid() + "\"}");
            count++;
            if (i != list3.size() - 1) {
                sb.append(",");
            }

        }
        String str = "{\"title\":\"" + name + "\",\"id\":1,\"start\":0,\"data\":[" + sb + "]}";
        return str;

    }


    //用于随机添加图片
//    @RequestMapping(value = "/randomimg")
//    @ResponseBody
//    public String insertimg() {
//        List<fungus> list = fungusService.getAllFungi();
//        list.forEach(data -> {
//            System.out.println(data.getId());
//            fungusService.updateByid(data.getId());
//
//        });
//        return "ok";
//    }
}
