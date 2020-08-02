package com.scj.demo.dubbo.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HelloService {

    Result<String> hello(String name);

    Result<String> exception(String name);

    Result<String> timeout(String name);

    Result<List<String>> testPage1(Integer page, Integer pageSize);

    CompletableFuture<Result<List<String>>> testPage2(Integer page, Integer pageSize);

    String helloWorld(String name);
}

