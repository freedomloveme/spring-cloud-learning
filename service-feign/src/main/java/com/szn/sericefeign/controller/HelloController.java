package com.szn.sericefeign.controller;

import com.szn.sericefeign.serivce.SchedualServiceHi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private SchedualServiceHi schedualServiceHi;

    @RequestMapping("/hello")
    public String sayHello(@RequestParam String name){
        return schedualServiceHi.sayHiFromClientOne(name);
    }

}
