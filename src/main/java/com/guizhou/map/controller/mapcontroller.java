package com.guizhou.map.controller;

import com.guizhou.map.domain.Fungi;
import com.guizhou.map.domain.fungus;
import com.guizhou.map.service.FungiService;
import com.guizhou.map.service.FungusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class mapcontroller {

    @Autowired
    private FungiService fungiService;

    @Autowired
    private FungusService fungusService;

    //主页
    @RequestMapping("/map")
    public String getmap(){

        return "index2";
    }

    //主页ajax取数据后台接口
    @RequestMapping("/list")
    @ResponseBody
    public ModelAndView getlist(){
        List<Fungi> list = fungiService.getAllFungi();
        ModelAndView modelAndView =  new ModelAndView();
        modelAndView.addObject("list",list);
        modelAndView.setViewName("list");
        return modelAndView;
    }
    //测试
    @RequestMapping("/list2")
    @ResponseBody
    public Object getdata(){
        List<Fungi> list = fungiService.getAllFungi();

        return list;
    }
    @RequestMapping("/insert")
    public Object insertdata(){
        List<fungus> list = fungusService.getAllFungi();
        fungusService.updateByid(3);
        System.out.println("ok");
        return 1;
    }
}
