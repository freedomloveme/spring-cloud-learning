package com.szn.sericefeign.serivce;

import com.szn.sericefeign.serivce.impl.SchedualServiceHiHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hi", fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

    @RequestMapping(value = "/hello")
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

}
