package com.scj.demo.dubbo.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlProviderGenericMain {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("provider-generic.xml");

        synchronized (XmlProviderGenericMain.class){
            XmlProviderGenericMain.class.wait();
        }

    }

}
