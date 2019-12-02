import map.Vector2d;
import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest {

    Vector2d v_1_2 = new Vector2d(1, 2);
    Vector2d v_3_6 = new Vector2d(3, 6);

    @Test
    public void equalsTest(){
        Assert.assertFalse(v_1_2.equals(null));
        Assert.assertFalse(v_3_6.equals(null));
        Assert.assertFalse(v_1_2.equals(v_3_6));
        Assert.assertTrue(v_1_2.equals(v_1_2));
    }

    @Test
    public void toStringTest(){
        Assert.assertEquals(v_1_2.toString(), "(1,2)");
        Assert.assertEquals(v_3_6.toString(), "(3,6)");
    }

    @Test
    public void precedesTest(){
        Assert.assertTrue(v_1_2.precedes(v_3_6));
        Assert.assertFalse(v_3_6.precedes(v_1_2));
    }

    @Test
    public void followsTest(){
        Assert.assertTrue(v_3_6.follows(v_1_2));
        Assert.assertFalse(v_1_2.follows(v_3_6));
    }

    @Test
    public void upperRightTest(){
        Assert.assertEquals(v_1_2.upperRight(v_3_6), new Vector2d(3, 6));
        Assert.assertNotEquals(v_1_2.upperRight(v_3_6), new Vector2d(1, 2));
    }

    @Test
    public void lowerLeftTest(){
        Assert.assertEquals(v_1_2.lowerLeft(v_3_6), new Vector2d(1, 2));
        Assert.assertNotEquals(v_1_2.lowerLeft(v_3_6), new Vector2d(3, 6));

    }

    @Test
    public void addTest(){
        Assert.assertEquals(v_1_2.add(v_3_6), new Vector2d(4, 8));
        Assert.assertNotEquals(v_1_2.add(v_3_6), new Vector2d(4, 3));

    }

    @Test
    public void subtractTest(){
        Assert.assertNotEquals(v_1_2.subtract(v_3_6), new Vector2d(3, 6));
        Assert.assertEquals(v_3_6.subtract(v_1_2), new Vector2d(2, 4));
    }

    @Test
    public void oppositeTest(){
        Assert.assertEquals(v_1_2.opposite(), new Vector2d(-1, -2));
        Assert.assertEquals(v_3_6.opposite(), new Vector2d(-3, -6));
    }




}
