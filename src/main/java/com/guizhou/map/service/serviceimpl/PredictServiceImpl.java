package com.guizhou.map.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.guizhou.map.controller.searchcontroller_new;
import com.guizhou.map.dao.predictMapper;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.predict;
import com.guizhou.map.domain.predictExample;
import com.guizhou.map.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@CacheConfig(cacheNames="predict")
public class PredictServiceImpl implements PredictService{

    private int only = 0;
    private int size = 40;

    @Autowired
    private predictMapper predictMapper;
    private static final Map<String,String> citymap;
    static {
        citymap = new HashMap<String,String>();
        citymap.put("贵阳市","贵阳市");
        citymap.put("六盘水地区","六盘水市");
        citymap.put("遵义市","遵义市");
        citymap.put("安顺地区","安顺市");
        citymap.put("毕节地区","毕节市");
        citymap.put("铜仁地区","铜仁市");
        citymap.put("黔西南布依族苗族自治州","黔西南布依族苗族自治州");
        citymap.put("黔东南苗族侗族自治州","黔东南苗族侗族自治州");
        citymap.put("黔南布依族苗族自治州","黔南布依族苗族自治州");

    }
    //@Cacheable(key="'allpredict'")
    public List<predict> getAllFungi() {
        //System.out.println("执行这里，说明缓存中读取不到数据，直接读取数据库....");
        return predictMapper.selectByExample(null);
    }
    //@Cacheable(key = "#genus")
    public List<predict> selectByname(String genus) {
        //System.out.println("执行这里，说明缓存中读取不到数据，直接读取数据库....byname");
        predictExample example = new predictExample();
        predictExample.Criteria criteria = example.createCriteria();
        criteria.andGenus1EqualTo(genus);
        return predictMapper.selectByExample(example);
    }

    public List<predict> updateByid(int id) {

        return null;
    }
    //去除名称相同的属
    public List<predict> removeDuplicateCase_p(List<predict> list) {
        Set<predict> set = new TreeSet<>(new Comparator<predict>() {

            @Override
            public int compare(predict o1, predict o2) {
                return o1.getGenus1().compareTo(o2.getGenus1());
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }
    // 根据市查找
    public List<predict> findbycity_p(List<predict> list, String city) {
        List<predict> list2 = new ArrayList<>();
        for(int i = 0 ;i < list.size();i++){
            if(list.get(i).getCity().equals(city)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }
    //根据县查找
    public List<predict> findbycountry_p(List<predict> list, String country) {
        List<predict> list2 = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            if (list.get(i).getCountry().equals(country)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }
    //根据属名查找
    public List<predict> findbyfungusname_p(List<predict> list, String fungusname) {
        List<predict> list2 = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getGenus1().equals(fungusname)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }
    //list转换为数组
    public double[][] listconverttoarr_p(List<predict> list) {
        double [][]arr2 = new double[list.size()][3];
        for(int i = 0;i < list.size();i++){

            arr2[i][0] = Double.parseDouble(list.get(i).getLongitude());
            arr2[i][1] = Double.parseDouble(list.get(i).getLatitude());
            arr2[i][2] = Double.parseDouble(list.get(i).getValue());

        }
        return arr2;
    }
    //list转换为数组
    public double[][] listconverttoarr_p(List<predict> list, List<fungus> list2) {


        double [][]arr2 = new double[(list.size()+list2.size())][3];



        for (int i = 0; i < list.size(); i++) {
                arr2[i][0] = Double.parseDouble(list.get(i).getLongitude());
                arr2[i][1] = Double.parseDouble(list.get(i).getLatitude());
                arr2[i][2] = Double.parseDouble(list.get(i).getValue());
            }


            for (int i = list.size(); i < (list.size() + list2.size()); i++) {
                arr2[i][0] = list2.get(i - list.size()).getLongitude();
                arr2[i][1] = list2.get(i - list.size()).getLatitude();
                arr2[i][2] = list2.get(i - list.size()).getValue();
            }

        return arr2;
    }
    //(预测+实际)根据行和列的值，来判断有多少菌种的坐标落到这个分块中
    public Object findinthisfungi_fp(List<fungus> list, List<predict> list_p, int column, int row) {
        List<fungus> list2 = new ArrayList<>();
        //把十万条数据放到arr中，相同方格的数目要相加
        float y = (float) ((109.672039 - 103.299969)/size);
        //     float x = (float) ((29.376113 - 24.631894)/100);
        //真正使用时要用上面这句
        float x = (float) ((29.443776 - 24.191224)/size);
        for(int i=0;i<list.size();i++){
            int g1 =  (int) ((float)( list.get(i).getLatitude() - 24.191224)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(list.get(i).getLongitude() - 103.299969)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;
                for(int j=0;j<list2.size();j++){
                    if(list.get(i).getGenus1().equals(list2.get(j).getGenus1())){
                        flag = 0;
                        int m1 = list2.get(j).getValue();
                        int m2 = list.get(i).getValue();
                        list2.get(j).setValue(m1+m2);
                        break;
                    }
                }
                if(flag == 1){
                    list2.add(list.get(i));
                }
            }
        }
        List<predict> listp = new ArrayList<>();
        for(int i=0;i<list_p.size();i++){
            int g1 =  (int) ((float)(Double.parseDouble( list_p.get(i).getLatitude()) - 24.191224)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(Double.parseDouble(list_p.get(i).getLongitude()) - 103.299969)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;

                for(int j=0;j<list2.size();j++){
                    if(list_p.get(i).getGenus1().equals(list2.get(j).getGenus1())){
                        flag = 0;
                        int m1 = list2.get(j).getValue();
                        int m2 = Integer.parseInt(list_p.get(i).getValue());
                        list2.get(j).setValue(m1+m2);
                        break;
                    }
                }
                for(int j=0;j<listp.size();j++){
                    if(list_p.get(i).getGenus1().equals(listp.get(j).getGenus1())){
                        flag = 0;
                        int m1 = Integer.parseInt(listp.get(j).getValue());
                        int m2 = Integer.parseInt(list_p.get(i).getValue());
                        listp.get(j).setValue(Integer.toString(m1+m2));
                        break;
                    }
                }
                if(flag == 1){
                    listp.add(list_p.get(i));
                }

            }
        }
        Map<Object,Object> twolist = new HashMap<>();
        twolist.put("list1",list2);
        twolist.put("list2",listp);
        String twolist2 = JSON.toJSONString(twolist);
        return twolist2;
    }
    //（预测）根据行和列的值，来判断有多少菌种的坐标落到这个分块中
    public List<predict> findinthisfungi_p(List<predict> list, int column, int row) {
        List<predict> list2 = new ArrayList<>();
        //把十万条数据放到arr中，相同方格的数目要相加
        float y = (float) ((109.672039 - 103.299969)/size);
        //     float x = (float) ((29.376113 - 24.631894)/100);
        //真正使用时要用上面这句
        float x = (float) ((29.443776 - 24.191224)/size);
        for(int i=0;i<list.size();i++){
            int g1 =  (int) ((float)( Double.parseDouble(list.get(i).getLatitude())- 24.191224)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(Double.parseDouble(list.get(i).getLongitude()) - 103.299969)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;
                for(int j=0;j<list2.size();j++){
                    if(list.get(i).getGenus1().equals(list2.get(j).getGenus1())){
                        flag = 0;
                        int m1 = Integer.parseInt(list2.get(j).getValue());
                        int m2 = Integer.parseInt(list.get(i).getValue());
                        int m = m1+m2;
                        list2.get(j).setValue(String.valueOf(m));
                        break;
                    }
                }
                if(flag == 1){
                    list2.add(list.get(i));
                }
            }
        }
        return list2;
    }

    public List<predict> selectdata(String city, String country, String funginame) {
        List<predict> list =  new ArrayList<>();
        list = getAllFungi();

        if(country.equals("all")){//找市内所有地区
            list = findbycity_p(list,city);

            searchcontroller_new.cityCountry = citymap.get(city);
            searchcontroller_new.zoom = 9;
        }else{//找单个县
            list =findbycountry_p(list,country);
            searchcontroller_new.cityCountry = country;
            searchcontroller_new.zoom  = 11;
        }
        //根据蘑菇种类筛选
        if(!funginame.equals("allkind")) {
            searchcontroller_new.only = 1;
            list = findbyfungusname_p(list, funginame);
        }
        return list;
    }

//    public List<predict> updateByid(int id){
//        predictExample example = new predictExample();
//        predictExample.Criteria criteria = example.createCriteria();
//        criteria.andIdEqualTo(id);
//        predict predict = new predict();
//        predict.setValue(1);
//        predictMapper.updateByExampleSelective(predict,example);
//        return null;
//    }
}
