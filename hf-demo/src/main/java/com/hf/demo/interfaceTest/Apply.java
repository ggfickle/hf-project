package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 16:48
 */
public class Apply {

    public static <T> void process(Processor<T> processor, T t) {
        System.out.println(processor.name());
        System.out.println(processor.process(t));
    }
}
