package map;

import elements.Animal;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalHashMap{

    private MultiValuedMap<Vector2d, Animal> vector2dToAnimal;;

    public AnimalHashMap(){
        this.vector2dToAnimal = new ArrayListValuedHashMap<>();
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

    public List<Animal> allAnimalsOnPosition(Vector2d postion){
        synchronized (this.vector2dToAnimal){
            List<Animal> animals = new CopyOnWriteArrayList<>();
            animals.addAll(this.vector2dToAnimal.get(postion));
            return animals;
        }
    }

    public int animalsNumber() {
        synchronized (this.vector2dToAnimal) {
            return this.vector2dToAnimal.values().size();
        }
    }

    public boolean fewAnimalsOnOnePosition(Vector2d position){
        synchronized (this.vector2dToAnimal) {
            return this.vector2dToAnimal.get(position).size() > 1;
        }
    }

    public boolean containsAnimal(Vector2d position) {
        synchronized (this.vector2dToAnimal) {
            return vector2dToAnimal.containsKey(position);
        }
    }

    public void placeAnimal(Animal animal, Vector2d position){
        synchronized (this.vector2dToAnimal){
            this.vector2dToAnimal.put(position, animal);
        }
    }

    public void removeAnimal(Animal animal, Vector2d position){
        synchronized (this.vector2dToAnimal){
            this.vector2dToAnimal.removeMapping(position, animal);
        }
    }




}
