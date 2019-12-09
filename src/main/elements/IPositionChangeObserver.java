package elements;

import map.Vector2d;

public interface IPositionChangeObserver {

    public void positionChanged(String id, Vector2d oldPosition, Vector2d newPosition);

}
