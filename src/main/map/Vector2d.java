package map;

import app.World;

public class Vector2d {

    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d that){
        return (this.x < that.x && this.y < that.y);
    }

    public boolean follows(Vector2d that){
        return (this.x > that.x && this.y > that.y);
    }

    public Vector2d upperRight(Vector2d that){
        Vector2d result = new Vector2d(Math.max(that.x, this.x), Math.max(that.y, this.y));
        return result;
    }

    public Vector2d lowerLeft(Vector2d that){
        Vector2d result = new Vector2d(Math.min(that.x, this.x), Math.min(that.y, this.y));
        return result;
    }

    public Vector2d add(Vector2d that){
        Vector2d result = new Vector2d(this.x + that.x, this.y + that.y);
        return result;
    }

    public Vector2d subtract(Vector2d that){
        Vector2d result = new Vector2d(this.x - that.x, this.y - that.y);
        return result;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        int hash = 2137;
        hash += (this.x * 31);
        hash += (this.y * 17);
        return hash;

    }

    public Vector2d opposite(){
        Vector2d result = new Vector2d(this.x*(-1), this.y*(-1));
        return result;
    }

    public Vector2d replaceOnMap(){
        return new Vector2d((this.x + World.width)%World.width, (this.y + World.height)%World.height);
    }
}
