package app;

import elements.Animal;
import map.GrassField;
import map.MapDirection;
import map.Vector2d;

import java.io.IOException;
import java.util.ArrayList;


public class World {

    public static int startSeed;
    public static int width;
    public static int height;
    public static int initialAnimalsNumber;
    public static int simulationLength;
    public static float startEnergy;
    public static float moveEnergy;
    public static float plantEnergy;
    public static float jungleRatio;
    public static float startGrassTuftsRatio;


    private Game game;

    public World(){
        this.game = new Game(startSeed);
    }

    public static void main(String[] args) {
        try {
            JSONParser.readJSON();
            World world = new World();
            world.game.beginSimulation(initialAnimalsNumber, simulationLength);
        }
        catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }
}
