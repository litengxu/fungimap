package com.guizhou.map.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.guizhou.map.common.domain.baseconfig;
import com.guizhou.map.common.staticvariable;
import com.guizhou.map.controller.searchcontroller_new;
import com.guizhou.map.dao.predictMapper;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.predict;
import com.guizhou.map.domain.predictExample;
import com.guizhou.map.service.PredictService;
import javafx.beans.binding.DoubleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@CacheConfig(cacheNames="predict")
public class PredictServiceImpl implements PredictService{

    private int only = 0;
//    private  int size = baseconfig.getKuaisize();
//    private double longitudemax = Double.parseDouble(baseconfig.getLongitudemax());
//    private double longitudemin = Double.parseDouble(baseconfig.getLongitudemin());
//    private double latitudemax = Double.parseDouble(baseconfig.getLatitudemax());
//    private double latitudemin = Double.parseDouble(baseconfig.getLatitudemin());
    private  int size = 40;
    private double longitudemax = 109.672039;
    private double longitudemin = 103.299969;
    private double latitudemax = 29.443776;
    private double latitudemin = 24.191224;
    @Autowired
    private predictMapper predictMapper;

    @Override
    public List<predict> selectByspecialname(String specialname) {
        //System.out.println("执行这里，说明缓存中读取不到数据，直接读取数据库....byname");
        predictExample example = new predictExample();
        predictExample.Criteria criteria = example.createCriteria();
        criteria.andGenus1EqualTo(specialname);
        return predictMapper.selectByExample(example);
    }

    @Override
    public Map listtoarrmap(List<predict> list) {
        int  n =list.size();
        //id数组
        //菌种名字
//        String name[] = new String[n];
        //属
        String shu[] = new String[n];
        String name[] = new String[n];
        //经度
        String longitude[] = new String[n];
        //纬度
        String latitude[] = new String[n];
        //数量
        String value[] = new String[n];
        int j = 0;
        for(int i=0;i< n;i++){
            if(list.get(j).getLatitude().length() == 0 || list.get(j).getLongitude().length() == 0){
                n--;
                i--;
                j++;
                continue;
            }
            name[i] = list.get(j).getSpecialname();
            shu[i] = list.get(j).getGenus1();
            longitude[i] = list.get(j).getLongitude();
            latitude[i] = list.get(j).getLatitude();
            value[i] = list.get(j).getProbability();
            j++;
        }

        Map<Object,Object> maplist = new HashMap<>();
//        maplist.put("namearr",name);
        maplist.put("namearr_p",name);
        maplist.put("shuarr_p",shu);
        maplist.put("longitudearr_p",longitude);
        maplist.put("latitudearr_p",latitude);
        maplist.put("valuearr_p",value);
        return maplist;
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
    //计算两个list中一共有多少种
    @Override
    public int removeDuplicateCase_fpNotNull(List<fungus> list1,List<predict> list2) {
        return 0;
    }

    // 根据市查找
    public List<predict> findbycity_p(String city) {
        List<predict> list2 = new ArrayList<>();
//        for(int i = 0 ;i < list.size();i++){
//            if(list.get(i).getCity().equals(city)){
//                list2.add(list.get(i));
//            }
//        }
        //全改为以市或者州结尾，模糊查询（地区结尾）
        city = staticvariable.citymap.get(city);
        city = city.substring(0,city.length()-1);
        list2 = predictMapper.selectByCity(city);
        return list2;
    }

    @Override
    public List<predict> find30Fungi(List<predict> list) {
        List<predict> newlist = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            for(int j = 0;j<staticvariable.list30.size();j++){
                //看预测数据修改
                if(list.get(i).getSpecialname().equals(staticvariable.list30.get(j))){
                    newlist.add(list.get(i));
                }
            }
        }
        return newlist;
    }

    //根据县查找
//    public List<predict> findbycountry_p(List<predict> list, String country) {
//        List<predict> list2 = new ArrayList<>();
//        for(int i = 0;i<list.size();i++){
//            if (list.get(i).getCountry().equals(country)){
//                list2.add(list.get(i));
//            }
//        }
//        return list2;
//    }
    public List<predict> findbycountry_p(String country) {
        List<predict> list2 = new ArrayList<>();
        country = country.substring(0,country.length()-1);
        list2 = predictMapper.selectByCountry(country);
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
                arr2[i][0] = Double.parseDouble(list2.get(i - list.size()).getShorttext1567760963892());
                arr2[i][1] = Double.parseDouble(list2.get(i - list.size()).getShorttext1567760966363());
                arr2[i][2] = list2.get(i - list.size()).getValue();
            }

        return arr2;
    }
    //(预测+实际)根据行和列的值，来判断有多少菌种的坐标落到这个分块中
    public Map findinthisfungi_fp(List<fungus> list, List<predict> list_p, int column, int row) {
        List<fungus> list2 = new ArrayList<>();
        //把十万条数据放到arr中，相同方格的数目要相加
        float y = (float) ((longitudemax - longitudemin)/size);
        //     float x = (float) ((29.376113 - 24.631894)/100);
        //真正使用时要用上面这句
        float x = (float) ((latitudemax - latitudemin)/size);
        for(int i=0;i<list.size();i++){
            int g1 =  (int) ((float)(Double.parseDouble( list.get(i).getShorttext1567760966363() )- latitudemin)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(Double.parseDouble(list.get(i).getShorttext1567760963892() )- longitudemin)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;
                for(int j=0;j<list2.size();j++){
                    if(list.get(i).getShorttext1567761455594().length() == 0){//当属为空时
                        if(list2.get(j).getShorttext1567761455594().length() == 0){
                            if(list.get(i).getShorttext1567761353834().equals(list2.get(j).getShorttext1567761353834())){
                                flag = 0;
                                int m1 = list2.get(j).getValue();
                                int m2 = list.get(i).getValue();
                                list2.get(j).setValue(m1 + m2);
                                break;
                            }
                        }
                    }
                    else {
                        if (list.get(i).getShorttext1567761455594().equals(list2.get(j).getShorttext1567761455594())) {
                            flag = 0;
                            int m1 = list2.get(j).getValue();
                            int m2 = list.get(i).getValue();
                            list2.get(j).setValue(m1 + m2);
                            break;
                        }
                    }

                }
                if(flag == 1){
                    list2.add(list.get(i));
                }
            }
        }
        List<predict> listp = new ArrayList<>();
        for(int i=0;i<list_p.size();i++){
            int g1 =  (int) ((float)(Double.parseDouble( list_p.get(i).getLatitude()) - latitudemin)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(Double.parseDouble(list_p.get(i).getLongitude()) - longitudemin)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;
                //list2中有属（不存在时比较名称）可能相等时
                //当预测表中的属为空时,当list2中的属也为空时(比较菌种名称，若相等，则value相加)，flag=0（一个数据只加一次）
                //当list2中的属不为空时，不做操作。因为这个数据会被加到listp中。
                // 当预测表中的属不为空时，比较list2和预测表中的属，相等value+1。flag=0,
                //若没有相等的，不做操作。
                //listp中有属（不存在时比较名称）可能相等时同理
                if(list_p.get(i).getGenus1().length() == 0){
                    for (int j=0;j<list2.size();j++){
                        if (list2.get(j).getShorttext1567761455594().length() == 0){
                            if(list2.get(j).getShorttext1567761353834().equals(list_p.get(i).getSpecialname())){
                                flag = 0;
                                int m1 = list2.get(j).getValue();
                                int m2 = Integer.parseInt(list_p.get(i).getValue());
                                list2.get(j).setValue(m1 + m2);
                                break;
                            }
                        }
                    }
                }else {
                    for (int j = 0; j < list2.size(); j++) {
                        if (list_p.get(i).getGenus1().equals(list2.get(j).getShorttext1567761455594())) {
                            flag = 0;
                            int m1 = list2.get(j).getValue();
                            int m2 = Integer.parseInt(list_p.get(i).getValue());
                            list2.get(j).setValue(m1 + m2);
                            break;
                        }
                    }
                }
                if(flag == 1) {
                    if(list_p.get(i).getGenus1().length() == 0){

                        for (int j = 0; j < listp.size(); j++) {
                            if (listp.get(j).getGenus1().length() == 0) {
                                if (list_p.get(i).getSpecialname().equals(listp.get(j).getSpecialname())) {
                                    flag = 0;
                                    int m1 = Integer.parseInt(listp.get(j).getValue());
                                    int m2 = Integer.parseInt(list_p.get(i).getValue());
                                    listp.get(j).setValue(Integer.toString(m1 + m2));
                                    break;
                                }
                            }
                        }
                    }else {
                        for (int j = 0; j < listp.size(); j++) {
                            if (list_p.get(i).getGenus1().equals(listp.get(j).getGenus1())) {
                                flag = 0;
                                int m1 = Integer.parseInt(listp.get(j).getValue());
                                int m2 = Integer.parseInt(list_p.get(i).getValue());
                                listp.get(j).setValue(Integer.toString(m1 + m2));
                                break;
                            }
                        }
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
        int w = (list2.size()+listp.size());

//        for(int i =0;i<list2.size();i++){
//            for(int j =0;j<listp.size();j++){
//                if(list2.get(i).getShorttext1567761455594().equals(listp.get(j).getGenus1())){
//                    w = w-1;
//                }
//            }
//        }
        twolist.put("w",w);
//        String twolist2 = JSON.toJSONString(twolist);
        return twolist;
    }
    //（预测）根据行和列的值，来判断有多少菌种的坐标落到这个分块中
    public List<predict> findinthisfungi_p(List<predict> list, int column, int row) {
        List<predict> list2 = new ArrayList<>();
        //把十万条数据放到arr中，相同方格的数目要相加
        float y = (float) ((longitudemax - longitudemin)/size);
        //     float x = (float) ((29.376113 - 24.631894)/100);
        //真正使用时要用上面这句
        float x = (float) ((latitudemax - latitudemin)/size);
        for(int i=0;i<list.size();i++){
            int g1 =  (int) ((float)( Double.parseDouble(list.get(i).getLatitude())- latitudemin)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(Double.parseDouble(list.get(i).getLongitude()) - longitudemin)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;
                if(list.get(i).getGenus1().length() == 0){
                    for (int j=0;j<list2.size();j++){
                        if (list2.get(j).getGenus1().length() == 0){
                            if(list2.get(j).getSpecialname().equals(list.get(i).getSpecialname())){
                                flag = 0;
                                int m1 = Integer.parseInt(list2.get(j).getValue());
                                int m2 = Integer.parseInt(list.get(i).getValue());
                                int m = m1+m2;
                                list2.get(j).setValue(String.valueOf(m));
                                break;
                            }
                        }else{
                            if(list2.get(j).getSpecialname().equals(list.get(i).getGenus1())){
                                flag = 0;
                                int m1 = Integer.parseInt(list2.get(j).getValue());
                                int m2 = Integer.parseInt(list.get(i).getValue());
                                int m = m1+m2;
                                list2.get(j).setValue(String.valueOf(m));
                                break;
                            }
                        }
                    }
                }else {
                    for (int j = 0; j < list2.size(); j++) {
                        if (list2.get(j).getGenus1().length() != 0) {
                            if (list.get(i).getGenus1().equals(list2.get(j).getGenus1())) {
                                flag = 0;
                                int m1 = Integer.parseInt(list2.get(j).getValue());
                                int m2 = Integer.parseInt(list.get(i).getValue());
                                int m = m1 + m2;
                                list2.get(j).setValue(String.valueOf(m));
                                break;
                            }
                        }else{
                            if (list.get(i).getGenus1().equals(list2.get(j).getSpecialname())) {
                                flag = 0;
                                int m1 = Integer.parseInt(list2.get(j).getValue());
                                int m2 = Integer.parseInt(list.get(i).getValue());
                                int m = m1 + m2;
                                list2.get(j).setValue(String.valueOf(m));
                                break;
                            }
                        }
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
//        list = getAllFungi();

        if(country.equals("all")){//找市内所有地区
//            list = findbycity_p(list,city);
            list = findbycity_p(city);

        }else{//找单个县
//            list =findbycountry_p(list,country);
            list =findbycountry_p(country);

        }
        //根据蘑菇种类筛选
        if(!funginame.equals("allkind")) {
//            searchcontroller_new.only = 1;
            if (funginame.equals("30")) {
                list = find30Fungi(list);
            } else {
                list = findbyfungusname_p(list, funginame);
            }

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
