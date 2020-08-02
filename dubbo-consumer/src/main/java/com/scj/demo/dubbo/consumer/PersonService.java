package com.scj.demo.dubbo.consumer;

import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shengchaojie
 * @date 2020/8/2
 **/
@Service
public class PersonService {

    @Resource(name = "byeService")
    private GenericService genericService;


    public void sayBye(){
        Object result = genericService.$invoke(
                "bye",
                new String[]{"java.lang.String"},
                new Object[]{"1234"});
        System.out.println(result);
    }

}
