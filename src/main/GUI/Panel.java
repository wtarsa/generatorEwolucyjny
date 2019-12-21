package GUI;

import app.Game;
import app.World;
import elements.Animal;
import map.Vector2d;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Panel extends JPanel implements ActionListener, KeyListener {

    public ImageIcon titleImage;
    private static final Color grassColor = new Color(19, 109, 21);
    private Game game;
    private Game secondGame;
    private Graphics g;
    private int windowWidth;
    private int windowHeight;
    private int cellWidth = 100;
    private int cellHeight = 100;
    private int space;
    private boolean spacePressed = false;
    private int delay;
    private Timer timer;


    public Panel(Game game, Game secondGame) {
        addKeyListener(this);
        this.game = game;
        this.secondGame = secondGame;
        this.delay = World.delay;
        this.timer = new Timer(delay, this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.mouseListener();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        this.windowWidth = getGameplayWindowSize(World.width);
        this.windowHeight = getGameplayWindowSize(World.height);
        this.cellHeight = windowHeight/World.height;
        this.cellWidth = windowWidth/World.width;
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
        g.drawString("day: " + game.day, 10, 30);

        this.timer.start();
        g.dispose();

    }

    private void drawStrings(Game game, int leftMargin){
        g.setColor(Color.BLACK);
        g.drawString("animals: " + game.map.vector2dToAnimal.getAnimals().size(), leftMargin, 700);
        g.drawString("grass: " + game.map.tuftsMap.getAllGrasses().size(), leftMargin, 720);
        g.drawString("dominant genotype: " + game.map.genotypeMap.mostCommonGenotype(), leftMargin, 740);
        g.drawString("average energy: " + game.averageAnimalEnergy, leftMargin, 760);
        g.drawString("average age(dead animals): "+ game.averageAnimalAge, leftMargin, 780);
        g.drawString("average child number: "+ game.averageChildNumber, leftMargin, 800);

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
        List<Vector2d> grassPositions = game.map.tuftsMap.getAllPositions();
        for (Vector2d position : grassPositions) {
            g.setColor(grassColor);
            g.fillRect(i + position.x * (width / World.width) + 1, i1 - position.y * (height / World.height) + 1, width / World.width - 1, height / World.height - 1);
        }
    }

    private void drawAnimals(Graphics g, int i, int i1, int width, int height, Game game) {
        List<Animal> animals = game.map.vector2dToAnimal.getAnimals();
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
        if(!spacePressed) {
            this.game.run();
            this.secondGame.run();
            repaint();
        }

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            if(!spacePressed){
                spacePressed = true;
            }
            else{
                spacePressed = false;
            }
            System.out.println("space");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }


    //wypisuje w konsoli genotyp wybranego zwierzęcia, nie zdążyłem tego podpiąć do visualizacji
    private void mouseListener(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                System.out.println(x + "," + y);
                int gameplayNumber = chooseGameplay(x, y);
                if(gameplayNumber == 1){
                    Vector2d position = getAnimalPosition(x, y, 10, 56+windowHeight);
                    if(game.map.vector2dToAnimal.containsAnimal(position)) showAnimalInfo(game, position, 10);
                }
                else if(gameplayNumber == 2){
                    Vector2d position = getAnimalPosition(x, y, 10+space+windowWidth, 56+windowHeight);
                    if(secondGame.map.vector2dToAnimal.containsAnimal(position)){
                        showAnimalInfo(secondGame, position, 10+space+windowWidth);
                    }
                }
            }
        });
    }

    private int chooseGameplay(int x, int y){
        if(x > 10 && y < 56+this.windowHeight && x < 10+this.windowWidth && y > 56){ return 1;}// from first gameplay
        else if(x > 10+space+this.windowWidth && y < 56+this.windowHeight && x < 10+(2*this.windowWidth)+space && y > 56){ return 2;} //from second gameplay
        return 0;
    }


    private Vector2d getAnimalPosition(int x, int y, int xg, int yg){
        int posX = -1;
        int posY = -1;
        while(xg+(this.cellWidth*posX) < x) posX++;
        while(yg-(this.cellHeight*posY) > y) posY++;
        return new Vector2d(posX-1, posY-1);
    }

    private void showAnimalInfo(Game game, Vector2d position, int leftMargin){
        List<Animal> animals = game.map.vector2dToAnimal.allAnimalsOnPosition(position);
        Animal animal = animals.get(0);
        for(Animal animal1: animals) {
            if(Double.compare(animal1.energy, animal.energy) > 0) animal = animal1;
        }
        System.out.println(animal.ID);
    }


}
