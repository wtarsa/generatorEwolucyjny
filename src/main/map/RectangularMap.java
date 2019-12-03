package map;

import elements.Animal;
import elements.IPositionChangeObserver;
import map.AbstractWorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class RectangularMap implements IWorldMap, IPositionChangeObserver {

    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    protected HashMap<Vector2d, Animal> vector2dToAnimal = new LinkedHashMap<>();
    protected List<Animal> animals = new ArrayList<>();

    public RectangularMap(int width, int height){
        this.upperRight = new Vector2d(width, height);
        this.lowerLeft = new Vector2d(0, 0);
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return (!isOccupied(position) && position.follows(this.lowerLeft) && position.precedes(this.upperRight));
    }

    @Override
    public Object objectAt(Vector2d position) {
        return vector2dToAnimal.get(position);
    }

    @Override
    public boolean place(Animal animal) {
        try{
            if(isOccupied(animal.getPosition()))
                throw new IllegalArgumentException("This field is occupied!") ;
            this.vector2dToAnimal.put(animal.getPosition(), animal);
            animal.addObserver(this);
            this.animals.add(animal);
            return true;
        }
        catch (IllegalArgumentException a){
            System.out.println("Exception thrown  :" + a);
            return false;
        }
    }

    @Override
    public void run() {

    }

    public String toString(){
        MapVisualizer mapInstance = new MapVisualizer(this);
        return mapInstance.draw(lowerLeft, this.upperRight);
    }
    
    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = vector2dToAnimal.get(oldPosition);
        vector2dToAnimal.remove(oldPosition);
        vector2dToAnimal.put(newPosition, animal);
    }

}
