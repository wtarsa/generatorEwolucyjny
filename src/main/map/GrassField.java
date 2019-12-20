package map;

import app.World;
import elements.Animal;
import elements.Grass;
import elements.IPositionChangeObserver;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GrassField implements IPositionChangeObserver {


    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    public MultiValuedMap<Vector2d, Animal> vector2dToAnimal = new ArrayListValuedHashMap<>();

    public int emptyPlaces;
    private int seed = World.startSeed;
    private int tuftOfGrassNumber = 0;
    public Jungle jungle;
    public LinkedHashMap<Vector2d, Grass> tuftsMap = new LinkedHashMap<>();
    public GrassField(int number){
        this.tuftOfGrassNumber = number;
        this.upperRight = new Vector2d(World.width-1, World.height-1);
        this.lowerLeft = new Vector2d(0,0);
        this.jungle = new Jungle();
        this.emptyPlaces = World.width*World.height - this.jungle.emptyPlaces;
    }

    //Grass placing
    public void placeGrassTufts(){
        synchronized (this.tuftsMap) {
            Random rand = new Random(seed);
            fillMapWithTufts(rand);
        }
    }

    private void fillMapWithTufts(Random rand){
        int n = this.tuftOfGrassNumber;
        for(int i = 0; i < n; i++) {
            placeOneTuftRandomly(rand);
        }
    }

    private void placeOneTuftRandomly(Random rand){
        synchronized (this.tuftsMap) {
            Grass tuft;
            do {
                tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
            }
            while (tuftsMap.containsKey(tuft.getPosition()));
            this.tuftsMap.put(tuft.getPosition(), tuft);
            if (tuft.belongsToJungle(this.jungle)) this.jungle.emptyPlaces--;
            else this.emptyPlaces--;
        }
    }

    private void placeOneTuftOnMap(Random rand){
        synchronized (this.tuftsMap) {
            Grass tuft;
            do {
                tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
            }
            while (tuftsMap.containsKey(tuft.getPosition()) || tuft.belongsToJungle(this.jungle));
            this.tuftsMap.put(tuft.getPosition(), tuft);
            this.emptyPlaces--;
        }
    }

    private void placeOneTuftOnJungle(Random rand){
        synchronized (this.tuftsMap) {
            Grass tuft;
            do {
                tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
            }
            while (tuftsMap.containsKey(tuft.getPosition()) || !tuft.belongsToJungle(this.jungle));
            this.tuftsMap.put(tuft.getPosition(), tuft);
            this.jungle.emptyPlaces--;
        }
    }


    public void addNewPlants(){
        synchronized (this.tuftsMap) {
            Random rand = new Random(seed);
            if (this.emptyPlaces > 0) this.placeOneTuftOnMap(rand);
            if (this.jungle.emptyPlaces > 0) this.placeOneTuftOnJungle(rand);
        }
    }

    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    public boolean place(Animal animal) {
        synchronized (this.vector2dToAnimal) {
            try {
                if (containsAnimal(animal.getPosition()))
                    throw new IllegalArgumentException("This field is occupied!");
                this.vector2dToAnimal.put(animal.getPosition(), animal);

                animal.addObserver(this);
                return true;
            } catch (IllegalArgumentException a) {
                System.out.println("Exception thrown  :" + a);

                return false;
            }
        }
    }

    public Object objectAt(Vector2d position) {
        synchronized (this.vector2dToAnimal) {
            if (vector2dToAnimal.containsKey(position)) return vector2dToAnimal.get(position);
            if (tuftsMap.containsKey(position)) return tuftsMap.get(position);
            return null;
        }
    }

    public void run() {
        ArrayList<Animal> animals = new ArrayList<>(vector2dToAnimal.values());
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        vector2dToAnimal.removeMapping(oldPosition, animal);
        vector2dToAnimal.put(newPosition, animal);
    }

    public String toString(){
        MapVisualizer mapInstance = new MapVisualizer(this);
        return mapInstance.draw(this.lowerLeft, this.upperRight);
    }

    // mapa tak na prawde, do wywalenia
    private boolean containsAnimal(Vector2d position) {
        synchronized (this.vector2dToAnimal) {
            return vector2dToAnimal.containsKey(position);
        }
    }

    public List<Animal> getAnimals(){
        synchronized (this.vector2dToAnimal) {
            List<Animal> animals = new CopyOnWriteArrayList<>();
            animals.addAll(this.vector2dToAnimal.values());
            return animals;
        }
    }

    public List<Vector2d> getAnimalPositions(){
        synchronized (this.vector2dToAnimal){
            List<Vector2d> positions = new CopyOnWriteArrayList<Vector2d>();
            positions.addAll(this.vector2dToAnimal.keySet());
            return positions;
        }
    }
}
