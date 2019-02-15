package com.scj.demo.dubbo.consumer.spi.impl;

import com.scj.demo.dubbo.consumer.spi.Person;
import org.apache.dubbo.common.URL;

public class Coder implements Person {

    @Override
    public void day(URL url) {
        System.out.println("写代码");
    }
}
