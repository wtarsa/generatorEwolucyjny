package elements;

import app.Game;
import app.World;
import map.*;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements IMapElement, IPositionChangeObserver {

    public Genotype genotype;
    public float energy;
    public String ID;
    protected MapDirection direction;
    public Vector2d position;
    public Game game;
    public int age;
    public int children;

    ArrayList<IPositionChangeObserver> observerCollection = new ArrayList<>();

    public Animal(Game game){
        this.age = 0;
        this.children = 0;
        this.game = game;
        this.genotype = new Genotype(game);
        this.direction = this.genotype.getDirection();
        this.position = placeAnimal() ;
        this.energy = World.startEnergy;
        this.ID = this.genotype.getID();
        this.game.numberOfAnimals++;
    }

    public Animal(Animal parent1, Animal parent2, Vector2d position){
        this.age = 0;
        this.game = parent1.game;
        this.genotype = new Genotype(parent1.genotype, parent2.genotype, game);
        this.direction = this.genotype.getDirection();
        this.position = position;
        this.energy = (float) (0.25*parent1.energy + 0.25*parent2.energy);
        this.ID = this.genotype.getID();
        updateParent(parent1);
        updateParent(parent2);
        this.game.numberOfAnimals++;
    }

    private void updateParent(Animal animal){
        animal.children++;
        animal.energy = (float) (0.75*animal.energy);
    }

    @Override
    public boolean belongsToJungle(Jungle jungle){
        return this.position.follows(jungle.lowerLeftCorner) && this.position.precedes(jungle.upperRightCorner);
    }

    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String toString(){
        switch(this.direction){
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            case NORTHWEST:
                return "NW";
            case NORTHEAST:
                return "NE";
            case SOUTHEAST:
                return "SE";
            case SOUTHWEST:
                return "SW";
            default:
                return "";
        }
    }

    public void move() {
        Vector2d newPosition = this.position.add(this.direction.toUnitVector()).replaceOnMap();
        this.positionChanged(this, this.position, newPosition);
        this.position = this.position.add(this.direction.toUnitVector()).replaceOnMap();
        this.updateDirection();
    }

    private void updateDirection(){
        int newDirection = this.genotype.getDirectionInt();
        for(int i = 0; i < newDirection; i++){
            this.direction = this.direction.next();
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observerCollection.add(observer);
    }


    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observerCollection) {
            observer.positionChanged(animal, oldPosition, newPosition);
        }
    }

    private Vector2d placeAnimal(){
        Vector2d position;
        Random rand = new Random(this.game.seed + this.game.numberOfAnimals);
        do {
            position = new Vector2d(rand.nextInt(World.width), rand.nextInt(World.height));
        } while (this.game.map.vector2dToAnimal.containsAnimal(position));
        return position;
    }

//reproducting

    public boolean enoughEnergyToReproduce(Animal animal){
        return ((this.energy > (World.startEnergy/2))&&(animal.energy > (World.startEnergy/2)));
    }

    public Animal reproduce(Animal animal, Vector2d position){
        return new Animal(this, animal, position);
    }

}
