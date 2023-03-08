package com.hf.demo.abstractTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/8 23:16
 */
public abstract class AbstractAnimal {

    abstract void sound();

    public static void getAnimalSound(AbstractAnimal abstractAnimal) {
        abstractAnimal.sound();
    }
}
