package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 18:30
 */
public class InterfaceMain {

    public static void main(String[] args) {
        Apply.process(new FilterAdapter(new HighPassFilter()), new WaveForm());
    }
}
