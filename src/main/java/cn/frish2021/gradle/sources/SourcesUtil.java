package cn.frish2021.gradle.sources;

import cc4g.extensions.CC4GExtension;
import cn.frish2021.gradle.FileUtil;
import cn.frish2021.gradle.url.GameUrl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SourcesUtil {
    public static byte[] getClientSources(CC4GExtension extension) {
        return FileUtil.readFile(new Gson().fromJson(FileUtil.readString(GameUrl.GAME_SOURCES_LIST), JsonObject.class).get("sources").getAsJsonObject().get(extension.gameVersion).getAsString());
    }

    public static byte[] getClientResources(CC4GExtension extension) {
        return FileUtil.readFile(new Gson().fromJson(FileUtil.readString(GameUrl.GAME_SOURCES_LIST), JsonObject.class).get("resources").getAsJsonObject().get(extension.gameVersion).getAsString());
    }
}
