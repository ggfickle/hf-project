package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 18:24
 */
public class Filter {

    public String name() {
        return getClass().getSimpleName();
    }

    public WaveForm process(WaveForm waveForm) {
        return waveForm;
    }
}
