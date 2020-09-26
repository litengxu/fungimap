package com.guizhou.map.service.serviceimpl;

import com.guizhou.map.common.domain.baseconfig;
import com.guizhou.map.dao.idtoimgMapper;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.idtoimg;
import com.guizhou.map.service.IdtoimgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IdtoimgServiceImpl implements IdtoimgService {
    @Autowired
    private idtoimgMapper idtoimgMapper;
//    private String imgurl = baseconfig.getImgurl();
    private String imgurl = "http://gzbdi-yunshu.oss-cn-beijing.aliyuncs.com/";

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


    public Object createImgJson(List<idtoimg> list3, fungus fungus, String name) {
        StringBuffer sb = new StringBuffer();
        int count = 0;
//        String discoverer ="发现者:"+fungus.getShorttext1568085486500();
        String jie = "界："+fungus.getShorttext1569728158252();
        String men = "门："+ fungus.getShorttext1569728170780();
        String gang = "纲："+fungus.getShorttext1569728174540();
        String mu = "目："+fungus.getShorttext1569728177604();
        String ke = "科：" +fungus.getShorttext1567761446290();
        String shu = "属：" +fungus.getShorttext1567761455594();
//        String city = "所在市："+fungus.getCity();
//        String country = "所在县（区）："+fungus.getCountry();
//        String protection_zone = "所在保护区名称："+fungus.getShorttext1578362642317();
        String vegetation = "植被类型："+fungus.getShorttext1567761846314();
        String trees = "组成树种："+fungus.getShorttext1567761886218();
        String location = "位置："+fungus.getShorttext1567761904178();
        String aspect = "坡向："+fungus.getShorttext1567761983306();
        String ecological = "生态条件："+fungus.getShorttext1567762030586();
        String base_material = "基物："+fungus.getShorttext1567762120642();
        String interference = "干扰因素："+fungus.getShorttext1567762172409();
        String habit = "习性："+fungus.getShorttext1567762180513();
        String nutrition_type = "营养类型："+fungus.getShorttext1567762191345();
        String describe = "形态特征："+fungus.getLongtext1567762200825();
        String economic_value = "经济价值：" + fungus.getShorttext1569728465060();


        for (int i = 0; i < list3.size(); i++) {

            sb.append("{\"alt\":\"" + name +"\",\"pid\":" + count + ",\"src\":\""+ imgurl + list3.get(i).getRefid() + "\",\"thumb\":\""+imgurl+ list3.get(i).getRefid() + "\"}");
            count++;
            if (i != list3.size() - 1) {
                sb.append(",");
            }

        }

        String str = "{\"title\":\"" + name + "\",\"id\":1,\"start\":0,\"data\":[" + sb + "]}";
        Map<Object, Object> imgmap = new HashMap<>();
        imgmap.put("name",name);
        imgmap.put("img",str);
        imgmap.put("jie",jie);
        imgmap.put("men",men);
        imgmap.put("gang",gang);
        imgmap.put("mu",mu);
        imgmap.put("ke",ke);
        imgmap.put("shu",shu);
        imgmap.put("location",location);
        imgmap.put("aspect",aspect);
        imgmap.put("habit",habit);
        imgmap.put("ecological",ecological);
        imgmap.put("base_material",base_material);
        imgmap.put("interference",interference);
        imgmap.put("nutrition_type",nutrition_type);
        imgmap.put("vegetation",vegetation);
        imgmap.put("trees",trees);
//        imgmap.put("discoverer",discoverer);
        imgmap.put("describe",describe);
        imgmap.put("economic_value",economic_value);
        return imgmap;
    }
}
