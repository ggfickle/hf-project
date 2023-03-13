package com.hf.demo.innerClassDemo.demo1;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:33
 */
public class Demo1Main {

    public static void main(String[] args) {
        // ok 静态内部类可以使用如下写法
        new DotStatic.InnerStatic();
        // error 普通内部类下面的写法不行
//        new DotNew().InnerNew();
    }
}
