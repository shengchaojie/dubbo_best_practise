package com.scj.demo.dubbo.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlProviderMain {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("provider.xml");

        synchronized (XmlProviderMain.class){
            XmlProviderMain.class.wait();
        }

    }

}
