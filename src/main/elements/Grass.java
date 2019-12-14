package elements;

import map.Jungle;
import map.Vector2d;

public class Grass implements IMapElement {

    private Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }

    @Override
    public boolean belongsToJungle(Jungle jungle){
        return this.position.follows(jungle.lowerLeftCorner) && this.position.precedes(jungle.upperRightCorner);
    }

    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String toString(){
        return "*";
    }

}
