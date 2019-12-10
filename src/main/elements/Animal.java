package elements;

import app.World;
import map.GrassField;
import map.MapDirection;
import map.Vector2d;
import map.IWorldMap;
import elements.Genotype;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements IMapElement, IPositionChangeObserver {

    public static int numberOfAnimals = 0;
    public Genotype genotype;
    public int energy;
    public String ID;
    private MapDirection direction;
    private Vector2d position;
    private GrassField map;

    Random rand = new Random(World.startSeed+Animal.numberOfAnimals);

    ArrayList<IPositionChangeObserver> observerCollection = new ArrayList<>();

    public Animal(){
        this.genotype = new Genotype();
        this.direction = this.genotype.getDirection();
        this.position = new Vector2d(2, 2);
        this.energy = World.startEnergy;
        this.ID = this.genotype.getID();
        numberOfAnimals++;
    }

    public Animal(GrassField map){
        this.genotype = new Genotype();
        this.direction = this.genotype.getDirection();
        this.map = map;
        this.position = placeAnimal() ;
        this.energy = World.startEnergy;
        this.ID = this.genotype.getID();
        numberOfAnimals++;
    }

    public Animal(GrassField map, Vector2d initialPosition){
        this.genotype = new Genotype();
        this.direction = this.genotype.getDirection();
        this.position = initialPosition;
        this.map = map;
        this.energy = World.startEnergy;
        this.ID = this.genotype.getID();
        numberOfAnimals++;
    }

    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String toString(){
        switch(this.direction){
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            case NORTHWEST:
                return "NW";
            case NORTHEAST:
                return "NE";
            case SOUTHEAST:
                return "SE";
            case SOUTHWEST:
                return "SW";
            default:
                return "";
        }
    }

    public void move() {
        if(this.map.canMoveTo(this.position, this.position.add(this.direction.toUnitVector()))) {
            Vector2d newPosition = this.position.add(this.direction.toUnitVector()).replaceOnMap();
            this.positionChanged(this.ID, this.position, newPosition);
            this.position = this.position.add(this.direction.toUnitVector()).replaceOnMap();
            this.updateDirection();
            this.updatePosition();
        }
    }

    private void updateDirection(){
        this.direction = this.genotype.getDirection();
    }

    private void updatePosition(){
        Vector2d newPosition = (new Vector2d((this.position.x + World.width)%(World.width),
                (this.position.y + World.height)%(World.height)));
        this.positionChanged(this.ID, this.position, newPosition);

    }

    public void addObserver(IPositionChangeObserver observer){
        this.observerCollection.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observerCollection.remove(observer);
    }

    public void positionChanged(String id, Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observerCollection) {
            observer.positionChanged(id, oldPosition, newPosition);
        }
    }

    private Vector2d placeAnimal(){
        Vector2d position;
        do{
            position = new Vector2d(rand.nextInt(World.width),rand.nextInt(World.height));
        } while(this.map.vector2dToAnimal.containsKey(position));
        return position;
    }

}
