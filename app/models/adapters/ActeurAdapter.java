package models.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Acteur;

import java.lang.reflect.Type;

public class ActeurAdapter implements JsonSerializer<Acteur> {

    public static ActeurAdapter instance;

    private ActeurAdapter() {

    }

    public static ActeurAdapter get() {
        if (instance == null) {
            instance = new ActeurAdapter();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(Acteur acteur, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();

        obj.addProperty("nomActeur", acteur.nomActeur);
        obj.addProperty("prenomActeur", acteur.prenomActeur);


        return obj;
    }



}
