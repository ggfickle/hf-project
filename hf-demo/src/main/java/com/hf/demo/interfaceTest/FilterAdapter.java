package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 18:28
 */
public class FilterAdapter implements Processor<WaveForm>{

    private Filter filter;

    public FilterAdapter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public WaveForm process(WaveForm waveForm) {
        return filter.process(waveForm);
    }
}
