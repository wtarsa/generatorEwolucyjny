package map;

import app.World;

public class Jungle {

    public int emptyPlaces;
    private int width;
    private int height;
    public int size;
    public Vector2d lowerLeftCorner;
    public Vector2d upperRightCorner;
    public int grassNumber = 0;
    private double jungleArea = World.width*World.height*World.jungleRatio;

    public Jungle(){
        if(World.width > World.height) {
            this.width = (int) Math.ceil(Math.sqrt(jungleArea));
            this.height = (int) Math.floor(Math.sqrt(jungleArea));
        } else{
            this.height = (int) Math.ceil(Math.sqrt(jungleArea));
            this.width = (int) Math.floor(Math.sqrt(jungleArea));
        }
        lowerLeftCorner = new Vector2d((World.width-this.width)/2, (World.height-this.height)/2);
        upperRightCorner = lowerLeftCorner.add(new Vector2d(this.width, this.height));
        this.emptyPlaces = (this.width-1)*(this.height-1);
    }

}
