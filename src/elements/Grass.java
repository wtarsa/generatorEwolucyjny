package elements;

import agh.cs.lab2.Vector2d;

public class Grass implements IMapElement {

    private Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
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
