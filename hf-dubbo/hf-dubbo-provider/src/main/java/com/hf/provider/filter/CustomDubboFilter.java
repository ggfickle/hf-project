package com.hf.provider.filter;

import org.apache.dubbo.rpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/5 0:05
 */
public class CustomDubboFilter implements Filter,Filter.Listener {
    private static final Logger LOGGER = LogManager.getLogger(CustomDubboFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // before filter ...
        LOGGER.info("invoke--invocation.getMethodName()" + invocation.getMethodName());
        Result result = invoker.invoke(invocation);
        // after filter ...
        LOGGER.info("invoke--result.getValue()--" + result.getValue());
        return result;
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {

    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }
}
