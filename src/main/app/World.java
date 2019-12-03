package app;

import elements.Animal;
import map.GrassField;
import map.Vector2d;

import java.io.IOException;

public class World {

    public static int startSeed;
    public static int width;
    public static int height;

    public static void main(String[] args) {
        try {
            JSONParser.readJSON();
            GrassField map = new GrassField(5);
            Animal animal1 = new Animal(map);
            Animal animal2 = new Animal(map, new Vector2d(1, 1));
            animal1.genotype.showGenotype();
            animal2.genotype.showGenotype();
            map.place(animal1);
            map.place(animal2);
            System.out.println(map.toString());
            map.run();
            System.out.println(map.toString());
            map.run();
            System.out.println(map.toString());


        }catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }
}
