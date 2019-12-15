package GUI;

import app.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private Game game;
    public Panel panel;

    public Frame(Game game){
        this.game = game;
        this.setBounds(10, 10, 1280, 1000);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        panel = new Panel(game);
        this.add(panel);
    }


}
