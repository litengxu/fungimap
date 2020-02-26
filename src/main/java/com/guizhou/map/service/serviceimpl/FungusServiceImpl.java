package com.guizhou.map.service.serviceimpl;

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

    private  int size = 40;
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
   // @Cacheable(key="'allFungi'")
    public List<fungus> getAllFungi() {
        return fungusMapper.selectByExample(null);
    }

    //@Cacheable(key = "#genus")
    public List<fungus> selectByname(String genus) {
        fungusExample example = new fungusExample();
        fungusExample.Criteria criteria = example.createCriteria();
        criteria.andGenus1EqualTo(genus);
        return fungusMapper.selectByExample(example);
    }
    //演示用，随机更新图图片地址
    public List<fungus> updateByid(int id){
        fungusExample example = new fungusExample();
        fungusExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        fungus fungus = new fungus();
        fungus.setImg("/images/"+(int)(Math.random() * 25 + 1)+".jpg");
        fungusMapper.updateByExampleSelective(fungus,example);
        return null;
    }

    //利用set，按姓名去重，来获得全部共多少类菌种
    public List<fungus> removeDuplicateCase(List<fungus> list){
        Set<fungus> set = new TreeSet<>(new Comparator<fungus>() {
            @Override
            public int compare(fungus o1, fungus o2) {
                return o1.getGenus1().compareTo(o2.getGenus1());
            }
        });
        set.addAll(list);
        return new ArrayList<>(set);
    }

    //把list部分字段的值放到新的二维数组中
    public double[][] listconverttoarr(List<fungus> list) {
        double [][]arr2 = new double[list.size()][3];
        for(int i = 0;i < list.size();i++){
            arr2[i][0] = list.get(i).getLongitude();
            arr2[i][1] = list.get(i).getLatitude();
            arr2[i][2] = list.get(i).getValue();
        }
        return arr2;
    }

    //根据行和列的值，来判断有多少菌种的坐标落到这个分块中
    public List<fungus> findinthisfungi(List<fungus> list,int column,int row){
        List<fungus> list2 = new ArrayList<>();
        //把十万条数据放到arr中，相同方格的数目要相加
        //float y = (float) ((109.672039 - 103.299969)/100);
        float y = (float) ((109.672039 - 103.299969)/size);
        //真正使用时要用上面这句
        //float x = (float) ((29.443776 - 24.191224)/100);
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
        float y = (float) ((109.672039 - 103.299969)/size);
        //     float x = (float) ((29.376113 - 24.631894)/100);
        //真正使用时要用上面这句
        float x = (float) ((29.443776 - 24.191224)/size);
        for(int i=0;i<arr2.length;i++) {
            //列方向第g1块行 对应row
            //int g1 =  (int) ((float)(arr2[i][0] - 24.631894)/x);
            int g1 =  (int) ((float)(arr2[i][1] - 24.191224)/x);
            //double g1 = (arr2[i][0] - 24.631894)/x;
            //行方向第g2块 对应column
            int g2 =  (int) ((float)(arr2[i][0] - 103.299969)/y);
            if(g1 < 0 || g2 < 0){
                continue;
            }
            //double g2 = (arr2[i][1] - 103.622947)/y;
            int c = (int) (g1*size+g2);
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
    //根据市查找
    public List<fungus> findbycity(List<fungus> list, String city) {
        List<fungus> list2 = new ArrayList<>();
        if(city.equals("铜仁地区")){
            for(int i = 0 ;i < list.size();i++){
                if(list.get(i).getCity().equals(city)){
                    list2.add(list.get(i));
                }
                if(list.get(i).getCity().equals("铜仁市")){
                    list2.add(list.get(i));
                }
            }
            return list2;
        }
        for(int i = 0 ;i < list.size();i++){
            if(list.get(i).getCity().equals(city)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }
    //根据县查找
    public List<fungus> findbycountry(List<fungus> list, String country) {
        List<fungus> list2 = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            if (list.get(i).getCounty().equals(country)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }
    //根据属查找
    public List<fungus> findbyfungusname(List<fungus> list, String fungusname) {
        List<fungus> list2 = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getGenus1().equals(fungusname)){
                list2.add(list.get(i));
            }
        }
        return list2;
    }

    //根据县市筛选数据
    public List<fungus> selectdata(String city, String country,String funginame) {
        List<fungus> list =  new ArrayList<>();
        list = getAllFungi();

        if(country.equals("all")){//找市内所有地区
            list = findbycity(list,city);

            searchcontroller_new.cityCountry = citymap.get(city);
            searchcontroller_new.zoom = 9;
        }else{//找单个县
            list =findbycountry(list,country);
            searchcontroller_new.cityCountry = country;
            searchcontroller_new.zoom  = 11;
        }
        //根据蘑菇种类筛选
        if(!funginame.equals("allkind")) {
            searchcontroller_new.only = 1;
            list = findbyfungusname(list, funginame);
        }
        return list;
    }

}
