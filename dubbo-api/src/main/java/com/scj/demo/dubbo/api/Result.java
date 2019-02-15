package com.scj.demo.dubbo.api;

import lombok.Data;

import java.io.Serializable;

@Data
public  class  Result<T> implements Serializable {

    private T data;

    private Integer code;

    private String message;

    public  static <T>  Result<T> ofSuccess(T data){
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public  static <T>  Result<T> ofFail(Integer code,String message){
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
