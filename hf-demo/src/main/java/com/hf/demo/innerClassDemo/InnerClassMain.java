package com.hf.demo.innerClassDemo;

import com.hf.demo.innerClassDemo.demo2.Destination;
import com.hf.demo.innerClassDemo.demo3.Parcel15;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:57
 */
public class InnerClassMain {

    public static void main(String[] args) {
        Parcel15 parcel15 = new Parcel15();
        Destination pDestination = parcel15.createPDestination();
        System.out.println(pDestination.readLabel());


    }
}
