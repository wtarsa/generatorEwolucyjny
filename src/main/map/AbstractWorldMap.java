package map;

import elements.Animal;
import elements.IPositionChangeObserver;

import java.util.*;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    public MultiValuedMap<Vector2d, Animal> vector2dToAnimal = new ArrayListValuedHashMap<>();


    @Override
    public void run() {
        ArrayList<Animal> animals = new ArrayList<>(vector2dToAnimal.values());
        for (Animal animal : animals) {
            animal.move();
        }
    }

    @Override
    public void positionChanged(String id ,Vector2d oldPosition, Vector2d newPosition) {
        Collection<Animal> animals = vector2dToAnimal.get(oldPosition);
        Animal animal = getAnimalWithID(id, oldPosition);
        vector2dToAnimal.removeMapping(oldPosition, animal);
        vector2dToAnimal.put(newPosition, animal);
    }

    protected Animal getAnimalWithID(String ID, Vector2d position){
        Collection<Animal> animals = vector2dToAnimal.get(position);
        Animal animal = new Animal();
        for(Animal animal1: animals){
            if(animal1.ID.equals(ID)){
                animal = animal1;
                break;
            }
        }
        return animal;
    }
}
