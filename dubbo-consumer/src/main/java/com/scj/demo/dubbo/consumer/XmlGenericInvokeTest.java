package com.scj.demo.dubbo.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shengchaojie
 * @date 2020-06-05
 **/
public class XmlGenericInvokeTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer-generic.xml");
        PersonService personService = applicationContext.getBean(PersonService.class);

        personService.sayBye();

    }

}
