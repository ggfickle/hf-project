package com.hf.demo.innerClassDemo.demo4;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:31
 */
public class AnonymousConstructor {

    public static Base getBase(int i) {
        return new Base(i) {
            {
                System.out.println("inside instance init");
            }
            @Override
            public void f() {
                System.out.println("inner f()");
            }
        };
    }

    public static void main(String[] args) {
        getBase(2).f();
    }
}
