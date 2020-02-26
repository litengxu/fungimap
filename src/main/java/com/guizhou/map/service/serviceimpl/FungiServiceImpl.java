package com.guizhou.map.service.serviceimpl;

import com.guizhou.map.dao.FungiMapper;
import com.guizhou.map.domain.Fungi;
import com.guizhou.map.domain.FungiExample;
import com.guizhou.map.service.FungiService;
import org.springframework.beans.factory.annotation.Autowired;
import com.guizhou.map.configure.RedisConfig.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig(cacheNames="fungi")
public class FungiServiceImpl implements FungiService {

    @Autowired
    private FungiMapper fungiMapper;
//    @Autowired
//    private RedisTemplate<Object,Object> redisTemplate;
//
//    @Override
//    public List<Fungi> getAllFungi() {
//        //key的编码序列化，也可以自己定义重写redisTemplate
//        RedisSerializer redisSerializer =  new StringRedisSerializer();
//        redisTemplate.setKeySerializer(redisSerializer);
//        List<Fungi> fungiList = (List<Fungi>)redisTemplate.opsForValue().get("allFungi");
//        //双重检测防止穿透
//        if(null == fungiList){
//            synchronized(this){
//                fungiList = (List<Fungi>)redisTemplate.opsForValue().get("allFungi");
//                if(null == fungiList){
//                    fungiList = fungiMapper.selectByExample(null);
//                    System.out.println("list是"+ fungiList);
//                    redisTemplate.opsForValue().set("allFungi",fungiList);
//                    System.out.println("数据库输出-----------");
//                }else{
//                    System.out.println("缓存输出-----------------");
//                }
//            }
//        }else{
//            System.out.println("缓存输出----------------");
//        }
//        return fungiList;
//    }

        //return fungiMapper.findAll();

    //@Cacheable(key="'allFungi'")
    public  List<Fungi> getAllFungi() {
        //System.out.println("执行这里，说明缓存中读取不到数据，直接读取数据库....");
        return fungiMapper.selectByExample(null);
    }
    //@Cacheable(key = "#name")
    public List<Fungi> selectByname(String name) {
        //System.out.println("执行这里，说明缓存中读取不到数据，直接读取数据库....byname");
        FungiExample example = new FungiExample();
        FungiExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        return fungiMapper.selectByExample(example);
    }




}

