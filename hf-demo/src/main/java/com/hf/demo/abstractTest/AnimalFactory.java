package com.hf.demo.abstractTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/8 23:22
 */
public class AnimalFactory {

    private static AbstractAnimal abstractAnimal;

    public static AbstractAnimal createAnimalInstance(AnimalType animalType) {
        if (animalType == AnimalType.CAT) {
            abstractAnimal = new Cat();
        } else {
            abstractAnimal = new Dog();
        }
        return abstractAnimal;
    }

    public static AbstractAnimal changeAnimal(AnimalType animalType) {
        abstractAnimal = createAnimalInstance(animalType);
        return abstractAnimal;
    }

    public enum AnimalType {
        CAT,
        DOG
    }
}


