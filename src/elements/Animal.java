package elements;

import map.MapDirection;
import map.Vector2d;
import map.IWorldMap;

import java.util.ArrayList;

public class Animal implements IMapElement, IPositionChangeObserver {

    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;

    private static final Vector2d v_0_0 = new Vector2d(0, 0);
    private static final Vector2d v_4_4 = new Vector2d(4, 4);

    ArrayList<IPositionChangeObserver> observerCollection = new ArrayList<>();

    public Animal(){
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2,2 );
    }

    public Animal(IWorldMap map){
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.orientation = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
    }

    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String toString(){
        switch(this.orientation){
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            default:
                return "";
        }
    }

    public void move(MoveDirection direction){
        if(direction == MoveDirection.LEFT){
            this.orientation = this.orientation.previous();
        }
        else if(direction == MoveDirection.RIGHT){
            this.orientation = this.orientation.next();
        }
        else if(direction == MoveDirection.FORWARD && this.map.canMoveTo(this.position.add(this.orientation.toUnitVector()))){
            this.positionChanged(this.position, this.position.add(this.orientation.toUnitVector()));
            this.position = this.position.add(this.orientation.toUnitVector());
        }
        else if(direction == MoveDirection.BACKWARD && this.map.canMoveTo(this.position.add(this.orientation.toUnitVector().opposite()))){
            this.positionChanged(this.position, this.position.add(this.orientation.toUnitVector().opposite()));
            this.position = this.position.add(this.orientation.toUnitVector().opposite());
        }
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
