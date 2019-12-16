package GUI;

import app.Game;
import app.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame/* implements ActionListener*/ {

    private Game game;
    public Panel panel;
    private Timer timer;
    private int delay;

    public Frame(Game game){
        this.game = game;
        this.setBounds(10, 10, 1280, 1000);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        panel = new Panel(game);
        this.add(panel);
      //  this.delay = 1000;
      //  this.timer = new Timer(delay, this);
    }

    public void start(){
        this.game.beginSimulation(World.initialAnimalsNumber, World.simulationLength);
      //  this.timer.start();

    }


  /*  @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.game.run();
        repaint();
    }*/
}
