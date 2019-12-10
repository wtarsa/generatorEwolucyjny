package app;

import elements.Animal;
import map.GrassField;
import map.Vector2d;

import java.io.IOException;
import java.util.ArrayList;
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
    private static boolean DEBUG = false;
    public GrassField map;

    public World(int tuftsNumber){
        this.map = new GrassField(tuftsNumber);
    }

    public static void main(String[] args) {
        try {
            JSONParser.readJSON();
            int tuftsNumber = (int)(World.width*World.height*World.startGrassTuftsRatio);
            World world = new World(tuftsNumber);
            world.debug(true);
            world.createAnimals(2);
            world.map.placeGrassTufts();
            world.runGameplay(3);

        }catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }

    private void runGameplay(int days){
        for(int i = 0; i < days; i++){
            this.deleteDeadAnimals();
            this.map.run();
            System.out.println(this.map.toString());
            if(DEBUG) printEnergy();
            this.subtractMoveEnergy();
            this.addPlantEnergy();
            if(DEBUG) printEnergy();
        }
    }

    private void createAnimals(int animalsNumber){
        for(int i = 0; i < animalsNumber; i++){
            Animal animal = new Animal(this.map);
            this.map.place(animal);
        }
    }

    private void deleteDeadAnimals(){
        ArrayList<Animal> animals = new ArrayList<>(this.map.vector2dToAnimal.values());
        for(Animal animal : animals){
            if(animal.energy <= 0){
                this.map.vector2dToAnimal.removeMapping(animal.getPosition(), animal);
            }
        }
    }

    private void subtractMoveEnergy(){
        ArrayList<Animal> animals = new ArrayList<>(this.map.vector2dToAnimal.values());
        for(Animal animal : animals){
            animal.energy -= World.moveEnergy;
        }
    }

    private void addPlantEnergy() {
        ArrayList<Animal> animals = new ArrayList<>(this.map.vector2dToAnimal.values());
        for (Animal animal : animals) {
            if(this.map.tuftsMap.containsKey(animal.getPosition())){
                this.map.tuftsMap.remove(animal.getPosition());
                animal.energy += World.plantEnergy;
            }
        }
    }
    // for debugging purposes
    private void debug(boolean flag){
        World.DEBUG = flag;
    }

    private void printEnergy() {
        ArrayList<Animal> animals = new ArrayList<>(this.map.vector2dToAnimal.values());
        for (Animal animal : animals) {
            System.out.print(animal.ID + " ");
            System.out.println(animal.energy);
        }
    }
}
