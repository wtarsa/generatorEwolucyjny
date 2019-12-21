package app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class JSONParser {

    public static int documentCount = 1;

    public static void readJSON() throws IOException {
        File file = new File("src/main/app/parameters.json");
        String content = FileUtils.readFileToString(file, "utf-8");
        JSONObject parameters = new JSONObject(content);
        World.startSeed = parameters.getInt("seed");
        World.width = parameters.getInt("width");
        World.height = parameters.getInt("height");
        World.startEnergy = parameters.getFloat("startEnergy");
        World.plantEnergy = parameters.getFloat("plantEnergy");
        World.moveEnergy = parameters.getFloat("moveEnergy");
        World.jungleRatio = parameters.getFloat("jungleRatio");
        World.startGrassTuftsRatio = parameters.getFloat("startGrassTuftsRatio");
        World.initialAnimalsNumber = parameters.getInt("initialAnimalsNumber");
        World.delay = parameters.getInt("delay");
        World.stat_days = parameters.getInt("stat_days");
    }

    public static void createJSON(float avg_animals, float avg_grasses, float avg_energy, float avg_age, float avg_children) throws IOException {
        JSONObject stat = new JSONObject();
        stat.put("average_animals_number", avg_animals);
        stat.put("average_grass_number", avg_grasses);
        stat.put("average_energy", avg_energy);
        stat.put("average_age", avg_age);
        stat.put("average_children_number", avg_children);


        File file = new File("stat" + documentCount);
        documentCount++;
        FileUtils.writeStringToFile(file, stat.toString(), "utf-8");
    }
}

/*https://www.codevoila.com/post/65/java-json-tutorial-and-example-json-java-orgjson*/