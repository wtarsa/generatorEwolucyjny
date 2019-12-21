package GUI;

import app.Game;
import app.World;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    private Game game;
    private Game secondGame;
    public Panel panel;

    public Frame(Game game, Game secondGame){
        this.game = game;
        this.secondGame = secondGame;
        this.setTitle("evolutionary_generator");
        this.setBounds(10, 10, 1280, 1000);
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.panel = new Panel(game, secondGame);
        this.panel.setFocusable(true);
        this.add(panel);

        //setVisible must be on the end!
        this.setVisible(true);

    }

    public void start(){
        this.game.beginSimulation(World.initialAnimalsNumber);
        this.secondGame.beginSimulation(World.initialAnimalsNumber);

    }

}
