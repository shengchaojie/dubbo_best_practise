package com.scj.demo.dubbo.consumer.spi.impl;

import com.scj.demo.dubbo.consumer.spi.Person;
import com.scj.demo.dubbo.consumer.spi.Pet;
import lombok.Data;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;

@Data
public class Beauty implements Person {


    @Override
    public void day(URL url) {
        List<Pet> pets = ExtensionLoader.getExtensionLoader(Pet.class).getActivateExtension(url,"fish","cat");
        pets.stream().forEach((a)->a.day(url));
        System.out.println("铲屎");
    }
}
