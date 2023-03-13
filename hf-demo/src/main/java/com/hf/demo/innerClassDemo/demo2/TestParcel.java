package com.hf.demo.innerClassDemo.demo2;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:41
 */
public class TestParcel {

    public static void main(String[] args) {
        Parcel4 parcel4 = new Parcel4();
        Contents contents = parcel4.contents();
        System.out.println(contents.value());
        Destination destination = parcel4.destination();
        System.out.println(destination.readLabel());

        Parcel4.PcDestination pcDestination = parcel4.new PcDestination();
        System.out.println(pcDestination.readLabel());
    }
}
