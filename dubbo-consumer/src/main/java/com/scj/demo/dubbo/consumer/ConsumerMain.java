package com.scj.demo.dubbo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.scj.demo.dubbo.api.HelloService;

public class ConsumerMain {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-consumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<HelloService> referenceConfig = new ReferenceConfig<HelloService>();
        referenceConfig.setInterface(HelloService.class);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        HelloService helloService = referenceConfig.get();
        for(int i =0 ;i <10;i++)
        System.out.println(helloService.exception("scj"));
    }

}
