package map;

import elements.Animal;
import elements.IPositionChangeObserver;

import java.util.*;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
   // protected HashMap<Vector2d, Animal> vector2dToAnimal = new LinkedHashMap<>();
    public MultiValuedMap<Vector2d, Animal> vector2dToAnimal = new ArrayListValuedHashMap<>();
 //   protected List<Animal> animals = new ArrayList<>();


    @Override
    public void run() {
        Collection<Animal> animals = vector2dToAnimal.values();
        ArrayList<Animal> animals1 = new ArrayList<>(animals);
        for (Animal animal : animals1) {
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
