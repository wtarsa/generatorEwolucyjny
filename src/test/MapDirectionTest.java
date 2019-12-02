import map.MapDirection;
import org.junit.Test;
import org.junit.Assert;

public class MapDirectionTest {

    @Test
    public void nextTest(){
        MapDirection tmp = MapDirection.NORTH;
        Assert.assertEquals(MapDirection.NORTHEAST, tmp.next());
        tmp = MapDirection.NORTHEAST;
        Assert.assertEquals(MapDirection.EAST, tmp.next());
        tmp = MapDirection.EAST;
        Assert.assertEquals(MapDirection.SOUTHEAST, tmp.next());
        tmp = MapDirection.SOUTHEAST;
        Assert.assertEquals(MapDirection.SOUTH, tmp.next());
        tmp = MapDirection.SOUTH;
        Assert.assertEquals(MapDirection.SOUTHWEST, tmp.next());
        tmp = MapDirection.SOUTHWEST;
        Assert.assertEquals(MapDirection.WEST, tmp.next());
        tmp = MapDirection.WEST;
        Assert.assertEquals(MapDirection.NORTHWEST, tmp.next());
        tmp = MapDirection.NORTHWEST;
        Assert.assertEquals(MapDirection.NORTH, tmp.next());
    }

    @Test
    public void previousTest(){
        MapDirection tmp = MapDirection.NORTH;
        Assert.assertEquals(MapDirection.NORTHWEST, tmp.previous());
        tmp = MapDirection.NORTHWEST;
        Assert.assertEquals(MapDirection.WEST, tmp.previous());
        tmp = MapDirection.WEST;
        Assert.assertEquals(MapDirection.SOUTHWEST, tmp.previous());
        tmp = MapDirection.SOUTHWEST;
        Assert.assertEquals(MapDirection.SOUTH, tmp.previous());
        tmp = MapDirection.SOUTH;
        Assert.assertEquals(MapDirection.SOUTHEAST, tmp.previous());
        tmp = MapDirection.SOUTHEAST;
        Assert.assertEquals(MapDirection.EAST, tmp.previous());
        tmp = MapDirection.EAST;
        Assert.assertEquals(MapDirection.NORTHEAST, tmp.previous());
        tmp = MapDirection.NORTHEAST;
        Assert.assertEquals(MapDirection.NORTH, tmp.previous());

    }
}
