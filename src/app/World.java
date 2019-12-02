package app;

import elements.Animal;

import java.io.IOException;
import java.text.ParseException;

public class World {

    public static int startSeed;

    public static void main(String[] args) {
        try {
            JSONHandler.readJSON();
            Animal animal1 = new Animal();
            Animal animal2 = new Animal();
            animal1.genotype.showGenotype();
            animal2.genotype.showGenotype();
        }catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }
}
