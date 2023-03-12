package com.hf.demo.interfaceTest;

import java.util.Locale;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 16:54
 */
public class UpCase extends StringProcessor{
    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
