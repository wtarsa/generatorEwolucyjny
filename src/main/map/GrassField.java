package map;

import app.World;
import elements.Animal;
import elements.Grass;
import elements.IPositionChangeObserver;

import java.util.*;

public class GrassField implements IPositionChangeObserver {


    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    public AnimalHashMap vector2dToAnimal = new AnimalHashMap();
    public TuftsMap tuftsMap = new TuftsMap();
    public int emptyPlaces;
    private int seed = World.startSeed;
    private int tuftOfGrassNumber = 0;
    public Jungle jungle;
    public GrassField(int number){
        this.tuftOfGrassNumber = number;
        this.upperRight = new Vector2d(World.width-1, World.height-1);
        this.lowerLeft = new Vector2d(0,0);
        this.jungle = new Jungle();
        this.emptyPlaces = World.width*World.height - this.jungle.emptyPlaces;
    }

    //Grass placing
    public void placeGrassTufts(){
        Random rand = new Random(seed);
        fillMapWithTufts(rand);
    }

    private void fillMapWithTufts(Random rand){
        int n = this.tuftOfGrassNumber;
        for(int i = 0; i < n; i++) {
            placeOneTuftRandomly(rand);
        }
    }

    private void placeOneTuftRandomly(Random rand){
        Grass tuft;
        do {
            tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
        }
        while (this.tuftsMap.containsGrass(tuft.getPosition()));
        this.tuftsMap.placeGrass(tuft.getPosition(), tuft);
        if (tuft.belongsToJungle(this.jungle)) this.jungle.emptyPlaces--;
        else this.emptyPlaces--;
    }

    private void placeOneTuftOnMap(Random rand){
        Grass tuft;
        do {
            tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
        }
        while (tuftsMap.containsGrass(tuft.getPosition()) || tuft.belongsToJungle(this.jungle));
        this.tuftsMap.placeGrass(tuft.getPosition(), tuft);
        this.emptyPlaces--;
    }

    private void placeOneTuftOnJungle(Random rand){
        Grass tuft;
        do {
            tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
        }
        while (tuftsMap.containsGrass(tuft.getPosition()) || !tuft.belongsToJungle(this.jungle));
        this.tuftsMap.placeGrass(tuft.getPosition(), tuft);
        this.jungle.emptyPlaces--;
    }


    public void addNewPlants(){
        Random rand = new Random(seed);
        if (this.emptyPlaces > 0) this.placeOneTuftOnMap(rand);
        if (this.jungle.emptyPlaces > 0) this.placeOneTuftOnJungle(rand);
    }

    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    public boolean place(Animal animal) {
        try {
            if (this.vector2dToAnimal.containsAnimal(animal.getPosition()))
                throw new IllegalArgumentException("This field is occupied!");
            this.vector2dToAnimal.placeAnimal(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        } catch (IllegalArgumentException a) {
            System.out.println("Exception thrown  :" + a);
            return false;
        }
    }

    public Object objectAt(Vector2d position) {
        if (vector2dToAnimal.containsAnimal(position)) return vector2dToAnimal.allAnimalsOnPosition(position);
        if (tuftsMap.containsGrass(position)) return tuftsMap.getGrass(position);
        return null;
    }

    public void run() {
        List<Animal> animals = this.vector2dToAnimal.getAnimals();
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        vector2dToAnimal.removeAnimal(oldPosition, animal);
        vector2dToAnimal.placeAnimal(newPosition, animal);
    }

    public String toString() {
        MapVisualizer mapInstance = new MapVisualizer(this);
        return mapInstance.draw(this.lowerLeft, this.upperRight);
    }

}
