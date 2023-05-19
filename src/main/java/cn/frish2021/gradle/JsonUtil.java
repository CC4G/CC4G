package cn.frish2021.gradle;

import cn.frish2021.gradle.url.GameUrl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil {
    public static String getJson(String version) {
        String jsonUrl = "";
        for (JsonElement versions : new Gson().fromJson(FileUtil.readString(GameUrl.GAME_URL), JsonObject.class).get("versions").getAsJsonArray()) {
            if (versions.getAsJsonObject().get("id").getAsString().equals(version)) {
                jsonUrl = versions.getAsJsonObject().get("url").getAsString();
            }
        }
        return FileUtil.readString(jsonUrl);
    }
}
