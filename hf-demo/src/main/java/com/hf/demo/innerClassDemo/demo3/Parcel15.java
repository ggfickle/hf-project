package com.hf.demo.innerClassDemo.demo3;

import com.hf.demo.innerClassDemo.demo2.Destination;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:47
 */
public class Parcel15 {

    public Destination destination() {
        class PDestination implements Destination {

            @Override
            public String readLabel() {
                return "pds";
            }
        }
        return new PDestination();
    }

    public Destination destination2() {
        class PDestination implements Destination {

            @Override
            public String readLabel() {
                return "pds2";
            }
        }
        return new PDestination();
    }

    private class PDestination implements Destination {

        @Override
        public String readLabel() {
            return "PRIVATE PD";
        }
    }

    public Destination createPDestination() {
        return new PDestination();
    }

    public static void main(String[] args) {
        System.out.println(new Parcel15().destination().readLabel());
        System.out.println(new Parcel15().destination2().readLabel());
        PDestination pDestination = new Parcel15().new PDestination();
    }
}
