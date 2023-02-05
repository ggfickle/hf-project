package com.hf.provider.filter;

import org.apache.dubbo.rpc.*;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/5 0:05
 */
public class CustomDubboFilter implements Filter,Filter.Listener {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // before filter ...
        System.out.println(invocation.getMethodName());
        Result result = invoker.invoke(invocation);
        // after filter ...
        System.out.println(result.getValue());
        return result;
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {

    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }
}
