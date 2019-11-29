package map;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab3.Animal;
import agh.cs.lab7.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    protected HashMap<Vector2d, Animal> vector2dToAnimal = new LinkedHashMap<>();
    protected List<Animal> animals = new ArrayList<>();


    @Override
    public void run(MoveDirection[] directions) {
        for (int i = 0; i < directions.length;) {
            for (Animal animal : animals) {
                if (i < directions.length) {
                    animal.move(directions[i]);
                    i++;
                }
            }
        }
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
