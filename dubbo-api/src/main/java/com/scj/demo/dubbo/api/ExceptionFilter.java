package com.scj.demo.dubbo.api;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.support.RpcUtils;

@Activate(group = Constants.PROVIDER)
public class ExceptionFilter implements Filter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result originResult = invoker.invoke(invocation);
        if(originResult.hasException()){
            Class<?> clazz = RpcUtils.getReturnType(invocation);
            if(clazz.isAssignableFrom(com.scj.demo.dubbo.api.Result.class)){
                com.scj.demo.dubbo.api.Result result = new com.scj.demo.dubbo.api.Result();
                result.setCode(500);
                result.setMessage(originResult.getException().getMessage());
                RpcResult changedResult = new RpcResult(result);
                return changedResult;
            }
            return originResult;
        }
        return originResult;
    }
}
