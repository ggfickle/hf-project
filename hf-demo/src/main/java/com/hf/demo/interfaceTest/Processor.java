package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 16:47
 */
public interface Processor<T> {

    String name();

    T process(T input);
}
