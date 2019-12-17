package elements;

import app.Game;
import app.World;
import map.*;
import elements.Genotype;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements IMapElement, IPositionChangeObserver {

    public Genotype genotype;
    public float energy;
    public String ID;
    protected MapDirection direction;
    public Vector2d position;
    public Game game;

    ArrayList<IPositionChangeObserver> observerCollection = new ArrayList<>();

    public Animal(Game game){
        this.game = game;
        this.genotype = new Genotype(game);
        this.direction = this.genotype.getDirection();
        this.position = placeAnimal() ;
        this.energy = World.startEnergy;
        this.ID = this.genotype.getID();
        this.game.numberOfAnimals++;
    }

    public Animal(Animal parent1, Animal parent2, Vector2d position){
        this.game = parent1.game;
        this.genotype = new Genotype(parent1.genotype, parent2.genotype, game);
        this.direction = this.genotype.getDirection();
        this.position = position;
        this.energy = (float) (0.25*parent1.energy + 0.25*parent2.energy);
        this.ID = this.genotype.getID();
        parent1.energy = (float) (0.75*parent1.energy);
        parent2.energy = (float) (0.75*parent2.energy);
        this.game.numberOfAnimals++;
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
      //  if(this.game.map.canMoveTo(this.position, this.position.add(this.direction.toUnitVector()))) {
            Vector2d newPosition = this.position.add(this.direction.toUnitVector()).replaceOnMap();
            this.positionChanged(this, this.position, newPosition);
            this.position = this.position.add(this.direction.toUnitVector()).replaceOnMap();
            this.updateDirection();
          //  this.updatePosition();
        //}
    }

    private void updateDirection(){
        int newDirection = this.genotype.getDirectionInt();
        for(int i = 0; i < newDirection; i++){
            this.direction = this.direction.next();
        }
    }

    private void updatePosition(){
        Vector2d newPosition = (new Vector2d((this.position.x + World.width)%(World.width),
                (this.position.y + World.height)%(World.height)));
        this.positionChanged(this, this.position, newPosition);

    }

    public void addObserver(IPositionChangeObserver observer){
        this.observerCollection.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observerCollection.remove(observer);
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observerCollection) {
            observer.positionChanged(animal, oldPosition, newPosition);
        }
    }

    private Vector2d placeAnimal(){
        Vector2d position;
        Random rand = new Random(World.startSeed+this.game.numberOfAnimals);
        do{
            position = new Vector2d(rand.nextInt(World.width),rand.nextInt(World.height));
        } while(this.game.map.vector2dToAnimal.containsKey(position));
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
