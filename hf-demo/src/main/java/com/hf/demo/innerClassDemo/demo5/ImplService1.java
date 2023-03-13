package com.hf.demo.innerClassDemo.demo5;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:43
 */
public class ImplService1 implements Service{

    private ImplService1() {
    }

    @Override
    public void method1() {
        System.out.println("impl1 m1");
    }

    @Override
    public void method2() {
        System.out.println("impl1 m2");
    }

    public static ServiceFactory serviceFactory = ImplService1::new;
}
