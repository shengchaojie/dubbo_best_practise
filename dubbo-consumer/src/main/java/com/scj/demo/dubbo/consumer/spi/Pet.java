package com.scj.demo.dubbo.consumer.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

@SPI("cat")
public interface Pet {

    void day(URL url);

}
