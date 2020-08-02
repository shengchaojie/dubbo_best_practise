package com.scj.demo.dubbo.provider.service.impl;

import java.util.List;
import java.util.Set;

/**
 * @author shengchaojie
 * @date 2020/8/2
 **/
public interface GenericReturnValueService {

    String returnString();

    Long returnLong();

    long returnPrimitiveLong();

    List<String> returnListString();

    String[] returnArrayString();

    Set<String> returnSetString();

    ByeService.Person returnPojo();

    List<ByeService.Person> returnListPerson();

}
