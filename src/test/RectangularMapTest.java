import map.Vector2d;
import elements.Animal;
import map.IWorldMap;
import map.RectangularMap;
import org.junit.Assert;
import org.junit.Test;

public class RectangularMapTest {



    @Test
    public void objectAtTest(){
        IWorldMap map = new RectangularMap(5, 5);
        map.place(new Animal(map, new Vector2d(1,1)));
        Assert.assertNotEquals(map.objectAt(new Vector2d(1, 1)), null);
    }

    @Test
    public void isOccupiedTest(){
        IWorldMap map = new RectangularMap(5, 5);
        map.place(new Animal(map, new Vector2d(1,1)));
        Assert.assertTrue(map.isOccupied(new Vector2d(1, 1)));
        Assert.assertFalse(map.isOccupied(new Vector2d(1, 2)));
    }


}
