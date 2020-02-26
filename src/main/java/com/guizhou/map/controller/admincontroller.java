//package com.guizhou.map.controller;
//
//import com.guizhou.map.domain.Fungi;
//import com.guizhou.map.service.FungiService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.*;
//
////后台跳转逻辑代码
//@Controller
//public class admincontroller {
//
//    @Autowired
//    private FungiService fungiService;
//
//    //利用set，按姓名去重，来获得全部共多少类菌种
//    private List<Fungi> removeDuplicateCase(List<Fungi> list){
//        Set<Fungi> set = new TreeSet<>(new Comparator<Fungi>() {
//            @Override
//            public int compare(Fungi o1, Fungi o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
//        set.addAll(list);
//        return new ArrayList<>(set);
//    }
//    //把list部分字段的值放到新的二维数组中
//    private double[][] listconverttoarr(List<Fungi> list){
//        double [][]arr2 = new double[list.size()][3];
//        for(int i = 0;i < list.size();i++){
//            arr2[i][0] = Double.parseDouble(list.get(i).getLongitude());
//            arr2[i][1] = Double.parseDouble(list.get(i).getLatitude());
//            arr2[i][2] = list.get(i).getValue();
//        }
//        return arr2;
//    }
//    //根据行和列的值，来判断有多少菌种的坐标落到这个分块中
//    private List<Fungi> findinthisfungi(List<Fungi> list,int column,int row){
//        List<Fungi> list2 = new ArrayList<>();
//        //把十万条数据放到arr中，相同方格的数目要相加
//        float y = (float) ((109.326109 - 103.622947)/100);
//        //     float x = (float) ((29.376113 - 24.631894)/100);
//        //真正使用时要用上面这句
//        float x = (float) ((29.376113 - 24.000000)/100);
//        for(int i=0;i<list.size();i++){
//            int g1 =  (int) ((float)( Double.parseDouble(list.get(i).getLongitude()) - 24.000000)/x);
//            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 =  (int) ((float)(Double.parseDouble(list.get(i).getLatitude()) - 103.622947)/y);
//            if(g1 == row && g2 == column) {
//                int flag = 1;
//                for(int j=0;j<list2.size();j++){
//                    if(list.get(i).getName().equals(list2.get(j).getName())){
//                        flag = 0;
//                        int m1 = list2.get(j).getValue();
//                        int m2 = list.get(i).getValue();
//                        list2.get(j).setValue(m1+m2);
//                        break;
//                    }
//                }
//                if(flag == 1){
//                    list2.add(list.get(i));
//                }
//            }
//        }
//        return list2;
//    }
//    //主页跳转
//    @RequestMapping("/home")
//    public ModelAndView getadmin() {
//        List<Fungi> list = fungiService.getAllFungi();
//
////        ArrayList<Object> newlist = new ArrayList<>();
////        list.forEach(data->{
////
////        });
//        list =  removeDuplicateCase(list);
////        list.forEach(data->{
////            System.out.println(data.getName());
////        });
//        ModelAndView modelAndView =new ModelAndView();
//        modelAndView.addObject("funginamelist",list);
//        modelAndView.setViewName("admin/home");
//        return modelAndView;
//    }
//    //根据name来跳转并获得相应的数据来渲染
//    @RequestMapping("/map-zhu/{name}")
//    public ModelAndView getmap(@PathVariable("name")String name){
//
//        List<Fungi> list = new ArrayList<>();
//        list = fungiService.selectByname(name);
//        double [][]arr2 = listconverttoarr(list);
//
////        list.forEach(data->{
////            System.out.println(Double.parseDouble(data.getLongitude()));
////        });
//        //初始化要赋值的数组
//        int [][]arr = new int[10000][3];
//        int k=0;
//        for(int i = 0;i<arr.length;i++) {
//            arr[i][0] = i%100;
//            arr[i][1] = k;
//            if((i+1)%100==0){
//                k++;
//            }
//            arr[i][2] = 0;
//        }
//        //把十万条数据放到arr中，相同方格的数目要相加
//        float y = (float) ((109.326109 - 103.622947)/100);
//   //     float x = (float) ((29.376113 - 24.631894)/100);
//       //真正使用时要用上面这句
//        float x = (float) ((29.376113 - 24.000000)/100);
//        for(int i=0;i<arr2.length;i++) {
//            //列方向第g1块行 对应row
//            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
//            int g1 =  (int) ((float)(arr2[i][0] - 24.000000)/x);
//            //double g1 = (arr2[i][0] - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 =  (int) ((float)(arr2[i][1] - 103.622947)/y);
//            //double g2 = (arr2[i][1] - 103.622947)/y;
//            int c = (int) (g1*100+g2);
//            arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
//
//        }
//        StringBuffer sb  = new StringBuffer();
//        //sb.append("[");
//
//        for(int i = 0;i<arr.length;i++){
//            //string  =string + "[";
//           // sb.append("[");
//            sb.append(arr[i][0]);
//            sb.append(",");
//            sb.append(arr[i][1]);
//            sb.append(",");
//            sb.append(arr[i][2]);
//            if(i!=arr.length-1){
//                sb.append(";");
//            }
//        }
//        //string  =string + "]";
//        //sb.append("]");
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("arr",sb.toString());
//        modelAndView.setViewName("map/map_zhu");
//        return modelAndView;
//    }
//    //单种类散点图
//    @RequestMapping("/map-effectScatter/{name}")
//    public ModelAndView geteffectScatter(@PathVariable("name") String name){
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("name",name);
//        modelAndView.setViewName("map/map_effectScatter");
//       // modelAndView.setViewName("index2");
//        return modelAndView;
//    }
//    //单种类散点图ajax接口
//    @RequestMapping("/oneScatter/{name}")
//    @ResponseBody
//    public Object getoneScaatterimpl(@PathVariable("name") String name){
//
//        List<Fungi> list = new ArrayList<>();
//        list = fungiService.selectByname(name);
//        return list;
//    }
//    //所有种类分块图
//    @RequestMapping("/allFungi-zhu")
//    public ModelAndView getallFungi(){
//
//        List<Fungi> list = new ArrayList<>();
//        list = fungiService.getAllFungi();
//        double [][]arr2 = listconverttoarr(list);
//
////        list.forEach(data->{
////            System.out.println(Double.parseDouble(data.getLongitude()));
////        });
//        //初始化要赋值的数组
//        int [][]arr = new int[10000][3];
//        int k=0;
//        for(int i = 0;i<arr.length;i++) {
//            arr[i][0] = i%100;
//            arr[i][1] = k;
//            if((i+1)%100==0){
//                k++;
//            }
//            arr[i][2] = 0;
//        }
//        //把十万条数据放到arr中，相同方格的数目要相加
//        float y = (float) ((109.326109 - 103.622947)/100);
//        //     float x = (float) ((29.376113 - 24.631894)/100);
//        //真正使用时要用上面这句
//        float x = (float) ((29.376113 - 24.000000)/100);
//        for(int i=0;i<arr2.length;i++) {
//            //经度第g1块
//            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
//            int g1 =  (int) ((float)(arr2[i][0] - 24.000000)/x);
//            //double g1 = (arr2[i][0] - 24.631894)/x;
//            //纬度第g2块
//            int g2 =  (int) ((float)(arr2[i][1] - 103.622947)/y);
//            //double g2 = (arr2[i][1] - 103.622947)/y;
//            int c = (int) (g1*100+g2);
//            arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
//
//        }
//        StringBuffer sb  = new StringBuffer();
//        //sb.append("[");
//
//        for(int i = 0;i<arr.length;i++){
//            //string  =string + "[";
//            // sb.append("[");
//            sb.append(arr[i][0]);
//            sb.append(",");
//            sb.append(arr[i][1]);
//            sb.append(",");
//            sb.append(arr[i][2]);
//            if(i!=arr.length-1){
//                sb.append(";");
//            }
//        }
//        //string  =string + "]";
//        //sb.append("]");
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("arr",sb.toString());
//        modelAndView.setViewName("map/map_allFungi");
//        return modelAndView;
//
//    }
//    //柱状图ajax接口
//    @RequestMapping(value = "/map-one-zhu",method = RequestMethod.GET)
//    @ResponseBody
//    public Object getonezhu(@RequestParam("column") int  column ,@RequestParam("row")int  row){
//        List list = new ArrayList();
//        list = fungiService.getAllFungi();
//        list = findinthisfungi(list,column,row);
//        return list;
//    }
//    //所有种类散点图
//    @RequestMapping(value="/toallScatter")
//    public String toallScatter(){
//        return "map/map_alleffectScatter";
//    }
//    //散点图ajax接口
//    @RequestMapping(value = "/allScatter")
//    @ResponseBody
//    public Object allScatter(){
//        List<Fungi> list = new ArrayList<>();
//        list = fungiService.getAllFungi();
//        return list;
//    }
//}
