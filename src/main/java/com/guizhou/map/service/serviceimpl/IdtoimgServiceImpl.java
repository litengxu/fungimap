package com.guizhou.map.service.serviceimpl;

import com.guizhou.map.dao.idtoimgMapper;
import com.guizhou.map.domain.idtoimg;
import com.guizhou.map.service.IdtoimgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IdtoimgServiceImpl implements IdtoimgService {
    @Autowired
    private idtoimgMapper idtoimgMapper;

    @Override
    public List<idtoimg> getall() {
        return idtoimgMapper.selectByExample(null);
    }

    public List<idtoimg> findbybizObjectid(List<idtoimg> list,String bizObjectid) {
        List<idtoimg> list2 = new ArrayList<>();

        list.forEach(data->{
            if(data.getBizobjectid().equals(bizObjectid)) {
                list2.add(data);
            }
        });
        return list2;
    }

    public String createImgJson(List<idtoimg> list3,String name) {
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (int i = 0; i < list3.size(); i++) {
            sb.append("{\"alt\":\"" + name + "\",\"pid\":" + count + ",\"src\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\",\"thumb\":\"https://yunshu-gzbdi.oss-cn-beijing.aliyuncs.com/" + list3.get(i).getRefid() + "\"}");
            count++;
            if (i != list3.size() - 1) {
                sb.append(",");
            }
        }
        String str = "{\"title\":\"" + name + "\",\"id\":1,\"start\":0,\"data\":[" + sb + "]}";
        return str;
    }
}
