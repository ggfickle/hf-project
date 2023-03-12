package com.hf.demo.interfaceTest;

import java.util.Arrays;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 16:56
 */
public class Splitter extends StringProcessor{
    @Override
    public String process(String input) {
        return Arrays.toString(input.split(" "));
    }
}
