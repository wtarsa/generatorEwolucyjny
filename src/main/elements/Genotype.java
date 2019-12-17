package elements;

import app.Game;
import map.MapDirection;
import app.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



public class Genotype {

    private Integer[] geneticCode = new Integer[32];
    private Game game;
    private Random rand;

    public Genotype(Game game){
        this.game = game;
        rand = new Random(this.game.seed+game.numberOfAnimals);
        for(int i = 0; i < 32; i++){
            this.geneticCode[i] = rand.nextInt(8);
        }
        geneticCodeCheck();
    }

    public Genotype(Genotype first, Genotype second, Game game){
        this.game = game;
        rand = new Random(this.game.seed+game.numberOfAnimals);
        int checkPoint1 = 3+rand.nextInt(13);
        int checkPoint2 = 19+rand.nextInt(13);
        for(int i = 0; i < checkPoint1; i++){
            this.geneticCode[i] = first.geneticCode[i];
        }
        for(int i = checkPoint1; i < checkPoint2; i++){
            this.geneticCode[i] = second.geneticCode[i];
        }
        for(int i = checkPoint2; i < 32; i++){
            this.geneticCode[i] = first.geneticCode[i];
        }
        geneticCodeCheck();
    }


    private void geneticCodeCheck(){
        List<LinkedList<Integer>> counter = new ArrayList<LinkedList<Integer>>();
        for(int i = 0; i < 8; i++){
            counter.add(new LinkedList<Integer>());
        }
        int maxOccurrence = 0;
        int mostCommonValue = 0;
        for(int i = 0; i < 32; i++){
            counter.get(this.geneticCode[i]).add(i);
            if(maxOccurrence < counter.get(this.geneticCode[i]).size()){
                maxOccurrence = counter.get(this.geneticCode[i]).size();
                mostCommonValue = this.geneticCode[i];
            }
        }
        for(int i = 0; i < 8; i++){
            if(counter.get(i).size() == 0){
                counter.get(i).add(counter.get(mostCommonValue).removeLast());
                this.geneticCode[counter.get(i).getFirst()] = i;
            }
        }
    }

    public int getDirectionInt(){
        return this.geneticCode[rand.nextInt(32)];
    }

    public MapDirection getDirection(){
        int id = this.geneticCode[rand.nextInt(32)];
        switch (id) {
            case 0:
                return MapDirection.NORTH;
            case 1:
                return MapDirection.NORTHEAST;
            case 2:
                return MapDirection.EAST;
            case 3:
                return MapDirection.SOUTHEAST;
            case 4:
                return MapDirection.SOUTH;
            case 5:
                return MapDirection.SOUTHWEST;
            case 6:
                return MapDirection.WEST;
            case 7:
                return MapDirection.NORTHWEST;
            default:
                return null;
        }

    }

    public void showGenotype(){
        for(int i = 0; i < 32; i++){
            System.out.print(this.geneticCode[i] + " ");
        }
        System.out.println();
    }

    public String getID(){
        String ID = "";
        for(int i = 0; i < 32; i++){
            ID += this.geneticCode[i];
        }
        return ID;
    }
}
