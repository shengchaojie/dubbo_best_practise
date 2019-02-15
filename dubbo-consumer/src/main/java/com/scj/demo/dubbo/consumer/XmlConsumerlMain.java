package com.scj.demo.dubbo.consumer;

import com.google.common.collect.Lists;
import com.scj.demo.dubbo.api.HelloService;
import org.apache.dubbo.common.json.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class XmlConsumerlMain {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
        final HelloService helloService = applicationContext.getBean(HelloService.class);
        List<String> data = new ArrayList<>();

        //测试异常
        //测试mock create -e /dubbo/com.scj.demo.dubbo.api.HelloService/configurators/override%3a%2f%2f192.168.28.249%3a21111%2fcom.scj.demo.dubbo.api.HelloService%3fexception.mock%3dforce%3areturn+*%7b%22data%22%3a%22scjhello%22%7d%26category%3dconfigurators 1
        //测试telnet
        System.out.println(helloService.exception("123").getMessage());

        //测试超时重试
        helloService.timeout("123455");

        //异步调用测试
        //多个线程阻塞
        CompletableFuture[] cfs1 = Lists.newArrayList(1,2,3,4,5,6,7)
                .stream().map(i -> CompletableFuture.supplyAsync(()->helloService.testPage1(20,i)).whenComplete((r,e)->{
                    data.addAll(r.getData());
                })).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs1).join();
        System.out.println(JSON.json(data));
        data.clear();

        //只有一个线程阻塞
        CompletableFuture[] cfs2 = Lists.newArrayList(1,2,3,4,5,6,7)
                .stream().map( i -> helloService.testPage2(20,i).whenComplete((r,e)->{
                    data.addAll(r.getData());
                })).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs2).join();
        System.out.println(JSON.json(data));

    }

}
