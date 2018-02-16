package com.szn.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.szn.service.HelloService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "hiError")
    public String helloSerivce(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    @Override
    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }

}
