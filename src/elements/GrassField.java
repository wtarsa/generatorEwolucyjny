package elements;

import agh.cs.lab2.Vector2d;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.MapVisualizer;
import agh.cs.lab7.MapBoundary;

import java.util.LinkedHashMap;
import java.util.Random;

public class GrassField extends AbstractWorldMap implements IWorldMap {

    private int seed = 0;
    private int tuftOfGrassNumber = 0;
    private MapBoundary border = new MapBoundary();
    public LinkedHashMap<Vector2d, Grass> tuftsMap = new LinkedHashMap<>();
    public GrassField(int number){
        this.tuftOfGrassNumber = number;
    }

    public void placeGrassTufts(){
        Random rand = new Random();
        fillMapWithTufts(rand);
    }

    public void placeGrassTufts(int seed){
        this.seed = seed;
        Random rand = new Random(seed);
        fillMapWithTufts(rand);
    }

    private void fillMapWithTufts(Random rand){
        int n = this.tuftOfGrassNumber;
        for(int i = 0; i < n; i++) {
            placeOneTuft(rand);
        }
    }

    private void placeOneTuft(Random rand){
        int n = this.tuftOfGrassNumber;
        Grass tuft;
        do{
            tuft = new Grass(new Vector2d(rand.nextInt((int) Math.sqrt(n * 10)), rand.nextInt((int) Math.sqrt(n * 10))));
        }
        while (tuftsMap.containsKey(tuft.getPosition()));
        this.tuftsMap.put(tuft.getPosition(), tuft);
        this.border.add(tuft.getPosition());
    }

    @Override
    public boolean place(Animal animal) {
        try{
            if(isOccupied(animal.getPosition()))
                throw new IllegalArgumentException("This field is occupied!") ;
            this.vector2dToAnimal.put(animal.getPosition(), animal);
            animal.addObserver(this);
            animal.addObserver(this.border);
            this.animals.add(animal);
            this.border.add(animal.getPosition());
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
    public boolean canMoveTo(Vector2d position){
        if(isOccupied(position) && tuftsMap.containsKey(position)){
            Random rand = new Random(this.seed);
            placeOneTuft(rand);
            return true;
        }
        return (!isOccupied(position));

    }

    private void updateCorners(){
        this.lowerLeft = border.getLowerLeftCorner();
        this.upperRight = border.getUpperRightCorner();
    }

    public String toString(){
        updateCorners();
        MapVisualizer mapInstance = new MapVisualizer(this);
        return mapInstance.draw(this.lowerLeft, this.upperRight);

    }

}
