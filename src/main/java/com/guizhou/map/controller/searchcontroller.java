//package com.guizhou.map.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.guizhou.map.domain.coordinatetoimgid;
//import com.guizhou.map.domain.fungus;
//import com.guizhou.map.domain.idtoimg;
//import com.guizhou.map.domain.predict;
//import com.guizhou.map.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.*;
//import java.util.function.DoubleToLongFunction;
//
//@RestController
//public class searchcontroller {
//
//    @Autowired
//    private FungusService fungusService;
//    @Autowired
//    private PredictService predictService;
//    @Autowired
//    private com.guizhou.map.service.IdtoimgService IdtoimgService;
//    @Autowired
//    private CoordinatetoimgidService coordinatetoimgidService;
//
//    private int only = 0;
//    private int size = 40;
//    private static final Map<String,String> citymap;
//
//    static {
//        citymap = new HashMap<String,String>();
//        citymap.put("贵阳市","贵阳市");
//        citymap.put("六盘水地区","六盘水市");
//        citymap.put("遵义市","遵义市");
//        citymap.put("安顺地区","安顺市");
//        citymap.put("毕节地区","毕节市");
//        citymap.put("铜仁地区","铜仁市");
//        citymap.put("黔西南布依族苗族自治州","黔西南布依族苗族自治州");
//        citymap.put("黔东南苗族侗族自治州","黔东南苗族侗族自治州");
//        citymap.put("黔南布依族苗族自治州","黔南布依族苗族自治州");
//
//    }
//    //按蘑菇的属去重
//    private List<fungus> removeDuplicateCase(List<fungus> list){
//        Set<fungus> set = new TreeSet<>(new Comparator<fungus>() {
//            @Override
//            public int compare(fungus o1, fungus o2) {
//                return o1.getGenus1().compareTo(o2.getGenus1());
//            }
//        });
//        set.addAll(list);
//        return new ArrayList<>(set);
//    }
//    //按蘑菇的属去重p
//    private List<predict> removeDuplicateCase_p(List<predict> list){
//        Set<predict> set = new TreeSet<>(new Comparator<predict>() {
//
//            @Override
//            public int compare(predict o1, predict o2) {
//                return o1.getGenus1().compareTo(o2.getGenus1());
//            }
//        });
//        set.addAll(list);
//        return new ArrayList<>(set);
//    }
//    //找到相应市区的数据
//    private List<fungus> findbycity(List<fungus> list,String city ){
//            List<fungus> list2 = new ArrayList<>();
//            if(city.equals("铜仁地区")){
//                for(int i = 0 ;i < list.size();i++){
//                    if(list.get(i).getCity().equals(city)){
//                        list2.add(list.get(i));
//                    }
//                    if(list.get(i).getCity().equals("铜仁市")){
//                        list2.add(list.get(i));
//                    }
//                }
//                return list2;
//            }
//            for(int i = 0 ;i < list.size();i++){
//                if(list.get(i).getCity().equals(city)){
//                    list2.add(list.get(i));
//                }
//            }
//            return list2;
//    }
//    //找到相应市区的数据p
//    private List<predict> findbycity_p(List<predict> list,String city ){
//        List<predict> list2 = new ArrayList<>();
//        for(int i = 0 ;i < list.size();i++){
//            if(list.get(i).getCity().equals(city)){
//                list2.add(list.get(i));
//            }
//        }
//        return list2;
//    }
//    //找到相应县区的数据
//    private List<fungus> findbycountry(List<fungus> list ,String country){
//        List<fungus> list2 = new ArrayList<>();
//        for(int i = 0;i<list.size();i++){
//            if (list.get(i).getCounty().equals(country)){
//                list2.add(list.get(i));
//            }
//        }
//        return list2;
//    }
//    //找到相应县区的数据p
//    private List<predict> findbycountry_p(List<predict> list ,String country){
//        List<predict> list2 = new ArrayList<>();
//        for(int i = 0;i<list.size();i++){
//            if (list.get(i).getCountry().equals(country)){
//                list2.add(list.get(i));
//            }
//        }
//        return list2;
//    }
//    //根据蘑菇属查找
//    private List<fungus> findbyfungusname(List<fungus> list,String fungusname){
//
//        List<fungus> list2 = new ArrayList<>();
//        for(int i = 0;i < list.size();i++){
//            if(list.get(i).getGenus1().equals(fungusname)){
//                list2.add(list.get(i));
//            }
//        }
//        return list2;
//    }
//    //根据蘑菇属查找p
//    private List<predict> findbyfungusname_p(List<predict> list,String fungusname){
//
//        List<predict> list2 = new ArrayList<>();
//        for(int i = 0;i < list.size();i++){
//            if(list.get(i).getGenus1().equals(fungusname)){
//                list2.add(list.get(i));
//            }
//        }
//        return list2;
//    }
//    //把list部分字段的值放到新的二维数组中
//    private double[][] listconverttoarr(List<fungus> list){
//        double [][]arr2 = new double[list.size()][3];
//        for(int i = 0;i < list.size();i++){
//            arr2[i][0] = list.get(i).getLongitude();
//            arr2[i][1] = list.get(i).getLatitude();
//            arr2[i][2] = list.get(i).getValue();
//        }
//        return arr2;
//    }
//    private double[][] listconverttoarr_p(List<predict> list){
//        double [][]arr2 = new double[list.size()][3];
//        for(int i = 0;i < list.size();i++){
//
//            arr2[i][0] = Double.parseDouble(list.get(i).getLongitude());
//            arr2[i][1] = Double.parseDouble(list.get(i).getLatitude());
//            arr2[i][2] = Double.parseDouble(list.get(i).getValue());
//
//        }
//        return arr2;
//    }
//    //把list部分字段的值放到新的二维数组中p
//    private double[][] listconverttoarr_p(List<predict> list,List<fungus> list2){
//        double [][]arr2 = new double[(list.size()+list2.size())][3];
//        for(int i = 0;i < list.size();i++){
//            arr2[i][0] = Double.parseDouble(list.get(i).getLongitude());
//            arr2[i][1] = Double.parseDouble(list.get(i).getLatitude());
//            arr2[i][2] = Double.parseDouble(list.get(i).getValue());
//        }
//        for(int i = list.size();i < (list.size()+list2.size());i++){
//            arr2[i][0] = list2.get(i-list.size()).getLongitude();
//            arr2[i][1] = list2.get(i-list.size()).getLatitude();
//            arr2[i][2] = list2.get(i-list.size()).getValue();
//        }
//        return arr2;
//    }
//
//    //根据行和列的值，来判断有多少菌种的坐标落到这个分块中
//    private Object findinthisfungi_fp(List<fungus> list,List<predict> list_p,int column,int row){
//        List<fungus> list2 = new ArrayList<>();
//        //把十万条数据放到arr中，相同方格的数目要相加
//        float y = (float) ((109.672039 - 103.299969)/size);
//        //     float x = (float) ((29.376113 - 24.631894)/100);
//        //真正使用时要用上面这句
//        float x = (float) ((29.443776 - 24.191224)/size);
//        for(int i=0;i<list.size();i++){
//            int g1 =  (int) ((float)( list.get(i).getLatitude() - 24.191224)/x);
//            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 =  (int) ((float)(list.get(i).getLongitude() - 103.299969)/y);
//            if(g1 == row && g2 == column) {
//                int flag = 1;
//                for(int j=0;j<list2.size();j++){
//                    if(list.get(i).getGenus1().equals(list2.get(j).getGenus1())){
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
//        List<predict> listp = new ArrayList<>();
//        for(int i=0;i<list_p.size();i++){
//            int g1 =  (int) ((float)(Double.parseDouble( list_p.get(i).getLatitude()) - 24.191224)/x);
//            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 =  (int) ((float)(Double.parseDouble(list_p.get(i).getLongitude()) - 103.299969)/y);
//            if(g1 == row && g2 == column) {
//                int flag = 1;
//
//                for(int j=0;j<list2.size();j++){
//                    if(list_p.get(i).getGenus1().equals(list2.get(j).getGenus1())){
//                        flag = 0;
//                        int m1 = list2.get(j).getValue();
//                        int m2 = Integer.parseInt(list_p.get(i).getValue());
//                        list2.get(j).setValue(m1+m2);
//                        break;
//                    }
//                }
//                for(int j=0;j<listp.size();j++){
//                    if(list_p.get(i).getGenus1().equals(listp.get(j).getGenus1())){
//                        flag = 0;
//                        int m1 = Integer.parseInt(listp.get(j).getValue());
//                        int m2 = Integer.parseInt(list_p.get(i).getValue());
//                        listp.get(j).setValue(Integer.toString(m1+m2));
//                        break;
//                    }
//                }
//                if(flag == 1){
//                    listp.add(list_p.get(i));
//                }
//
//            }
//        }
//        Map<Object,Object> twolist = new HashMap<>();
//        twolist.put("list1",list2);
//        twolist.put("list2",listp);
//        String twolist2 = JSON.toJSONString(twolist);
//        return twolist2;
//
//    }
//    private List<fungus> findinthisfungi(List<fungus> list,int column,int row) {
//        List<fungus> list2 = new ArrayList<>();
//        //把十万条数据放到arr中，相同方格的数目要相加
//        float y = (float) ((109.672039 - 103.299969) / size);
//        //     float x = (float) ((29.376113 - 24.631894)/100);
//        //真正使用时要用上面这句
//        float x = (float) ((29.443776 - 24.191224) / size);
//        for (int i = 0; i < list.size(); i++) {
//            int g1 = (int) ((float) (list.get(i).getLatitude() - 24.191224) / x);
//            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 = (int) ((float) (list.get(i).getLongitude() - 103.299969) / y);
//            if (g1 == row && g2 == column) {
//                int flag = 1;
//                for (int j = 0; j < list2.size(); j++) {
//                    if (list.get(i).getGenus1().equals(list2.get(j).getGenus1())) {
//                        flag = 0;
//                        int m1 = list2.get(j).getValue();
//                        int m2 = list.get(i).getValue();
//                        list2.get(j).setValue(m1 + m2);
//                        break;
//                    }
//                }
//                if (flag == 1) {
//                    list2.add(list.get(i));
//                }
//            }
//        }
//        return list2;
//    }
//    private List<predict> findinthisfungi_p(List<predict> list,int column,int row){
//        List<predict> list2 = new ArrayList<>();
//        //把十万条数据放到arr中，相同方格的数目要相加
//        float y = (float) ((109.672039 - 103.299969)/size);
//        //     float x = (float) ((29.376113 - 24.631894)/100);
//        //真正使用时要用上面这句
//        float x = (float) ((29.443776 - 24.191224)/size);
//        for(int i=0;i<list.size();i++){
//            int g1 =  (int) ((float)( Double.parseDouble(list.get(i).getLatitude())- 24.191224)/x);
//            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
//            //行方向第g2块 对应column
//            int g2 =  (int) ((float)(Double.parseDouble(list.get(i).getLongitude()) - 103.299969)/y);
//            if(g1 == row && g2 == column) {
//                int flag = 1;
//                for(int j=0;j<list2.size();j++){
//                    if(list.get(i).getGenus1().equals(list2.get(j).getGenus1())){
//                        flag = 0;
//                        int m1 = Integer.parseInt(list2.get(j).getValue());
//                        int m2 = Integer.parseInt(list.get(i).getValue());
//                        int m = m1+m2;
//                        list2.get(j).setValue(String.valueOf(m));
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
////    @RequestMapping("/search/{city}/{country}/{datakind}/{fungil}/{style}")
////    public Object search(@PathVariable(value = "city") String city,@PathVariable(value = "country") String country,@PathVariable(value = "datakind") String datakind,@PathVariable(value = "fungil") String funginame,@PathVariable(value = "style") String style) {
////
////    }
//    List<fungus> listzhu = new ArrayList<>();
//    List<predict> listzhu2 = new ArrayList<>();
//    String cityCountry;
//    int zoom = 0;
//    private String City;
//    private String Country;
//    private String Datakind;
//    private String Funginame;
//    @RequestMapping("/search/{city}/{country}/{datakind}/{fungil}/{style}")
//    public Object search(@PathVariable(value = "city") String city,@PathVariable(value = "country") String country,@PathVariable(value = "datakind") String datakind,@PathVariable(value = "fungil") String funginame,@PathVariable(value = "style") String style){
//        ModelAndView modelAndView = new ModelAndView();
//        listzhu = null;
//        listzhu2 = null;
//        City = city;
//        Country = country;
//        Datakind = datakind;
//        Funginame = funginame;
//        only  = 0;
//        List<fungus> list =  new ArrayList<>();
//        List<predict> list2 = new ArrayList<>();
//        if(datakind.equals("2")){
//                list = fungusService.getAllFungi();
//
//                if(country.equals("all")){//找市内所有地区
//                    list = findbycity(list,city);
//
//                    cityCountry = citymap.get(city);
//                    zoom = 9;
//                }else{//找单个县
//                    list = findbycountry(list,country);
//                    cityCountry = country;
//                    zoom  = 11;
//                }
//                //根据蘑菇种类筛选
//                if(!funginame.equals("allkind")) {
//                    only = 1;
//                    list = findbyfungusname(list, funginame);
//                }
//                listzhu =list;
//            if(style.equals("1")){//散点图
//    //            modelAndView.addObject("city",city);
//    //            modelAndView.addObject("country",country);
//    //            modelAndView.addObject("datakind",datakind);
//    //            modelAndView.addObject("funginame",funginame);
//                modelAndView.addObject("cityCountry",cityCountry);
//                modelAndView.addObject("zoom",zoom);
//                modelAndView.setViewName("map/map_effectScatter_search");
//                return modelAndView;
//            }else{//分块图
//                    modelAndView.addObject("list",list.toString());
//                    double [][]arr2 = listconverttoarr(list);
//                    //初始化要赋值的数组
//                    int [][]arr = new int[size*size][3];
//                    int k=0;
//                    for(int i = 0;i<arr.length;i++) {
//                        arr[i][0] = i%size;
//                        arr[i][1] = k;
//                        if((i+1)%size==0){
//                            k++;
//                        }
//                        arr[i][2] = 0;
//                    }
//                    //把十万条数据放到arr中，相同方格的数目要相加
//                    float y = (float) ((109.672039 - 103.299969)/size);
//                    //     float x = (float) ((29.376113 - 24.631894)/100);
//                    //真正使用时要用上面这句
//                    float x = (float) ((29.443776 - 24.191224)/size);
//                    for(int i=0;i<arr2.length;i++) {
//                        //列方向第g1块行 对应row
//                        //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
//                        int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
//                        //double g1 = (arr2[i][0] - 24.631894)/x;
//                        //行方向第g2块 对应column
//                        int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
//
//                        if(g1<0 || g2<0){
//                            continue;
//                        }
//                        //double g2 = (arr2[i][1] - 103.622947)/y;
//                        int c = (int) (g1*size+g2);
//
//                        arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
//
//                    }
//                    StringBuffer sb  = new StringBuffer();
//                    //sb.append("[");
//
//                    for(int i = 0;i<arr.length;i++){
//                        //string  =string + "[";
//                        // sb.append("[");
//                        sb.append(arr[i][0]);
//                        sb.append(",");
//                        sb.append(arr[i][1]);
//                        sb.append(",");
//                        sb.append(arr[i][2]);
//                        if(i!=arr.length-1){
//                            sb.append(";");
//                        }
//                    }
//                    modelAndView.addObject("only",only);
//                    modelAndView.addObject("cityCountry",cityCountry);
//                    modelAndView.addObject("zoom",zoom);
//                    modelAndView.addObject("arr",sb.toString());
//                    modelAndView.setViewName("map/map_kuai_search");
//                    return modelAndView;
//            }
//            //还要再加上混合和预测数据
//            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
//        }else if(datakind.equals("1")){//混合图
//            list = fungusService.getAllFungi();
//            list2 = predictService.getAllFungi();
//            if(country.equals("all")){//找市内所有地区
//                list = findbycity(list,city);
//                list2 = findbycity_p(list2,city);
//                cityCountry = citymap.get(city);
//                zoom = 9;
//            }else{//找单个县
//                list = findbycountry(list,country);
//                list2 = findbycountry_p(list2,country);
//                cityCountry = country;
//                zoom  = 11;
//            }
//            //根据蘑菇种类筛选
//            if(!funginame.equals("allkind")) {
//                only = 1;
//                list = findbyfungusname(list, funginame);
//                list2 = findbyfungusname_p(list2,funginame);
//            }
//            listzhu =list;
//            listzhu2 = list2;
//            if(style.equals("1")){//散点图
//                //            modelAndView.addObject("city",city);
//                //            modelAndView.addObject("country",country);
//                //            modelAndView.addObject("datakind",datakind);
//                //            modelAndView.addObject("funginame",funginame);
//                modelAndView.addObject("cityCountry",cityCountry);
//                modelAndView.addObject("zoom",zoom);
//                modelAndView.setViewName("map/map_effectScatter_search");
//                return modelAndView;
//            }else{//分块图
//               // modelAndView.addObject("list",list.toString());
//                double [][]arr2 = listconverttoarr_p(list2,list);
//                //初始化要赋值的数组
//                int [][]arr = new int[size*size][3];
//                int k=0;
//                for(int i = 0;i<arr.length;i++) {
//                    arr[i][0] = i%size;
//                    arr[i][1] = k;
//                    if((i+1)%size==0){
//                        k++;
//                    }
//                    arr[i][2] = 0;
//                }
//                //把十万条数据放到arr中，相同方格的数目要相加
//                float y = (float) ((109.672039 - 103.299969)/size);
//                //     float x = (float) ((29.376113 - 24.631894)/100);
//                //真正使用时要用上面这句
//                float x = (float) ((29.443776 - 24.191224)/size);
//                for(int i=0;i<arr2.length;i++) {
//                    //列方向第g1块行 对应row
//                    //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
//                    int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
//                    //double g1 = (arr2[i][0] - 24.631894)/x;
//                    //行方向第g2块 对应column
//                    int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
//
//                    if(g1<0 || g2<0){
//                        continue;
//                    }
//                    //double g2 = (arr2[i][1] - 103.622947)/y;
//                    int c = (int) (g1*size+g2);
//
//                    arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
//
//                }
//                StringBuffer sb  = new StringBuffer();
//                //sb.append("[");
//
//                for(int i = 0;i<arr.length;i++){
//                    //string  =string + "[";
//                    // sb.append("[");
//                    sb.append(arr[i][0]);
//                    sb.append(",");
//                    sb.append(arr[i][1]);
//                    sb.append(",");
//                    sb.append(arr[i][2]);
//                    if(i!=arr.length-1){
//                        sb.append(";");
//                    }
//                }
//                modelAndView.addObject("only",only);
//                modelAndView.addObject("cityCountry",cityCountry);
//                modelAndView.addObject("zoom",zoom);
//                modelAndView.addObject("arr",sb.toString());
//                modelAndView.setViewName("map/map_kuai_search");
//                return modelAndView;
//            }
//            //还要再加上混合和预测数据
//            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
//        }else{ //预测
//
//            list2 = predictService.getAllFungi();
//            if(country.equals("all")){//找市内所有地区
//                list2 = findbycity_p(list2,city);
//                cityCountry = citymap.get(city);
//                zoom = 9;
//            }else{//找单个县
//                list2 = findbycountry_p(list2,country);
//                cityCountry = country;
//                zoom  = 11;
//            }
//            //根据蘑菇种类筛选
//            if(!funginame.equals("allkind")) {
//                only = 1;
//                list2 = findbyfungusname_p(list2,funginame);
//            }
//            listzhu2 = list2;
//            if(style.equals("1")){//散点图
//                //            modelAndView.addObject("city",city);
//                //            modelAndView.addObject("country",country);
//                //            modelAndView.addObject("datakind",datakind);
//                //            modelAndView.addObject("funginame",funginame);
//
//                modelAndView.addObject("cityCountry",cityCountry);
//                modelAndView.addObject("zoom",zoom);
//                modelAndView.setViewName("map/map_effectScatter_search");
//
//                return modelAndView;
//            }else{//分块图
//                // modelAndView.addObject("list",list.toString());
//                double [][]arr2 = listconverttoarr_p(list2);
//                //初始化要赋值的数组
//                int [][]arr = new int[size*size][3];
//                int k=0;
//                for(int i = 0;i<arr.length;i++) {
//                    arr[i][0] = i%size;
//                    arr[i][1] = k;
//                    if((i+1)%size==0){
//                        k++;
//                    }
//                    arr[i][2] = 0;
//                }
//                //把十万条数据放到arr中，相同方格的数目要相加
//                float y = (float) ((109.672039 - 103.299969)/size);
//                //     float x = (float) ((29.376113 - 24.631894)/100);
//                //真正使用时要用上面这句
//                float x = (float) ((29.443776 - 24.191224)/size);
//                for(int i=0;i<arr2.length;i++) {
//                    //列方向第g1块行 对应row
//                    //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
//                    int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
//                    //double g1 = (arr2[i][0] - 24.631894)/x;
//                    //行方向第g2块 对应column
//                    int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
//
//                    if(g1<0 || g2<0){
//                        continue;
//                    }
//                    //double g2 = (arr2[i][1] - 103.622947)/y;
//                    int c = (int) (g1*size+g2);
//
//                    arr[c][2] = (int) (arr[c][2] + arr2[i][2]);
//
//                }
//                StringBuffer sb  = new StringBuffer();
//                //sb.append("[");
//
//                for(int i = 0;i<arr.length;i++){
//                    //string  =string + "[";
//                    // sb.append("[");
//                    sb.append(arr[i][0]);
//                    sb.append(",");
//                    sb.append(arr[i][1]);
//                    sb.append(",");
//                    sb.append(arr[i][2]);
//                    if(i!=arr.length-1){
//                        sb.append(";");
//                    }
//                }
//                modelAndView.addObject("only",only);
//                modelAndView.addObject("cityCountry",cityCountry);
//                modelAndView.addObject("zoom",zoom);
//                modelAndView.addObject("arr",sb.toString());
//                modelAndView.setViewName("map/map_kuai_search");
//                return modelAndView;
//            }
//            //还要再加上混合和预测数据
//            //转化市的名字  县的传县，市的传市，根据县市设置最开始的缩放比例模拟
//
//        }
//
//    }
//    //柱状图ajax接口
//    @RequestMapping(value = "/map-one-zhu-sea",method = RequestMethod.POST)
//    @ResponseBody
//    public Object getonezhusea(@RequestParam("column") int  column ,@RequestParam("row")int  row){
//        List<fungus> list = new ArrayList<>();
//        List<predict> list2 = new ArrayList<>();
//        list = fungusService.getAllFungi();
//        list2 = predictService.getAllFungi();
//        if(Country.equals("all")){//找市内所有地区
//            list = findbycity(list,City);
//            list2 = findbycity_p(list2,City);
//        }else{//找单个县
//            list = findbycountry(list,Country);
//            list2 = findbycountry_p(list2,Country);
//        }
//        //根据蘑菇种类筛选
//        if(!Funginame.equals("allkind")) {
//
//            list = findbyfungusname(list, Funginame);
//            list2 = findbyfungusname_p(list2,Funginame);
//        }
//
//        if(Datakind.equals("2")){
//            list = findinthisfungi(list,column,row);
//            return list;
//        }else if(Datakind.equals("3")){
//
//            list2 = findinthisfungi_p(list2,column,row);
//            list2.forEach(data->{
//                System.out.println(data.getGenus1());
//            });
//            return list2;
//        }else{
//            return findinthisfungi_fp(list,list2,column,row);
//
//        }
//    }
//    //散点图searchajax接口
//    @RequestMapping(value = "/map-scatter-sea")
//    @ResponseBody
//    public Object getscattersea(){
//
//        if(listzhu2 == null) {
//            return listzhu;
//        }else if(listzhu == null){
//            Map<Object,Object> twolist = new HashMap<>();
//            twolist.put("list1","预测");
//            twolist.put("list2",listzhu2);
//            String twolist2 = JSON.toJSONString(twolist);
//            return twolist2;
//        }else{
//            Map<Object,Object> twolist = new HashMap<>();
//            twolist.put("list1",listzhu);
//            twolist.put("list2",listzhu2);
//            String twolist2 = JSON.toJSONString(twolist);
//            return twolist2;
//        }
//    }
//    //下拉框的ajax接口
//    @RequestMapping("/selectkind/{selectvalue}")
//    @ResponseBody
//    public Object selectkind(@PathVariable(value = "selectvalue") String selectvalue){
//
//
//        if(selectvalue.equals("2")){//查询实际数据
//            List<fungus> list = new ArrayList<>();
//            list = fungusService.getAllFungi();
//            list = removeDuplicateCase(list);
//            return list;
//        }
//        else if(selectvalue.equals("1")){//查询混合数据
//
//            List<fungus> list = new ArrayList<>();
//            list = fungusService.getAllFungi();
//            list = removeDuplicateCase(list);
//            return list;
//        }else{//c查询预测数据
//            List<predict> list = new ArrayList<>();
//            list = predictService.getAllFungi();
//            list = removeDuplicateCase_p(list);
//            return list;
//        }
//
//    }
//
//    @RequestMapping(value = "/getphotos")
//    @ResponseBody
//    public Object getphotos(@RequestParam("longitude") String longitude1 ,@RequestParam("latitude") String latitude1 ,@RequestParam("name")String  name){
////        listzhu.forEach(data->{
////            System.out.println(data.get);
////        });
//        String longitude = longitude1;
//        String latitude = latitude1;
//        List<coordinatetoimgid> list1 = coordinatetoimgidService.getall();
//        String Objectid  = coordinatetoimgidService.findObjectidbycoor(list1,longitude,latitude);
//        if(Objectid == null){
//            List<predict> listp = predictService.getAllFungi();
//            for(int i = 0;i<listp.size();i++){
//                if(listp.get(i).getLongitude().equals(longitude) && listp.get(i).getLatitude().equals(latitude)){
//                        for(int j = 0;j < list1.size();j++ ){
//                            if(list1.get(j).getGenus1().equals(listp.get(i).getGenus1())){
//                                Objectid = list1.get(j).getId();
//                                break;
//                            }
//                        }
//                        break;
//                }
//            }
//        }
//        List<idtoimg> list2 = IdtoimgService.getall();
//        List<idtoimg> list3 = IdtoimgService.findbybizObjectid(list2,Objectid);
//        StringBuffer sb = new StringBuffer();
//        int count = 0;
//        for(int i = 0;i<list3.size();i++) {
//            sb.append("{\"alt\":\"" + name + "\",\"pid\":" +count + ",\"src\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\",\"thumb\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\"}");
//            count++;
//            if(i != list3.size()-1){
//                sb.append(",");
//           }
//       }
//       String str="{\"title\":\""+name+"\",\"id\":1,\"start\":0,\"data\":["+sb+"]}";
//       return str;
//   }
//
//}
