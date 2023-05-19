package cn.frish2021.gradle.Game;

import cc4g.extensions.CC4GExtension;
import cn.frish2021.gradle.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LibrariesUtil {
    public static List<String> getLibraries(CC4GExtension extension)
    {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        for (JsonElement jsonElement : new Gson().fromJson(JsonUtil.getJson(extension.gameVersion), JsonObject.class).get("libraries").getAsJsonArray()) {
            if (jsonElement.getAsJsonObject().has("natives")) {
                continue;
            }

            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            linkedHashMap.put(name.substring(0, name.lastIndexOf(":")), name.substring(name.lastIndexOf(":")));
        }
        List<String> libraries = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : linkedHashMap.entrySet()) {
            libraries.add(stringStringEntry.getKey() + stringStringEntry.getValue());
        }
        return libraries;
    }

    public static List<String> getNatives(CC4GExtension extension)
    {
        List<String> libraries = new ArrayList<>();
        for (JsonElement jsonElement : new Gson().fromJson(JsonUtil.getJson(extension.gameVersion), JsonObject.class).get("libraries").getAsJsonArray()) {
            JsonObject downloads = jsonElement.getAsJsonObject().get("downloads").getAsJsonObject();
            if (downloads.has("classifiers")) {
                String name = "natives-linux";
                if (GameUtil.getOsName().contains("win")) {
                    name = "natives-windows";
                } else if (GameUtil.getOsName().contains("mac")) {
                    name = "natives-macos";
                }
                JsonObject classifiers = downloads.get("classifiers").getAsJsonObject();
                if (classifiers.has(name)) {
                    libraries.add(downloads.get("classifiers").getAsJsonObject().get(name).getAsJsonObject().get("url").getAsString());
                }
            }
        }
        return libraries;
    }
}
