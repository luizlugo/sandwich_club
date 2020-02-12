package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        try {
            JSONObject sandwichJsonObject = new JSONObject(json);
            JSONObject nameObject = sandwichJsonObject.getJSONObject("name");
            JSONArray alsoKnownAsObject = nameObject.getJSONArray("alsoKnownAs");
            JSONArray ingredientsObject = sandwichJsonObject.getJSONArray("ingredients");
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();

            for (int i=0; i<ingredientsObject.length(); i++) {
                String name = ingredientsObject.getString(i);
                ingredients.add(name);
            }

            for (int i=0; i<alsoKnownAsObject.length(); i++) {
                String name = alsoKnownAsObject.getString(i);
                alsoKnownAs.add(name);
            }

            sandwich = new Sandwich(nameObject.getString("mainName"), alsoKnownAs, sandwichJsonObject.getString("placeOfOrigin"), sandwichJsonObject.getString("description"), sandwichJsonObject.getString("image"), ingredients);
        } catch (Exception e) {
            return null;
        }

        return sandwich;
    }
}
