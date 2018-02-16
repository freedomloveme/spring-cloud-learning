package com.szn.controller;

import com.szn.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private HelloService helloService;

    @RequestMapping(value = "/hello")
    public String hi(@RequestParam String name){
        return helloService.helloSerivce(name);
    }

}
