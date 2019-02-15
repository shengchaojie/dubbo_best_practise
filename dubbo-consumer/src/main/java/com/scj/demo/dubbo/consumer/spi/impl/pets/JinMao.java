package com.scj.demo.dubbo.consumer.spi.impl.pets;

import com.scj.demo.dubbo.consumer.spi.Pet;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

@Activate(group = "dog")
public class JinMao implements Pet {
    @Override
    public void day(URL url) {
        System.out.println("金毛发呆");
    }
}
