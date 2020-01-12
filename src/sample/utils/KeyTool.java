package sample.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class KeyTool {
    public static String getApiKey() {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("./src/sample/data/keys.json"));
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("Couldn't read file ./src/sample/data/keys.json. " +
                    "Please rename /data/keys-dist.json to /data/keys.json, and replace the ~ with your API key.");
        }

        JSONObject keysFile = (JSONObject) obj;
        String apiKey = (String) keysFile.get("GOOGLE_API_KEY");

        if (apiKey.trim().equals("~")) {
            throw new IllegalStateException("Please replace the ~ with your own API key in ./src/sample/data/keys.json.");
        }

        return apiKey;
    }
}
