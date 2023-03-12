package com.hf.demo.interfaceTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/12 16:50
 */
public abstract class StringProcessor implements Processor<String>{

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    public abstract String process(String input);

    public static String s = "Just Do It";

    public static void main(String[] args) {
        Apply.process(new DownCase(), s);
        Apply.process(new UpCase(), s);
        Apply.process(new Splitter(), s);
    }
}
