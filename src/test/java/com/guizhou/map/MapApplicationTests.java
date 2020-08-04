package com.guizhou.map;

import com.alibaba.fastjson.JSONObject;

import com.guizhou.map.common.domain.baseconfig;
import com.guizhou.map.dao.predictMapper;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.domain.fungusWithBLOBs;
import com.guizhou.map.domain.fungusExample;
import com.guizhou.map.domain.predict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import com.guizhou.map.dao.fungusMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapApplicationTests {
//	@Autowired
//	private baseconfig baseconfig;
	@Test
	public void contextLoads(){
		System.out.println(baseconfig.getImgurl());
	}

//
//	@Autowired
//	private fungusMapper fungusMapper;
//	@Autowired
//	private predictMapper predictMapper;
//	@Test
//	public void contextLoads() throws MalformedURLException {
//		InputStream inputStream = null;
//		InputStreamReader inputStreamReader = null;
//		BufferedReader bufferedReader = null;
//		List<fungus> list = fungusMapper.selectByExample(null);
//		for (int i = 0; i <list.size() ; i++) {
//            if(list.get(i).getShorttext1567760963892() == null || list.get(i).getShorttext1567760966363() == null){
//                continue;
//            }
//            if(list.get(i).getShorttext1567760963892().length() == 0 || list.get(i).getShorttext1567760966363().length() == 0){
//                continue;
//            }
//            if(list.get(i).getShorttext1567760963892().indexOf("°")!=-1 || list.get(i).getShorttext1567760966363().indexOf("°")!=-1){
//                continue;
//            }
//
//			double lo = Double.parseDouble(list.get(i).getShorttext1567760963892());//经度
//			double la = Double.parseDouble(list.get(i).getShorttext1567760966363());//纬度
////		http://api.map.baidu.com/reverse_geocoding/v3/?output=json&coordtype=BD09&pois=1&ak=Xg3x1KqgrWNnFox4cGPW739h0Bt6rab1&location=85.09099187480457,26.743833013375846
//			//需要纬度在前
//			String sUrl = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=Xg3x1KqgrWNnFox4cGPW739h0Bt6rab1&output=json&coordtype=BD09&location=" + la +  "," + lo;
//			URL url = new URL(sUrl);
//			try {
//				inputStream = url.openStream();
//				inputStreamReader = new InputStreamReader(inputStream,"utf-8");
//				bufferedReader = new BufferedReader(inputStreamReader);
//				String data = bufferedReader.readLine();
//				if (data != null) {
//					JSONObject object = JSONObject.parseObject(data);
//					String result = object.get("result").toString();
//					String[] res = result.split("\"");
////				System.out.println(object.get("addressCompont").toString());
//                    fungusWithBLOBs fungus = new fungusWithBLOBs();
////					//省
////					fungus.(res[67]);
//					//市
//					fungus.setCity(res[45]);
//					//县
//					fungus.setCountry(res[75]);
//					fungusExample fungusExample = new fungusExample();
//					fungusExample.Criteria criteria = fungusExample.createCriteria();
//					criteria.andIdEqualTo(list.get(i).getId());
//					fungusMapper.updateByExampleSelective(fungus,fungusExample);
//					System.out.println(i);
//				}
//				bufferedReader.close();
//				inputStreamReader.close();
//				inputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	@Test
//	public void selectcity(){
//		String s = "遵义市";
//		s = s.substring(0,s.length()-1);
//		System.out.println(s);
//		List<predict> list  = predictMapper.selectByCity(s);
//		for (predict l : list) {
//			System.out.println(l);
//		}
//	}
}
