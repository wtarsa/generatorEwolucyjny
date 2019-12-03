package elements;

import app.World;
import map.MapDirection;
import map.Vector2d;
import map.IWorldMap;
import elements.Genotype;

import java.util.ArrayList;

public class Animal implements IMapElement, IPositionChangeObserver {

    public static int numberOfAnimals = 0;
    public Genotype genotype;
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;

    ArrayList<IPositionChangeObserver> observerCollection = new ArrayList<>();

    public Animal(){
        this.genotype = new Genotype();
        this.direction = this.genotype.getDirection();
        this.position = new Vector2d(2,2 );
        numberOfAnimals++;
    }

    public Animal(IWorldMap map){
        this.genotype = new Genotype();
        this.direction = this.genotype.getDirection();
        this.position = new Vector2d(2, 2);
        this.map = map;
        numberOfAnimals++;
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.genotype = new Genotype();
        this.direction = this.genotype.getDirection();
        this.position = initialPosition;
        this.map = map;
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
        this.positionChanged(this.position, this.position.add(this.direction.toUnitVector()));
        this.position = this.position.add(this.direction.toUnitVector());
        this.updateDirection();
        this.updatePosition();
    }

    private void updateDirection(){
        this.direction = this.genotype.getDirection();
    }
    private void updatePosition(){
        Vector2d newPosition = (new Vector2d((this.position.x + World.width)%(World.width),
                (this.position.y + World.height)%(World.height)));
        this.positionChanged(this.position, newPosition);

    }

    public void addObserver(IPositionChangeObserver observer){
        this.observerCollection.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observerCollection.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observerCollection) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

}
