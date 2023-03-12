package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 16:56
 */
public class DownCase extends StringProcessor{
    @Override
    public String process(String input) {
        return input.toLowerCase();
    }
}
