package com.scj.demo.dubbo.consumer.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("beauty")
public interface Person {

    @Adaptive("person")
    void day(URL url);

}
