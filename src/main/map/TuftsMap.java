package map;

import elements.Grass;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TuftsMap {

    private LinkedHashMap<Vector2d, Grass> tuftsMap;

    public TuftsMap(){
        this.tuftsMap = new LinkedHashMap<Vector2d, Grass>();
    }

    public boolean containsGrass(Vector2d position){
        synchronized (this.tuftsMap) {
            return this.tuftsMap.containsKey(position);
        }
    }

    public void placeGrass(Vector2d position, Grass grass){
        synchronized (this.tuftsMap) {
            this.tuftsMap.put(position, grass);
        }
    }

    public void removeGrass(Vector2d position){
        synchronized (this.tuftsMap){
            this.tuftsMap.remove(position);
        }
    }

    public Grass getGrass(Vector2d position) {
        synchronized (this.tuftsMap) {
            return this.tuftsMap.get(position);
        }
    }

    public List<Vector2d> getAllPositions(){
        synchronized (this.tuftsMap) {
            List<Vector2d> positions = new CopyOnWriteArrayList<>();
            positions.addAll(this.tuftsMap.keySet());
            return positions;
        }
    }

    public List<Grass> getAllGrasses(){
        synchronized (this.tuftsMap){
            List<Grass> grasses = new CopyOnWriteArrayList<>();
            grasses.addAll(this.tuftsMap.values());
            return grasses;
        }
    }
}
