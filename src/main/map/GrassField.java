package map;

import app.World;
import elements.Animal;
import elements.Grass;
import map.Jungle;
import map.AbstractWorldMap;
import map.Vector2d;
import map.IWorldMap;
import map.MapVisualizer;

import java.util.LinkedHashMap;
import java.util.Random;

public class GrassField extends AbstractWorldMap implements IWorldMap {

    public int emptyPlaces;
    private int seed = World.startSeed;
    private int tuftOfGrassNumber = 0;
    public Jungle jungle;
    public LinkedHashMap<Vector2d, Grass> tuftsMap = new LinkedHashMap<>();
    public GrassField(int number){
        this.seed = seed;
        this.tuftOfGrassNumber = number;
        this.upperRight = new Vector2d(World.width-1, World.height-1);
        this.lowerLeft = new Vector2d(0,0);
        this.jungle = new Jungle();
        this.emptyPlaces = World.width*World.height - this.jungle.emptyPlaces;
    }

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
        do{
            tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
        }
        while (tuftsMap.containsKey(tuft.getPosition()));
        this.tuftsMap.put(tuft.getPosition(), tuft);
        if(tuft.belongsToJungle(this.jungle)) this.jungle.emptyPlaces--;
        else this.emptyPlaces--;
    }

    private void placeOneTuftOnMap(Random rand){
        Grass tuft;
        do{
            tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
        }
        while (tuftsMap.containsKey(tuft.getPosition()) || tuft.belongsToJungle(this.jungle));
        this.tuftsMap.put(tuft.getPosition(), tuft);
        this.emptyPlaces--;
    }

    private void placeOneTuftOnJungle(Random rand){
        Grass tuft;
        do{
            tuft = new Grass(new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height)));
        }
        while (tuftsMap.containsKey(tuft.getPosition()) || !tuft.belongsToJungle(this.jungle));
        this.tuftsMap.put(tuft.getPosition(), tuft);
        this.jungle.emptyPlaces--;
    }


    public void addNewPlants(){
        Random rand = new Random(seed);
        if(this.emptyPlaces > 0) this.placeOneTuftOnMap(rand);
        if(this.jungle.emptyPlaces > 0) this.placeOneTuftOnJungle(rand);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public boolean place(Animal animal) {
        try{
            if(containsAnimal(animal.getPosition()))
                throw new IllegalArgumentException("This field is occupied!") ;
            this.vector2dToAnimal.put(animal.getPosition(), animal);

            animal.addObserver(this);
            return true;
        }
        catch (IllegalArgumentException a){
            System.out.println("Exception thrown  :" + a);

            return false;
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(vector2dToAnimal.containsKey(position)) return vector2dToAnimal.get(position);
        if(tuftsMap.containsKey(position)) return tuftsMap.get(position);
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d oldPosition, Vector2d newPosition){
        if(isOccupied(newPosition) && tuftsMap.containsKey(newPosition)){
            return true;
        }
        return (!isOccupied(newPosition));
    }

    public String toString(){
        MapVisualizer mapInstance = new MapVisualizer(this);
        return mapInstance.draw(this.lowerLeft, this.upperRight);
    }

    private boolean containsAnimal(Vector2d position){
        return vector2dToAnimal.containsKey(position);
    }

}
