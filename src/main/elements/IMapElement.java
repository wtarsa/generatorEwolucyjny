package elements;

import map.Jungle;
import map.Vector2d;

public interface IMapElement {

    public Vector2d getPosition();
    public String toString();
    public boolean belongsToJungle(Jungle jungle);
}
