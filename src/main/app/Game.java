package app;

import GUI.Panel;
import elements.Animal;
import map.GrassField;
import map.MapDirection;
import map.Vector2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    public int day;
    public int seed;
    public int numberOfAnimals;
    public float averageAnimalNumberAll;
    public float averageGrassNumberAll;
    public float averageAnimalEnergy;
    public float averageAnimalAge;
    public float averageChildNumber;
    public GrassField map;
    private boolean DEBUG = false;
    private ArrayList<Integer> lifeLength;

    public Game(int seed) {
        averageAnimalNumberAll = 1;
        averageGrassNumberAll = 1;
        this.numberOfAnimals = 0;
        this.averageAnimalEnergy = 0;
        this.averageAnimalAge = 0;
        this.averageChildNumber = 0;
        this.seed = seed;
        this.day = 0;
        this.lifeLength = new ArrayList<>();
    }

    public void beginSimulation(int initialAnimalsNumber){
        int tuftsNumber = (int)(World.width*World.height*World.startGrassTuftsRatio);
        this.map = new GrassField(tuftsNumber);
        this.debug(false);
        this.createAnimals(initialAnimalsNumber);
        this.map.placeGrassTufts();
    }

    private void createAnimals(int animalsNumber) {
        for (int i = 0; i < animalsNumber; i++) {
            Animal animal = new Animal(this);
            this.map.place(animal);
        }
    }

    private void deleteDeadAnimals() {
        List<Animal> animals = this.map.vector2dToAnimal.getAnimals();
        for (Animal animal : animals) {
            if (animal.energy <= 0.0) {
                this.map.vector2dToAnimal.removeAnimal(animal.getPosition(), animal);
                this.map.genotypeMap.remove(animal.ID, animal);
                this.lifeLength.add(animal.age);
            }
        }
    }

    private void subtractMoveEnergy() {
        List<Animal> animals = this.map.vector2dToAnimal.getAnimals();
        for (Animal animal : animals) {
            animal.energy -= World.moveEnergy;
        }
    }

    private void addPlantEnergy() {
        List<Vector2d> positions = this.map.vector2dToAnimal.getAnimalPositions();
        for (Vector2d position : positions) {
            if (this.map.tuftsMap.containsGrass(position)) {
                if (this.map.tuftsMap.getGrass(position).belongsToJungle(this.map.jungle)) this.map.jungle.emptyPlaces++;
                else this.map.emptyPlaces++;
                this.map.tuftsMap.removeGrass(position);
                double maxEnergy = -2e9;
                int animalsWithMaxEnergy = 0;
                List<Animal> animals = this.map.vector2dToAnimal.allAnimalsOnPosition(position);
                for (Animal animal : animals) {
                    if (Double.compare(maxEnergy, animal.energy) < 0) {
                        maxEnergy = animal.energy;
                        animalsWithMaxEnergy = 1;
                    } else if (Double.compare(maxEnergy, animal.energy) == 0) animalsWithMaxEnergy++;
                }
                for (Animal animal : animals) {
                    if (Double.compare(maxEnergy, animal.energy) == 0)
                        animal.energy += (World.plantEnergy / animalsWithMaxEnergy);
                }
            }
        }
    }

    private ArrayList<Vector2d> emptyPosition(Vector2d position) {
        MapDirection direction = MapDirection.NORTH;
        ArrayList<Vector2d> emptyPlaces = new ArrayList<Vector2d>();
        for (int i = 0; i < 8; i++) {
            if (this.map.vector2dToAnimal.allAnimalsOnPosition(position.add(direction.toUnitVector()).replaceOnMap()).isEmpty()) {
                emptyPlaces.add(position.add(direction.toUnitVector()).replaceOnMap());
            }
            direction = direction.next();
        }
        return emptyPlaces;
    }

    private void addNewAnimals() {
        List<Vector2d> positions = this.map.vector2dToAnimal.getAnimalPositions();
        for (Vector2d position : positions) {
            if (this.map.vector2dToAnimal.fewAnimalsOnOnePosition(position)) {
                List<Animal> animalsOnOnePosition = this.map.vector2dToAnimal.allAnimalsOnPosition(position);
                Animal animal1 = animalsOnOnePosition.get(0);
                Animal animal2 = animalsOnOnePosition.get(1);
                for (int i = 2; i < animalsOnOnePosition.size(); i++) { // this for loop should give two animals with the same position and with maximum energy among all the animals in this position
                    if (Double.compare(animal1.energy, animal2.energy) <= 0
                            && Double.compare(animal1.energy, animalsOnOnePosition.get(i).energy) <= 0)
                        animal1 = animalsOnOnePosition.get(i);
                    else if (Double.compare(animal2.energy, animal1.energy) <= 0
                            && Double.compare(animal2.energy, animalsOnOnePosition.get(i).energy) <= 0)
                        animal2 = animalsOnOnePosition.get(i);
                }
                if (animal1.enoughEnergyToReproduce(animal2)) {
                    ArrayList<Vector2d> emptyPositions = this.emptyPosition(animal1.getPosition());
                    if (!emptyPositions.isEmpty()) {
                        Animal child = animal1.reproduce(animal2, emptyPositions.get(0));
                        this.map.place(child);
                    }
                }
            }
        }
    }

    public void run() {
        if (!this.map.vector2dToAnimal.isEmpty()) {
            this.day++;
            this.deleteDeadAnimals();
            this.map.run();
            if (DEBUG) System.out.println(this.map.toString());
            if (DEBUG) System.out.println("Before:");
            if (DEBUG) printEnergy();
            this.subtractMoveEnergy();
            this.addPlantEnergy();
            if (DEBUG) System.out.println("After:");
            if (DEBUG) printEnergy();
            this.map.addNewPlants();
            this.addNewAnimals();
            this.calculateAverageEnergy();
            this.calculateAverageAge();
            this.calculateAverageChildNumber();
            this.calculateAverageAnimalNumberAll();
            this.calculateAverageGrassNumberAll();

            if(day == World.stat_days) {
                try {
                    JSONParser.createJSON(averageAnimalNumberAll, averageGrassNumberAll, averageAnimalEnergy, averageAnimalAge, averageChildNumber);
                } catch (IOException ex) {
                    System.out.println("Problem while creating a JSON file...");
                }
            }
        }
    }

    private void calculateAverageEnergy(){
        List<Animal> animals = this.map.vector2dToAnimal.getAnimals();
        float avg = 0;
        for(Animal animal: animals){
            avg += animal.energy;
        }
        avg /= this.map.vector2dToAnimal.getAnimals().size();
        this.averageAnimalEnergy = avg;
    }

    private void calculateAverageAge(){
        synchronized (this.lifeLength) {
            if(lifeLength.size() != 0) {
                float avg = 0;
                for (Integer age : lifeLength) {
                    avg += age;
                }
                avg = avg / lifeLength.size();
                this.averageAnimalAge = avg;
            }
        }
    }

    private void calculateAverageChildNumber(){
        List<Animal> animals = this.map.vector2dToAnimal.getAnimals();
        float avg = 0;
        for (Animal animal : animals) {
            avg += animal.children;
        }
        avg = avg / this.map.vector2dToAnimal.animalsNumber();
        this.averageChildNumber = avg;

    }

    private void calculateAverageAnimalNumberAll(){
        averageAnimalNumberAll *= (day-1);
        averageAnimalNumberAll += this.map.vector2dToAnimal.animalsNumber();
        averageAnimalNumberAll /= day;
    }

    private void calculateAverageGrassNumberAll(){
        averageGrassNumberAll *= (day-1);
        averageGrassNumberAll += this.map.tuftsMap.grassNumber();
        averageGrassNumberAll /= day;
    }
    // for debugging purposes
    private void debug(boolean flag){
        this.DEBUG = flag;
    }

    private void printEnergy() {
        List<Animal> animals = this.map.vector2dToAnimal.getAnimals();
        for (Animal animal : animals) {
            System.out.print(animal.getPosition().toString() + " ");
            System.out.print(animal.ID + " ");
            System.out.println(animal.energy);
        }
    }
}