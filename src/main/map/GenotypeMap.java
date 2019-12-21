package map;


import elements.Animal;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GenotypeMap {

    private MultiValuedMap<String, Animal> genotypeMap;

    public GenotypeMap(){
        this.genotypeMap = new ArrayListValuedHashMap<>();
    }

    public void add(String id, Animal animal){
        synchronized (this.genotypeMap) {
            this.genotypeMap.put(id, animal);
        }
    }

    public void remove(String id, Animal animal){
        synchronized (this.genotypeMap){
            this.genotypeMap.removeMapping(id, animal);
        }
    }

    private int animalsWithTheSameGenotype(String genotype){
        return this.genotypeMap.get(genotype).size();
    }

    public String mostCommonGenotype(){
        synchronized (this.genotypeMap){
            List<String> allGenotypes = new CopyOnWriteArrayList<>(this.genotypeMap.keySet());
            int max = -1;
            String dominantGenotype = "none";
            for(String genotype: allGenotypes){
                if(animalsWithTheSameGenotype(genotype) > max){
                    max = animalsWithTheSameGenotype(genotype);
                    dominantGenotype = genotype;
                }
            }
            return dominantGenotype;
        }
    }

    public List<Animal> getAnimals(String genotype){
        synchronized (this.genotypeMap){
            List<Animal> animals = new CopyOnWriteArrayList<>(this.genotypeMap.values());
            return animals;
        }
    }
}
