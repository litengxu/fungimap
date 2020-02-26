package com.guizhou.map.service;
import com.guizhou.map.domain.coordinatetoimgid;

import java.util.*;
public interface CoordinatetoimgidService {

    List<coordinatetoimgid> getall();

    String findObjectidbycoor(List<coordinatetoimgid> list,String longitude,String latitude);
}
