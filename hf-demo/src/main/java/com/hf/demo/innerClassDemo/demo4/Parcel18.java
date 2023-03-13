package com.hf.demo.innerClassDemo.demo4;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:19
 */
public class Parcel18 {

    public Wrapping wrapping(int x) {
        return new Wrapping(x) {
            @Override
            public int value() {
                return super.value() + 19;
            }
        };
    }

    public static void main(String[] args) {
        Parcel18 parcel18 = new Parcel18();
        System.out.println(parcel18.wrapping(2).value());
    }
}
