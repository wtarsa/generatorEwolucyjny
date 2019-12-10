package map;

import app.World;

public class Jungle {

    public int width;
    public int height;
    private double jungleArea = World.width*World.height*World.jungleRatio;

    public Jungle(){
        if(World.width > World.height) {
            this.width = (int) Math.ceil(Math.sqrt(jungleArea));
            this.height = (int) Math.floor(Math.sqrt(jungleArea));
        } else{
            this.height = (int) Math.ceil(Math.sqrt(jungleArea));
            this.width = (int) Math.floor(Math.sqrt(jungleArea));
        }
    }
}
