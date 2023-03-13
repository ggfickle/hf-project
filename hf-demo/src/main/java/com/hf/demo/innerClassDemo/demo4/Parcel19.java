package com.hf.demo.innerClassDemo.demo4;

import com.hf.demo.innerClassDemo.demo2.Destination;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:24
 */
public class Parcel19 {

    public Destination destination(String dest) {
        return new Destination() {
            private final String label = dest;

            @Override
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel19 parcel19 = new Parcel19();
        System.out.println(parcel19.destination("test").readLabel());
    }
}
