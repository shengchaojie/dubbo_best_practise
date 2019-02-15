package com.scj.demo.dubbo.consumer.spi.impl;

import com.scj.demo.dubbo.consumer.spi.Person;
import org.apache.dubbo.common.URL;

public class SuperPowerWrapper implements Person {

    private Person person;

    public SuperPowerWrapper(Person person) {
        this.person =person;
    }

    @Override
    public void day(URL url) {
        System.out.println("好了你现在有超能力了");
        person.day(url);
    }
}
