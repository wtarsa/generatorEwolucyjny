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
    public static int delay;
    public static float startEnergy;
    public static float moveEnergy;
    public static float plantEnergy;
    public static float jungleRatio;
    public static float startGrassTuftsRatio;
    public static int stat_days;

    private Game game;
    private Game secondGame;
    private Frame frame;

    public World(){
        this.game = new Game(startSeed);
        this.secondGame = new Game(startSeed+initialAnimalsNumber+10);
        this.frame = new Frame(game, secondGame);
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
