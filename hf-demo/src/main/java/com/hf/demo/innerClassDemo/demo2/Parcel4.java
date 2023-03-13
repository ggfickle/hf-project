package com.hf.demo.innerClassDemo.demo2;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 21:39
 */
public class Parcel4 {

    private class PcContents implements Contents {

        @Override
        public int value() {
            return 2;
        }
    }

    protected class PcDestination implements Destination {

        @Override
        public String readLabel() {
            return "fok";
        }
    }

    public Contents contents() {
        return new PcContents();
    }

    public Destination destination() {
        return new PcDestination();
    }
}
