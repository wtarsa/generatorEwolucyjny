package GUI;

import app.Game;
import app.World;
import elements.Animal;
import elements.Grass;
import map.Vector2d;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Panel extends JPanel implements ActionListener {

    public ImageIcon titleImage;
    private static final Color grassColor = new Color(19, 109, 21);
    private Game game;
    private Game secondGame;
    private Graphics g;
    private int windowWidth;
    private int windowHeight;
    private int space;

    private int delay;
    private Timer timer;


    public Panel(Game game, Game secondGame) {
        this.game = game;
        this.secondGame = secondGame;
        this.delay = World.delay;
        this.timer = new Timer(delay, this);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        this.windowWidth = getGameplayWindowSize(World.width);
        this.windowHeight = getGameplayWindowSize(World.height);
        this.space = 1280 - 22 - 2 * (windowWidth);

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

        cleanBackground(g, windowWidth, windowHeight, space);
        simulate(this.game, 10, 56 + windowHeight - (windowHeight / World.height));
        simulate(this.secondGame, 10+windowWidth+space, 56 + windowHeight - (windowHeight / World.height));

        drawStrings(this.game, 10);
        drawStrings(this.secondGame, 10+windowWidth+space);

        this.timer.start();
    }

    private void drawStrings(Game game, int leftMargin){
        synchronized (game.map.tuftsMap) {

            g.setColor(Color.BLACK);
            g.drawString("animals: " + game.map.vector2dToAnimal.values().size(), leftMargin, 700);
            g.drawString("grass: " + game.map.tuftsMap.values().size(), leftMargin, 720);
        }
    }

    private void simulate(Game game, int i1, int i2) {
        drawGrass(g, i1, i2, windowWidth, windowHeight, game);
        drawAnimals(g, i1, i2, windowWidth, windowHeight, game);
    }

    private int getGameplayWindowSize(int x) {
        int size = 624;
        while (size % x != 0) size--;
        return size;
    }

    private void drawGrass(Graphics g, int i, int i1, int width, int height, Game game) {
        List<Vector2d> grassPositions = new ArrayList<>();
        synchronized (game.map.tuftsMap.keySet()){
            grassPositions.addAll(game.map.tuftsMap.keySet());
        }
        for (Vector2d position : grassPositions) {
            g.setColor(grassColor);
            g.fillRect(i + position.x * (width / World.width) + 1, i1 - position.y * (height / World.height) + 1, width / World.width - 1, height / World.height - 1);
        }
    }

    private void drawAnimals(Graphics g, int i, int i1, int width, int height, Game game) {
        List<Animal> animals = new ArrayList<Animal>();
        synchronized (game.map.vector2dToAnimal){
            animals.addAll(game.map.vector2dToAnimal.values());
        }
        for (Animal animal: animals) {
            g.setColor(getAnimalColor(animal.energy));
            g.fillRect(i + animal.position.x * (width / World.width) + 1, i1 - animal.position.y * (height / World.height) + 1, width / World.width - 1, height / World.height - 1);
        }
    }

    private void cleanBackground(Graphics g, int windowWidth, int windowHeight, int space) {
        g.setColor(new Color(133, 87, 35));
        g.fillRect(11, 57, windowWidth - 1, windowHeight - 1);
        g.fillRect(10 + space + windowWidth + 1, 57, windowWidth, windowHeight);
    }

    private Color getAnimalColor(double energy){
        if(Double.compare(energy, 5*World.startEnergy) > 0) return new Color(28, 0, 85);
        else if(Double.compare(energy, 4*World.startEnergy) > 0) return new Color(12, 0, 104);
        else if(Double.compare(energy, 3*World.startEnergy) > 0) return new Color(0, 3, 123);
        else if(Double.compare(energy, 2*World.startEnergy) > 0) return new Color(0, 36, 141);
        else if(Double.compare(energy, World.startEnergy) >= 0) return new Color(0, 76, 158);
        else if(Double.compare(7*energy, 6*World.startEnergy) > 0) return new Color(34, 126, 173);
        else if(Double.compare(7*energy, 5*World.startEnergy) > 0) return new Color(68, 170, 188);
        else if(Double.compare(7*energy, 4*World.startEnergy) > 0) return new Color(102, 202, 197);
        else if(Double.compare(7*energy, 3*World.startEnergy) > 0) return new Color(136, 215, 196);
        else if(Double.compare(7*energy, 2*World.startEnergy) > 0) return new Color(170, 227, 202);
        else return new Color(204, 239, 216);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.game.run();
        this.secondGame.run();
        repaint();
    }
}
