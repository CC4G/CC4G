package cn.frish2021.gradle.Game;

import cc4g.extensions.CC4GExtension;
import cn.frish2021.gradle.FileUtil;
import cn.frish2021.gradle.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;

public class AssetsUtil
{
    public static String getClientAssets(CC4GExtension extension) {
        return FileUtil.readString(new Gson().fromJson(JsonUtil.getJson(extension.gameVersion), JsonObject.class).get("assetIndex").getAsJsonObject().get("url").getAsString());
    }

    public static File getClientAssetsDir(CC4GExtension extension) {
        File assets = new File(System.getProperty("user.dir") + "/runs", "assets");
        if (!assets.exists()) {
            assets.mkdir();
        }
        return assets;
    }

    public static File getClientAssetsIndexesDir(CC4GExtension extension) {
        File indexes = new File(getClientAssetsDir(extension), "indexes");
        if (!indexes.exists()) {
            indexes.mkdir();
        }
        return indexes;
    }

    public static File getClientAssetsObjectsDir(CC4GExtension extension) {
        File objects = new File(getClientAssetsDir(extension), "objects");
        if (!objects.exists()) {
            objects.mkdir();
        }
        return objects;
    }

    public static File getClientAssetsSkinsDir(CC4GExtension extension) {
        File objects = new File(getClientAssetsDir(extension), "skins");
        if (!objects.exists()) {
            objects.mkdir();
        }
        return objects;
    }

    public static File getClientAssetsIndexFile(CC4GExtension extension) {
        return new File(getClientAssetsIndexesDir(extension), extension.gameVersion + ".json");
    }

    public static File getClientAssetsObjectFile(CC4GExtension extension, String name) {
        File file = new File(getClientAssetsObjectsDir(extension), name.substring(0, 2));
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(file, name);
    }

    public static File getLocalClientAssetsDir() {
        return new File(System.getProperty("user.dir") + "/runs", "assets");
    }

    public static File getLocalClientAssetsObjectsDir() {
        return new File(getLocalClientAssetsDir(), "objects");
    }

    public static File getLocalClientAssetsObjectFile(String name) {
        return new File(getLocalClientAssetsObjectsDir(), name.substring(0, 2) + "/" + name);
    }

    public static File getLocalClientAssetsSkinsDir() {
        return new File(getLocalClientAssetsDir(), "skins");
    }
}
