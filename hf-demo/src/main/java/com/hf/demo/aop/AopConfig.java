package com.hf.demo.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xiehongfei
 * @description
 * @date 2022/12/30 20:35
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy=true)
public class AopConfig {
}
