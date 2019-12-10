package app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class JSONParser {

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


    }
}

/*https://www.codevoila.com/post/65/java-json-tutorial-and-example-json-java-orgjson*/