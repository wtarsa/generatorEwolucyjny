package app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class JSONHandler {

    public static void readJSON() throws IOException {
        File file = new File("src/app/parameters.json");
        String content = FileUtils.readFileToString(file, "utf-8");
        JSONObject parameters = new JSONObject(content);
        World.startSeed = parameters.getInt("seed");
    }
}

/*https://www.codevoila.com/post/65/java-json-tutorial-and-example-json-java-orgjson*/