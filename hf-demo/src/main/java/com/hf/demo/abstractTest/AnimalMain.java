package com.hf.demo.abstractTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/8 23:19
 */
public class AnimalMain {
    public static void main(String[] args) {

        AbstractAnimal.getAnimalSound(new Cat());
        AbstractAnimal.getAnimalSound(new Dog());

        AbstractAnimal animalInstance = AnimalFactory.createAnimalInstance(AnimalFactory.AnimalType.CAT);
        animalInstance.sound();
        AbstractAnimal abstractAnimal = AnimalFactory.changeAnimal(AnimalFactory.AnimalType.DOG);
        abstractAnimal.sound();
    }
}
