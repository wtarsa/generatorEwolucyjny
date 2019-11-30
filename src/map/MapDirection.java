package map;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

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
            case SOUTHEAST:
                return "Południowy-Wschód";
            case SOUTHWEST:
                return "Południowy-Zachód";
            case NORTHWEST:
                return "Północny-Zachód";
            case NORTHEAST:
                return "Północny-Wschód";
            default:
                return null;
        }
    }

    public MapDirection next(){
        switch (this){
            case NORTH:
                return MapDirection.NORTHEAST;
            case NORTHEAST:
                return MapDirection.EAST;
            case EAST:
                return MapDirection.SOUTHEAST;
            case SOUTHEAST:
                return MapDirection.SOUTH;
            case SOUTH:
                return MapDirection.SOUTHWEST;
            case SOUTHWEST:
                return MapDirection.WEST;
            case WEST:
                return MapDirection.NORTHWEST;
            case NORTHWEST:
                return MapDirection.NORTH;
            default:
                return null;
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH:
                return MapDirection.NORTHWEST;
            case NORTHWEST:
                return MapDirection.WEST;
            case WEST:
                return MapDirection.SOUTHWEST;
            case SOUTHWEST:
                return MapDirection.SOUTH;
            case SOUTH:
                return MapDirection.SOUTHEAST;
            case SOUTHEAST:
                return MapDirection.EAST;
            case EAST:
                return MapDirection.NORTHEAST;
            case NORTHEAST:
                return MapDirection.NORTH;
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
            case NORTHEAST:
                return new Vector2d(1, 1);
            case NORTHWEST:
                return new Vector2d(-1, 1);
            case SOUTHEAST:
                return new Vector2d(1, -1);
            case SOUTHWEST:
                return new Vector2d(-1, -1);
            default:
                return new Vector2d(0, 0);
        }
    }
}
