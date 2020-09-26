package com.guizhou.map.service.serviceimpl;

import com.guizhou.map.common.domain.baseconfig;
import com.guizhou.map.common.staticvariable;
import com.guizhou.map.controller.searchcontroller_new;
import com.guizhou.map.dao.fungusMapper;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.fungusExample;
import com.guizhou.map.service.FungusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@CacheConfig(cacheNames="fungi")
public class FungusServiceImpl implements FungusService{

    @Autowired
    private fungusMapper fungusMapper;

//    private  int size = baseconfig.getKuaisize();
//    private double longitudemax = Double.parseDouble(baseconfig.getLongitudemax());
//    private double longitudemin = Double.parseDouble(baseconfig.getLongitudemin());
//    private double latitudemax = Double.parseDouble(baseconfig.getLatitudemax());
//    private double latitudemin = Double.parseDouble(baseconfig.getLatitudemin());
    private double longitudemax = 109.672039;
    private double longitudemin = 103.299969;
    private double latitudemax = 29.443776;
    private double latitudemin = 24.191224;
    private  int size = 40;

    @Override
    public List<fungus> selectBySpecificname(String name) {
        fungusExample example = new fungusExample();
        fungusExample.Criteria criteria = example.createCriteria();
        criteria.andShorttext1567761455594EqualTo(name);
        List<fungus> list = fungusMapper.selectByExample(example);
        return list;
    }

    @Override
    public fungus findbyobjectid(String objectid) {
        fungusExample example = new fungusExample();
        fungusExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(objectid);
        return fungusMapper.selectByExample(example).get(0);

    }
    @Override
    public Map listtoarrmap(List<fungus> list) {
       int  n =list.size();
       //id数组
       String idarr[] = new String[n];
       //菌种名字
        String name[] = new String[n];
        //属
        String shu[] = new String[n];
        //经度
        String longitude[] = new String[n];
        //纬度
        String latitude[] = new String[n];
        //数量
        int value[] = new int[n];
        int j = 0;
        for(int i=0;i< n;i++){
            if(list.get(j).getShorttext1567760963892().length() == 0 || list.get(j).getShorttext1567760966363().length() == 0){
                n--;
                i--;
                j++;
                continue;
            }
            idarr[i] = list.get(j).getId();
            name[i] = list.get(j).getShorttext1567761353834();
            shu[i] = list.get(j).getShorttext1567761455594();
            longitude[i] = list.get(j).getShorttext1567760963892();
            latitude[i] = list.get(j).getShorttext1567760966363();
            value[i] = list.get(j).getValue();
            j++;
        }

        Map<Object,Object> maplist = new HashMap<>();
        maplist.put("idarr",idarr);
        maplist.put("namearr",name);
        maplist.put("shuarr",shu);
        maplist.put("longitudearr",longitude);
        maplist.put("latitudearr",latitude);
        maplist.put("valuearr",value);
       return maplist;
    }

    //    private static final Map<String,String> citymap;
//    static {
//        citymap = new HashMap<String,String>();
//        citymap.put("贵阳地区","贵阳市");
//        citymap.put("六盘水地区","六盘水市");
//        citymap.put("遵义市","遵义市");
//        citymap.put("安顺地区","安顺市");
//        citymap.put("毕节地区","毕节市");
//        citymap.put("铜仁地区","铜仁市");
//        citymap.put("黔西南布依族苗族自治州","黔西南布依族苗族自治州");
//        citymap.put("黔东南苗族侗族自治州","黔东南苗族侗族自治州");
//        citymap.put("黔南布依族苗族自治州","黔南布依族苗族自治州");
//
//        List<String> list = new ArrayList<>();
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//        list.add("紫花菌");
//    }
   // @Cacheable(key="'allFungi'")
    public List<fungus> getAllFungi() {
//        return fungusMapper.selectByExample(null);
        return fungusMapper.selectAll();
    }

    @Override
    public List<fungus> find30Fungi(List<fungus> list) {
        List<fungus> newlist = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            for(int j = 0;j<staticvariable.list30.size();j++){
                if(list.get(i).getShorttext1567761353834().equals(staticvariable.list30.get(j))){
                    newlist.add(list.get(i));
                }
            }
        }
        return newlist;
    }

    //@Cacheable(key = "#genus")
    public List<fungus> selectByname(String genus) {
        fungusExample example = new fungusExample();
        fungusExample.Criteria criteria = example.createCriteria();
        criteria.andShorttext1567761455594EqualTo(genus);
        return fungusMapper.selectByExample(example);
    }
    //演示用，随机更新图图片地址
//    public List<fungus> updateByid(int id){
//        fungusExample example = new fungusExample();
//        fungusExample.Criteria criteria = example.createCriteria();
//        criteria.andIdEqualTo(String.valueOf(id));
//        fungus fungus = new fungus();
//        fungus.setImg("/images/"+(int)(Math.random() * 25 + 1)+".jpg");
//        fungusMapper.updateByExampleSelective(fungus,example);
//        return null;
//    }

    //利用set，按姓名去重，来获得全部共多少类菌种
//    getShorttext1567761455594 genus1
//    shorttext1567761455594

    public List<fungus> removeDuplicateCase(List<fungus> list){
        Set<fungus> set = new TreeSet<>(new Comparator<fungus>() {
            @Override
            public int compare(fungus o1, fungus o2) {
                return o1.getShorttext1567761455594().compareTo(o2.getShorttext1567761455594());
            }
        });
        set.addAll(list);
        List<fungus> list2 = new ArrayList<>(set);
//        for (int i = 0; i <list2.size() ; i++) {
//            if(list2.get(i).getGenus1() == " " || list2.get(i).getGenus1() == null){
//                list2.remove(i);
//            }
//        }
        return list2;
    }
    //获得一共有多少种，其中当属为空时，用名字代替
    public int removeDuplicateCasemNonull(List<fungus> list){
        List<fungus> list1 = new ArrayList<>();
        List<fungus> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getShorttext1567761455594().length() == 0){
                list1.add(list.get(i)); //只含有名字
                list.remove(i);
            }
        }
        Set<fungus> set = new TreeSet<>(new Comparator<fungus>() {
            @Override
            public int compare(fungus o1, fungus o2) {
                return o1.getShorttext1567761353834().compareTo(o2.getShorttext1567761353834());
            }
        });
        set.addAll(list1);
        list2 = new ArrayList<>(set);
        return (list2.size()+removeDuplicateCase(list).size());
    }

    //把list部分字段的值放到新的二维数组中
    public double[][] listconverttoarr(List<fungus> list) {

        double [][]arr2 = new double[list.size()][3];
        for(int i = 0;i < list.size();i++){
            //getShorttext1567760963892() long 108 shorttext1567760963892
            //getShorttext1567760966363() latitude 20 shorttext1567760966363
            if(list.get(i).getShorttext1567760963892() == null || list.get(i).getShorttext1567760966363() == null){
                continue;
            }
            if(list.get(i).getShorttext1567760963892().length() == 0 || list.get(i).getShorttext1567760966363().length() == 0){
                continue;
            }
            if(list.get(i).getShorttext1567760963892().indexOf("°")!=-1 || list.get(i).getShorttext1567760966363().indexOf("°")!=-1){
                continue;
            }
            arr2[i][0] = Double.parseDouble(list.get(i).getShorttext1567760963892());
            arr2[i][1] =  Double.parseDouble(list.get(i).getShorttext1567760966363());
            arr2[i][2] = list.get(i).getValue();
//            arr2[i][2] = ;
        }
        return arr2;
    }

    //根据行和列的值，来判断有多少菌种的坐标落到这个分块中
    public List<fungus> findinthisfungi(List<fungus> list,int column,int row){
        List<fungus> list2 = new ArrayList<>();
        //把十万条数据放到arr中，相同方格的数目要相加
        //float y = (float) ((109.672039 - 103.299969)/100);
        float y = (float) ((longitudemax - longitudemin)/size);
        //真正使用时要用上面这句
        //float x = (float) ((29.443776 - 24.191224)/100);
        float x = (float) ((latitudemax - latitudemin)/size);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getShorttext1567760963892() == null || list.get(i).getShorttext1567760966363() == null){
                continue;
            }
            if(list.get(i).getShorttext1567760963892().length() == 0 || list.get(i).getShorttext1567760966363().length() == 0){
                continue;
            }
            if(list.get(i).getShorttext1567760963892().indexOf("°")!=-1 || list.get(i).getShorttext1567760966363().indexOf("°")!=-1){
                continue;
            }
            int g1 =  (int) ((float)( Double.parseDouble(list.get(i).getShorttext1567760966363()) - latitudemin)/x);
            //double g1 = (Double.parseDouble(list.get(i).getLatitude()) - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(Double.parseDouble(list.get(i).getShorttext1567760963892() )- longitudemin)/y);
            if(g1 == row && g2 == column) {
                int flag = 1;
                for(int j=0;j<list2.size();j++){
                    if(list.get(i).getShorttext1567761455594().length() != 0){//当属为空时
                        if(list2.get(j).getShorttext1567761455594().length() != 0) {
                            if (list.get(i).getShorttext1567761455594().equals(list2.get(j).getShorttext1567761455594())) {
                                flag = 0;
                                int m1 = list2.get(j).getValue();
                                int m2 = list.get(i).getValue();
                                list2.get(j).setValue(m1 + m2);
                                break;
                            }
                        }else{
                            if (list.get(i).getShorttext1567761455594().equals(list2.get(j).getShorttext1567761353834())) {
                                flag = 0;
                                int m1 = list2.get(j).getValue();
                                int m2 = list.get(i).getValue();
                                list2.get(j).setValue(m1 + m2);
                                break;
                            }
                        }


                    }
                    else {

                        if(list2.get(j).getShorttext1567761455594().length() == 0){//当缓存结果中的属也为空时
                            //比较菌种名称是否相同
                            if(list.get(i).getShorttext1567761353834().equals(list2.get(j).getShorttext1567761353834())){
                                flag = 0;
                                int m1 = list2.get(j).getValue();
                                int m2 = list.get(i).getValue();
                                list2.get(j).setValue(m1 + m2);
                                break;
                            }
                        }else{
                            if(list.get(i).getShorttext1567761353834().equals(list2.get(j).getShorttext1567761455594())){
                                flag = 0;
                                int m1 = list2.get(j).getValue();
                                int m2 = list.get(i).getValue();
                                list2.get(j).setValue(m1 + m2);
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

    //填充块状图显示需要的数组
    public StringBuffer fillarr(double[][] arr2) {

        int [][]arr = new int[size*size][3];
        int k=0;
        for(int i = 0;i<arr.length;i++) {
            arr[i][0] = i%size;
            arr[i][1] = k;
            if((i+1)%size==0){
                k++;
            }
            arr[i][2] = 0;
        }
        //把十万条数据放到arr中，相同方格的数目要相加
        float y = (float) ((longitudemax - longitudemin)/size);
        //     float x = (float) ((29.376113 - 24.631894)/100);
        //真正使用时要用上面这句
        float x = (float) ((latitudemax - latitudemin)/size);
        for(int i=0;i<arr2.length;i++) {
            //列方向第g1块行 对应row
            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
            int g1 =  (int) ((float)(arr2[i][1] - latitudemin)/x);
            //double g1 = (arr2[i][0] - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(arr2[i][0] - longitudemin)/y);
            if(g1 < 0 || g2 < 0){
                continue;
            }
            //double g2 = (arr2[i][1] - 103.622947)/y;
            int c = (int) (g1*size+g2);
            if(c<0 || c >=size*size){
                continue;
            }

            arr[c][2] = (int) (arr[c][2] + arr2[i][2]);

        }
        StringBuffer sb  = new StringBuffer();
        //sb.append("[");

        for(int i = 0;i<arr.length;i++){
            //string  =string + "[";
            // sb.append("[");
            sb.append(arr[i][0]);
            sb.append(",");
            sb.append(arr[i][1]);
            sb.append(",");
            sb.append(arr[i][2]);
            if(i!=arr.length-1){
                sb.append(";");
            }
        }
        return sb;
    }
    //根据市模糊查找
    @Override
    public List<fungus> findbycity(String city) {
        List<fungus> list2 = new ArrayList<>();
        //把city都转化为市州结尾，设置的原因是之前后台的数据有铜仁市和铜仁地区两种
        //也可以使用模糊匹配查询
        //预测中都是按照地区来对应的
        city = staticvariable.citymap.get(city);
        city = city.substring(0,city.length()-1);

        list2 =fungusMapper.selectByCity(city);

        return list2;
    }
    //根据县模糊查找
    @Override
    public List<fungus> findbycountry(String country) {
        List<fungus> list2 = new ArrayList<>();
        country = country.substring(0,country.length()-1);
        list2 = fungusMapper.selectByCountry(country);
        return list2;
    }
    public List<fungus> findbycity(List<fungus> list, String city) {
        List<fungus> list2 = new ArrayList<>();
        //把city都转化为市州结尾，设置的原因是之前后台的数据有铜仁市和铜仁地区两种
        //也可以使用模糊匹配查询
        //预测中都是按照地区来对应的
        city = staticvariable.citymap.get(city);
        if(city.equals("铜仁市")){
            for(int i = 0 ;i < list.size();i++){
                if(list.get(i).getCity().equals(city)){
                    list2.add(list.get(i));
                }
                if(list.get(i).getCity().equals("铜仁地区")){
                    list2.add(list.get(i));
                }
            }
            return list2;
        }else {
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getCity() == null){
                    continue;
                }
                if(list.get(i).getCity().length() == 0){
                    continue;
                }
                if (list.get(i).getCity().equals(city)) {
                    list2.add(list.get(i));
                }
            }
        }
        return list2;
    }
    //根据县查找
    public List<fungus> findbycountry(List<fungus> list, String country) {
        List<fungus> list2 = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getCity() == null || list.get(i).getCountry() == null){
                continue;
            }
            if(list.get(i).getCountry().length() == 0 || list.get(i).getCountry().length() == 0){
                continue;
            }
            if (list.get(i).getCountry().equals(country)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }
    //根据属查找
    public List<fungus> findbyfungusname(List<fungus> list, String fungusname) {
        List<fungus> list2 = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getShorttext1567761455594() == null){
                continue;
            }
            if(list.get(i).getShorttext1567761455594().length() == 0){
                continue;
            }
            if(list.get(i).getShorttext1567761455594().equals(fungusname)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }

    //根据县市种类筛选数据
    public List<fungus> selectdata(String city, String country,String funginame) {
        List<fungus> list =  new ArrayList<>();
        list = getAllFungi();

        if(country.equals("all")){//找市内所有地区
//            list = findbycity(list,city);
            list = findbycity(city);
            findbycity(city);
        }else{//找单个县
//            list =findbycountry(list,country);
            list =findbycountry(country);
        }
        //根据蘑菇种类筛选
        if(!funginame.equals("allkind")) {//不是查询所有种类
//            searchcontroller_new.only = 1;
            if (funginame.equals("30")) {
                list = find30Fungi(list);
            } else {
                list = findbyfungusname(list, funginame);
            }
        }

        return list;
    }

}
