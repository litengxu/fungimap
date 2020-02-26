package com.guizhou.map.service;

import com.guizhou.map.domain.idtoimg;

import java.util.*;
public interface IdtoimgService {

    List<idtoimg> getall();//查询所有
    List<idtoimg> findbybizObjectid(List<idtoimg> list,String bizObjectid);//根据您bizObjectid查询
    String createImgJson(List<idtoimg> list3,String name);//建立前端需要格式的json串
}
