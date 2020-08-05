package com.scj.demo.dubbo.provider.service.impl;

import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @author shengchaojie
 * @date 2020/8/5
 **/
public class GenericServiceImpl implements GenericService{

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        if("helloWorld".equals(method)){
            return "hello" + args[0];
        }
        return "hello";
    }
}
