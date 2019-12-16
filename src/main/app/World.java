package app;

import GUI.Frame;
import GUI.Panel;

import javax.swing.*;
import java.io.IOException;


public class World {

    public static int startSeed;
    public static int width;
    public static int height;
    public static int initialAnimalsNumber;
    public static int simulationLength;
    public static int delay;
    public static float startEnergy;
    public static float moveEnergy;
    public static float plantEnergy;
    public static float jungleRatio;
    public static float startGrassTuftsRatio;


    private Game game;
    private Frame frame;

    public World(){
        this.game = new Game(startSeed);
        this.frame = new Frame(game);
    }

    public static void main(String[] args) {
        try {
            JSONParser.readJSON();
            World world = new World();
            world.frame.start();

        }
        catch (IOException ex){
            System.out.println("Problem while reading a JSON file...");
        }
    }
}
