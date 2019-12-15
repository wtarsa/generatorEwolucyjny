package GUI;

import app.Game;
import app.World;
import elements.Grass;
import map.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class Panel extends JPanel implements ActionListener {

    public ImageIcon titleImage;
    private Game game;
    private Graphics g;
    private int windowWidth;
    private int windowHeight;
    private int space;
    private int delay = 100;
    private Timer timer;

    public Panel(Game game) {
        this.game = game;
        this.timer = new Timer(delay, this);
    }


    public void paint(Graphics g) {
        this.g = g;
        this.windowWidth = getGameplayWindowWidth();
        this.windowHeight = getGameplayWindowHeight();
        this.space = 1280 - 22 - 2 * (windowWidth);
        // border for title image
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 1260, 36);

        // title image
        titleImage = new ImageIcon("src/assets/logo.png");
        titleImage.paintIcon(this, g, 475, 11);

        // border for first gameplay
        g.setColor(Color.BLACK);
        g.drawRect(10, 56, windowWidth, windowHeight);

        // background for first gameplay
        g.setColor(new Color(133, 87, 35));
        g.fillRect(11, 57, windowWidth - 1, windowHeight - 1);

        // border for second gameplay
        g.setColor(Color.BLACK);
        g.drawRect(10 + space + windowWidth, 56, windowWidth + 1, windowHeight + 1);

        // background for second gameplay
        g.setColor(new Color(133, 87, 35));
        g.fillRect(10 + space + windowWidth + 1, 57, windowWidth, windowHeight);

        //   paintVerticalLines(10, 56,  g, windowWidth, windowHeight);
        // paintHorizontalLines(10, 56, g, windowWidth, windowHeight);
        // paintVerticalLines(10+space+windowWidth, 56,  g, windowWidth, windowHeight);
        // paintHorizontalLines(10+space+windowWidth, 56, g, windowWidth, windowHeight);
        simulate();

    }

    public void simulate() {
        cleanBackground(g, windowWidth, windowHeight, space);
        drawGrass(g, 10, 56 + windowHeight - (windowHeight / World.height), windowWidth, windowHeight);
    }

    private void paintVerticalLines(int i1, int i2, Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        for (int i = 1; i < World.width; i++) {
            g.drawLine(i1 + ((width / World.width) * i), i2, i1 + ((width / World.width) * i), i2 + height);
        }
    }

    private void paintHorizontalLines(int i1, int i2, Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        for (int i = 1; i < World.height; i++) {
            g.drawLine(i1, i2 + ((height / World.height) * i), i1 + width, i2 + ((height / World.height) * i));
        }
    }

    private int getGameplayWindowWidth() {
        int size = 624;
        while (size % World.width != 0) size--;
        return size;
    }

    private int getGameplayWindowHeight() {
        int size = 624;
        while (size % World.height != 0) size--;
        return size;
    }

    private void drawGrass(Graphics g, int i, int i1, int width, int height) {
        ArrayList<Vector2d> grassPositions = new ArrayList<Vector2d>(this.game.map.tuftsMap.keySet());
        for (Vector2d position : grassPositions) {
            g.setColor(Color.GREEN);
            g.fillRect(i + position.x * (width / World.width) + 1, i1 - position.y * (height / World.height) + 1, width / World.width - 1, height / World.height - 1);
        }
    }

    private void cleanBackground(Graphics g, int windowWidth, int windowHeight, int space) {
        g.setColor(new Color(133, 87, 35));
        g.fillRect(11, 57, windowWidth - 1, windowHeight - 1);
        g.fillRect(10 + space + windowWidth + 1, 57, windowWidth, windowHeight);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        
    }
}
