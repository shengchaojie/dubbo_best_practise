package com.scj.demo.dubbo.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.scj.demo.dubbo.api.HelloService;
import com.scj.demo.dubbo.provider.service.impl.HelloServiceImpl;

public class ProviderMain {

    public static void main(String[] args) throws InterruptedException {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-provider");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(21111);

        ServiceConfig<HelloService> serviceConfig = new ServiceConfig<HelloService>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setInterface(HelloService.class);
        serviceConfig.setRef(new HelloServiceImpl());
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.export();

        synchronized (ProviderMain.class){
            ProviderMain.class.wait();
        }
    }

}
