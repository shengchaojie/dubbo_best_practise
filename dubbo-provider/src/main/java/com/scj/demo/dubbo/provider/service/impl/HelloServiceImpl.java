package com.scj.demo.dubbo.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.scj.demo.dubbo.api.HelloService;
import com.scj.demo.dubbo.api.Result;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HelloServiceImpl implements HelloService,ByeService,GenericReturnValueService {

    Executor executor = Executors.newCachedThreadPool();

    @Override
    public Result<String> hello(String name) {
        return Result.ofSuccess("hello"+"12345");
    }

    public Result<String> exception(String name)  {
        throw new RuntimeException("1234");
        //return Result.ofSuccess("hello"+name);
    }

    @Override
    public Result<String> timeout(String name) {
        try {
            Thread.sleep(1000* 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("timeout invoked");
        return Result.ofSuccess("hello"+name);
    }

    @Override
    public Result<List<String>> testPage1(Integer page, Integer pageSize) {
        try {
            Thread.sleep(1000* 4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ofSuccess(Lists.newArrayList("scj","123","356"));
    }

    @Override
    public CompletableFuture<Result<List<String>>> testPage2(Integer page, Integer pageSize) {

        //注意这边不过不配置executor 连续的testPage2调用 会在默认线程池里面串行
        return CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000* 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Result.ofSuccess(Lists.newArrayList("scj","123","356"));
        },executor);
    }

    @Override
    public String helloWorld(String name) {
        return "hello,world" + name;
    }

    @Override
    public String bye(String name) {
        return "bye"+ name;
    }

    @Override
    public String bye(String name, Long age, Date date) {
        return "hello"+name+age+date;
    }

    @Override
    public String bye(Person person) {
        return "hello"+person;
    }

    @Override
    public String bye(Map<String, Object> person) {
        return "hello" + JSON.toJSONString(person);
    }

    @Override
    public String bye(List<String> names) {
        return "hello" + Joiner.on(",").join(names);
    }

    @Override
    public String bye(String[] names) {
        return "hello" + Arrays.toString(names);
    }

    @Override
    public String byePersons(List<Person> persons) {
        return "hello" + Joiner.on(",").join(persons);
    }

    @Override
    public String byePersons(Person[] persons) {
        return "hello" + Arrays.toString(persons);
    }

    @Override
    public String returnString() {
        return "scj";
    }

    @Override
    public Long returnLong() {
        return 12L;
    }

    @Override
    public long returnPrimitiveLong() {
        return 12;
    }

    @Override
    public List<String> returnListString() {
        return Lists.newArrayList("123","456");
    }

    @Override
    public String[] returnArrayString() {
        return new String[]{"jus","t"};
    }

    @Override
    public Set<String> returnSetString() {
        return Sets.newHashSet("1","5","555");
    }

    @Override
    public Person returnPojo() {
        Person person = new Person();
        person.setAge(111L);
        person.setBirth(new Date());
        person.setName("scj111");
        return person;
    }

    @Override
    public List<Person> returnListPerson() {
        Person person = new Person();
        person.setAge(111L);
        person.setBirth(new Date());
        person.setName("scj111");
        return Lists.newArrayList(person,person,person);
    }
}
