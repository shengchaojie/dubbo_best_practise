package com.scj.demo.dubbo.provider.service.impl;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shengchaojie
 * @date 2020/8/2
 **/
public interface ByeService {

    String bye(String name);

    String bye(String name, Long age, Date date);

    String bye(Person person);

    String bye(Map<String,Object> person);

    String bye(List<String> names);

    String bye(String[] names);

    String byePersons(List<Person> persons);

    String byePersons(Person[] persons);

    @Data
    public static class Person{
        private String name;

        private Long age;

        private Date birth;
    }

    public static void main(String[] args) {
        System.out.println(Person.class.getName());
    }

}
