package map;

import agh.cs.lab3.Animal;
import agh.cs.lab5.AbstractWorldMap;

public class RectangularMap extends AbstractWorldMap implements IWorldMap {

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

    public String toString(){
        MapVisualizer mapInstance = new MapVisualizer(this);
        return mapInstance.draw(lowerLeft, this.upperRight);
    }

}
