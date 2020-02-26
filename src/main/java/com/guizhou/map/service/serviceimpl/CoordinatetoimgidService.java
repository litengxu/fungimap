package com.guizhou.map.service.serviceimpl;
import com.guizhou.map.dao.coordinatetoimgidMapper;
import com.guizhou.map.domain.coordinatetoimgid;
import com.guizhou.map.domain.idtoimg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CoordinatetoimgidService implements com.guizhou.map.service.CoordinatetoimgidService {

    @Autowired
    private coordinatetoimgidMapper coordinatetoimgMapper;
    @Override
    public List<coordinatetoimgid> getall() {
        return coordinatetoimgMapper.selectByExample(null);
    }


    public String findObjectidbycoor(List<coordinatetoimgid> list, String longitude, String latitude) {

        for(int i =0; i < list.size();i++){
            if(list.get(i).getLatitude().equals(latitude) && list.get(i).getLongitude().equals(longitude)){

                return list.get(i).getId();

            }
        }
        return null;
    }
}
