package com.szn.servicehello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
public class ServiceHelloApplication {

	@Value("${server.port}")
	private String port;

	@RequestMapping("/hello")
	public String home(@RequestParam String name) {
		return "hi " + name + ", i am from port: " + port;
	}
}
