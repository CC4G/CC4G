package cn.frish2021.gradle.Game;

import cc4g.extensions.CC4GExtension;
import cn.frish2021.gradle.FileUtil;
import cn.frish2021.gradle.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.gradle.api.Project;

import java.io.File;
import java.util.Locale;

public class GameUtil {

    public static String getOsName()
    {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT);
    }

    public static File getRunsDir(Project project) {
        return new File(project.getRootDir(),"runs");
    }

    public static File getVersionDir(Project project) {
        return new File(getRunsDir(project),"version");
    }

    public static File getTempDir(Project project) {
        return new File(getRunsDir(project),"version/Temp");
    }

    public static File getSrcDir(Project project) {
        return new File(project.getRootDir(),"src");
    }

    public static File getGameSources(Project project, CC4GExtension extension) {
        return new File(getRunsDir(project),"version/Temp/" + "sources-" + extension.gameVersion + ".zip");
    }

    public static File getGameResources(Project project, CC4GExtension extension) {
        return new File(getRunsDir(project),"version/Temp/" + "resources-" + extension.gameVersion + ".zip");
    }

    public static File getAssetsDir(Project project) {
        return new File(getRunsDir(project), "assets");
    }

    public static File getGameVersionDir(Project project, CC4GExtension extension) {
        return new File(getVersionDir(project), extension.gameVersion);
    }

    public static File getGameNativeDir(Project project, CC4GExtension extension) {
        return new File(getGameVersionDir(project, extension), "natives");
    }

    public static File getLocalClient(CC4GExtension extension, Project project) {
        return new File(getGameVersionDir(project, extension) + "/" + extension.gameVersion + ".jar");
    }

    public static byte[] getClientJar(CC4GExtension extension) {
        return FileUtil.readFile(new Gson().fromJson(JsonUtil.getJson(extension.gameVersion), JsonObject.class).get("downloads").getAsJsonObject().get("client").getAsJsonObject().get("url").getAsString());
    }

    public static void WorkSpace(Project project, CC4GExtension extension) {
        if (!getRunsDir(project).exists()) {
            getRunsDir(project).mkdirs();
        }

        if (!getAssetsDir(project).exists()) {
            getAssetsDir(project).mkdirs();
        }

        if (!getGameNativeDir(project, extension).exists()) {
            getGameNativeDir(project, extension).mkdirs();
        }

        if (!getGameVersionDir(project, extension).exists()) {
            getGameVersionDir(project, extension).mkdirs();
        }

        if (!getVersionDir(project).exists()) {
            getVersionDir(project).mkdirs();
        }
    }
}
