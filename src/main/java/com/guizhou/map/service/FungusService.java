package com.guizhou.map.service;


import com.guizhou.map.domain.fungus;

import java.util.*;

public interface FungusService {
    Map listtoarrmap(List<fungus> list);
    List<fungus> getAllFungi();
    List<fungus> find30Fungi(List<fungus> list);
    List<fungus> selectByname(String genus);
//    List<fungus> updateByid(int id);
    List<fungus> removeDuplicateCase(List<fungus> list);
    int removeDuplicateCasemNonull(List<fungus> list);
    //模糊查询城市和县
    List<fungus> findbycity(String city);
    List<fungus> findbycountry(String country);
    double[][] listconverttoarr(List<fungus> list);
    List<fungus> findinthisfungi(List<fungus> list,int column,int row);
    StringBuffer fillarr(double [][]arr2);
    List<fungus> findbycity(List<fungus> list,String city );
    List<fungus> findbycountry(List<fungus> list ,String country);
    List<fungus> findbyfungusname(List<fungus> list,String fungusname);
    List<fungus> selectdata(String city,String country,String funginame);

}
