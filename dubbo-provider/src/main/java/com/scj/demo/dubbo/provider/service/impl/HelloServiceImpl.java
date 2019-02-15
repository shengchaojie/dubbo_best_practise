package com.scj.demo.dubbo.provider.service.impl;

import com.google.common.collect.Lists;
import com.scj.demo.dubbo.api.HelloService;
import com.scj.demo.dubbo.api.Result;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HelloServiceImpl implements HelloService {

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

        return CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000* 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Result.ofSuccess(Lists.newArrayList("scj","123","356"));
        });
    }
}
