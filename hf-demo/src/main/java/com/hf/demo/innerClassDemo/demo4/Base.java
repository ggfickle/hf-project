package com.hf.demo.innerClassDemo.demo4;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:30
 */
public abstract class Base {

    public Base(int i) {
        System.out.println("Base init " + i);
    }

    public abstract void f();
}
