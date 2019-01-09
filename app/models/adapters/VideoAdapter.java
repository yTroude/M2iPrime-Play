package models.adapters;

import com.google.gson.*;
import models.Video;

import java.lang.reflect.Type;

public class VideoAdapter implements JsonSerializer<Video> {
    public static VideoAdapter instance;

    private VideoAdapter() {

    }

    public static VideoAdapter get() {
        if (instance == null) {
            instance = new VideoAdapter();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(Video video, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();

        obj.addProperty("titre", video.titre);
        obj.addProperty("desc", video.desc);
        obj.addProperty("categorie", video.categorie);
        obj.addProperty("format", video.format);

        return obj;
    }
}
