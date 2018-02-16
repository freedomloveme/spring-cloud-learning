package com.szn.sericefeign.serivce.impl;

import com.szn.sericefeign.serivce.SchedualServiceHi;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements SchedualServiceHi{
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry, " + name;
    }
}
