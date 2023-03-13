package com.hf.demo.innerClassDemo.demo5;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:43
 */
public class Impl2Service2 implements Service{

    private Impl2Service2() {
    }

    @Override
    public void method1() {
        System.out.println("impl2 m1");
    }

    @Override
    public void method2() {
        System.out.println("impl2 m1");
    }

    public static ServiceFactory factory = Impl2Service2::new;
}
