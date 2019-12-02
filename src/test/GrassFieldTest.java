import map.Vector2d;
import elements.Animal;
import map.IWorldMap;
import elements.GrassField;
import org.junit.Assert;
import org.junit.Test;

public class GrassFieldTest {


    @Test
    public void objectAtTest(){
        IWorldMap field = new GrassField( 5);
        field.place(new Animal(field, new Vector2d(1,1)));
        Assert.assertNotEquals(field.objectAt(new Vector2d(1, 1)), null);
    }

    @Test
    public void isOccupiedTest(){
        IWorldMap field = new GrassField( 5);
        field.place(new Animal(field, new Vector2d(1,1)));
        Assert.assertTrue(field.isOccupied(new Vector2d(1, 1)));
        Assert.assertFalse(field.isOccupied(new Vector2d(1, 2)));
    }

    @Test
    public void tuftNumberTest(){
        GrassField field = new GrassField(13);
        field.placeGrassTufts(50);
        Assert.assertEquals(13, field.tuftsMap.size());
        Assert.assertNotEquals(12, field.tuftsMap.size());
    }
}
