package com.hf.demo.innerClassDemo.demo1;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:23
 */
public class DotThis {

    void f() {
        System.out.println("DotThis.f()");
    }

    public class Inner {
        public DotThis outer() {
            return DotThis.this;
        }
    }

    public Inner inner() {
        return new Inner();
    }

    public static void main(String[] args) {
        DotThis dotThis = new DotThis();
        dotThis.inner().outer().f();
    }
}
