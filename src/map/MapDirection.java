package map;

public enum MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public String toString(){
        switch (this){
            case EAST:
                return "Wschód";
            case WEST:
                return "Zachód";
            case NORTH:
                return "Północ";
            case SOUTH:
                return "Południe";
            default:
                return null;
        }
    }

    public MapDirection next(){
        switch (this){
            case EAST:
                return MapDirection.SOUTH;
            case WEST:
                return MapDirection.NORTH;
            case NORTH:
                return MapDirection.EAST;
            case SOUTH:
                return MapDirection.WEST;
            default:
                return null;
        }
    }

    public MapDirection previous(){
        switch (this){
            case EAST:
                return MapDirection.NORTH;
            case WEST:
                return MapDirection.SOUTH;
            case NORTH:
                return MapDirection.WEST;
            case SOUTH:
                return MapDirection.EAST;
            default:
                return null;
        }
    }

    public Vector2d toUnitVector(){
        switch (this){
            case EAST:
                return new Vector2d(1, 0);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTH:
                return new Vector2d(0, 1);
            case SOUTH:
                return new Vector2d(0, -1);
            default:
                return new Vector2d(0, 0);
        }
    }
}
