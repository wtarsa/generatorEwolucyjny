package app;

import elements.Animal;
import map.GrassField;
import map.Vector2d;

import java.io.IOException;
import java.util.Collection;

public class World {

    public static int startSeed;
    public static int width;
    public static int height;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static float jungleRatio;
    public static float startGrassTuftsRatio;
    public GrassField map;

    public World(int tuftsNumber){
        this.map = new GrassField(tuftsNumber);
    }

    public static void main(String[] args) {
        try {
            JSONParser.readJSON();
            int tuftsNumber = (int)(World.width*World.height*World.startGrassTuftsRatio);
            World world = new World(tuftsNumber);
            world.createAnimals(2);
            // GrassField map = new GrassField(tuftsNumber);
          //  Animal animal1 = new Animal(map);
           // Animal animal2 = new Animal(map, new Vector2d(1, 1));

           // animal1.genotype.showGenotype();
           // animal2.genotype.showGenotype();
           // map.place(animal1);
           // map.place(animal2);
            world.map.placeGrassTufts();
            world.runGameplay(3);
            //System.out.println(map.toString());
            //map.run();
            //System.out.println(map.toString());
           // map.run();
           // System.out.println(map.toString());


        }catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }

    private void runGameplay(int days){
        for(int i = 0; i < days; i++){
            this.map.run();
            System.out.println(this.map.toString());
        }
    }

    private void createAnimals(int animalsNumber){
        for(int i = 0; i < animalsNumber; i++){
            Animal animal = new Animal(this.map);
            this.map.place(animal);
        }
    }

    private void deleteDeadAnimals(){
        Collection<Animal> animals = this.map.vector2dToAnimal.values();
        for(Animal animal : animals){
            if(animal.energy == 0){
                this.map.vector2dToAnimal.removeMapping(animal.getPosition(), animal);
            }
        }
    }


}
