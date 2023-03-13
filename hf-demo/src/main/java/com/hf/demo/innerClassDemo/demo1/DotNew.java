package com.hf.demo.innerClassDemo.demo1;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:26
 */
public class DotNew {

    public class InnerNew {
    }

    public static void main(String[] args) {
        DotNew dotNew = new DotNew();
        // 显式创建外部类对象
        InnerNew innerNew = dotNew.new InnerNew();
    }
}
