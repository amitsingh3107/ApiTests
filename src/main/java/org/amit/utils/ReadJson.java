package org.amit.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class ReadJson {
    private static JsonElement root;
    public static JsonObject object;

    public static Object[][] getData(String key, String jsonFilePath) {
        try {
            root = new JsonParser().parse(new FileReader(jsonFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        object = root.getAsJsonObject().get(key).getAsJsonObject();
        JsonArray cases = object.get("cases").getAsJsonArray();
        Object[][] data = new Object[cases.size()][1];
        for (int i = 0; i < cases.size(); i++) {
            JsonObject value = cases.get(i).getAsJsonObject();
            data[i][0] = value;
        }
        return data;
    }
}

