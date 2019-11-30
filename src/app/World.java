package app;

import elements.Animal;


public class World {

    public static void main(String[] args) {
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();
        animal1.genotype.showGenotype();
        animal2.genotype.showGenotype();
    }
}
