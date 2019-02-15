package com.scj.demo.dubbo.consumer.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

@SPI("earth")
public interface World {

    void day(URL url);

}
