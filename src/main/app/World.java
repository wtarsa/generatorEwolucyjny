package app;

import elements.Animal;
import map.GrassField;
import map.MapDirection;
import map.Vector2d;

import java.io.IOException;
import java.util.ArrayList;


public class World {

    public static int startSeed;
    public static int width;
    public static int height;
    public static float startEnergy;
    public static float moveEnergy;
    public static float plantEnergy;
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
            world.createAnimals(4);
            world.map.placeGrassTufts();
            world.runGameplay(30);

        }catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }

    private void runGameplay(int days){
        for(int i = 0; i < days; i++){
            if(this.map.vector2dToAnimal.size() != 0) {
                this.deleteDeadAnimals();
                this.map.run();
                System.out.println(this.map.toString());
                System.out.println("Before:");
                if (DEBUG) printEnergy();
                this.subtractMoveEnergy();
                this.addPlantEnergy();
                System.out.println("After:");
                if (DEBUG) printEnergy();
                this.map.addNewPlants();
                this.addNewAnimals();
            }
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
        ArrayList<Vector2d> positions = new ArrayList<>(this.map.vector2dToAnimal.keySet());
        for(Vector2d position: positions){
            if(this.map.tuftsMap.containsKey(position)){
                this.map.tuftsMap.remove(position);
                double maxEnergy = -2e9;
                int animalsWithMaxEnergy = 0;
                ArrayList<Animal> animals = new ArrayList<>(this.map.vector2dToAnimal.get(position));
                for(Animal animal: animals){
                    if(Double.compare(maxEnergy, animal.energy) < 0){
                        maxEnergy = animal.energy;
                        animalsWithMaxEnergy = 1;
                    }
                    else if(Double.compare(maxEnergy, animal.energy) == 0) animalsWithMaxEnergy++;
                }
                for(Animal animal: animals){
                    if(Double.compare(maxEnergy, animal.energy) == 0) animal.energy += (World.plantEnergy/animalsWithMaxEnergy);
                }
            }
        }
    }

    private ArrayList<Vector2d> emptyPosition(Vector2d position){
        MapDirection direction = MapDirection.NORTH;
        ArrayList<Vector2d> emptyPlaces = new ArrayList<Vector2d>();
        for(int i = 0; i < 8; i++){
            if(this.map.vector2dToAnimal.get(position.add(direction.toUnitVector()).replaceOnMap()).isEmpty()){ // czy to nie zmienia wartości position?
               emptyPlaces.add(position.add(direction.toUnitVector()).replaceOnMap());
            }
            direction = direction.next();
        }
        return emptyPlaces;
    }

    private void addNewAnimals() {
        ArrayList<Vector2d> positions = new ArrayList<>(this.map.vector2dToAnimal.keySet());
        for (Vector2d position : positions) {
            if(this.map.vector2dToAnimal.get(position).size() > 1){
                ArrayList<Animal> animalsOnOnePosition = new ArrayList<>(this.map.vector2dToAnimal.get(position));
                Animal animal1 = animalsOnOnePosition.get(0);
                Animal animal2 = animalsOnOnePosition.get(1);
                for(int i = 2; i < animalsOnOnePosition.size(); i++){ // this for loop should give two animals with the same position and with maximum energy among all the animals in this position
                    if(Double.compare(animal1.energy, animal2.energy) <= 0
                            && Double.compare(animal1.energy, animalsOnOnePosition.get(i).energy) <= 0)
                        animal1 = animalsOnOnePosition.get(i);
                    else if (Double.compare(animal2.energy, animal1.energy) <= 0
                            && Double.compare(animal2.energy, animalsOnOnePosition.get(i).energy) <= 0)
                        animal2 = animalsOnOnePosition.get(i);
                }
                if(animal1.enoughEnergyToReproduct(animal2)){
                    ArrayList<Vector2d> emptyPositions = this.emptyPosition(animal1.getPosition());
                    if(!emptyPositions.isEmpty()){
                        Animal child = animal1.reproduct(animal2, emptyPositions.get(0));
                        this.map.place(child);
                    }
                }
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
            System.out.print(animal.getPosition().toString() + " ");
            System.out.print(animal.ID + " ");
            System.out.println(animal.energy);
        }
    }


}
