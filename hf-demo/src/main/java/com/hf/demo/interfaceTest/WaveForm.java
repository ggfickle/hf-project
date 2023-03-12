package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 17:02
 */
public class WaveForm {

    private static long counter;

    private final long id = counter++;

    @Override
    public String toString() {
        return "WaveForm" + id;
    }
}
