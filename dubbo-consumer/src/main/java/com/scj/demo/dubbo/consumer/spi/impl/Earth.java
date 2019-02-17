package com.scj.demo.dubbo.consumer.spi.impl;

import com.scj.demo.dubbo.consumer.spi.Person;
import com.scj.demo.dubbo.consumer.spi.World;
import lombok.Data;
import org.apache.dubbo.common.URL;

@Data
public class Earth implements World {

    //扩展点自动注入
    //并且注入的为扩展点适配类
    private Person person;

    @Override
    public void day(URL url) {
        System.out.println("地球美好的一天开始");
        person.day(url);
        System.out.println("地球美好的一天结束");
    }
}
