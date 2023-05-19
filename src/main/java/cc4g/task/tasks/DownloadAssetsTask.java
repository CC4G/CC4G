package cc4g.task.tasks;

import cc4g.task.Task;
import cn.frish2021.gradle.FileUtil;
import cn.frish2021.gradle.Game.AssetsUtil;
import cn.frish2021.gradle.Game.GameUtil;
import cn.frish2021.gradle.Game.LibrariesUtil;
import cn.frish2021.gradle.url.GameUrl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DownloadAssetsTask extends Task {
    @TaskAction
    public void downloadAssets() {
        String clientAssets = AssetsUtil.getClientAssets(extension);
        getProject().getLogger().lifecycle("Download index ...");
        //index file
        if (!AssetsUtil.getClientAssetsIndexFile(extension).exists()) {
            try {
                FileUtils.write(AssetsUtil.getClientAssetsIndexFile(extension), clientAssets, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getProject().getLogger().lifecycle("Download assets ...");
        Map<String, AssetObject> objectMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> objects : new Gson().fromJson(clientAssets, JsonObject.class).get("objects").getAsJsonObject().entrySet()) {
            objectMap.put(objects.getKey(), new Gson().fromJson(objects.getValue(), AssetObject.class));
        }

        objectMap.forEach((name, assetObject) -> {
            File clientAssetsObjectFile = AssetsUtil.getClientAssetsObjectFile(extension, assetObject.getHash());
            try {
                FileUtils.writeByteArrayToFile(clientAssetsObjectFile, FileUtil.readFile(GameUrl.GAME_RESOURCE + "/" + assetObject.getHash().substring(0, 2) + "/" + assetObject.getHash()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (!GameUtil.getTempDir(getProject()).exists()) {
            GameUtil.getTempDir(getProject()).mkdirs();
        }

        getProject().getLogger().lifecycle("Download natives ...");
        File clientNativeJarDir = GameUtil.getTempDir(getProject());
        File clientNativeFileDir = GameUtil.getGameNativeDir(getProject(), extension);

        LibrariesUtil.getNatives(extension).forEach(link -> {
            String name = link.substring(link.lastIndexOf("/") + 1);
            File file = new File(clientNativeJarDir, name);
            try {
                if (!file.exists()) {
                    FileUtils.writeByteArrayToFile(file, FileUtil.readFile(link));
                }

                ZipFile zipFile = new ZipFile(file);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    if (zipEntry.isDirectory() || zipEntry.getName().contains("META-INF")) {
                        continue;
                    }
                    FileUtils.writeByteArrayToFile(new File(clientNativeFileDir, zipEntry.getName()), IOUtils.toByteArray(zipFile.getInputStream(zipEntry)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static class AssetObject {
        private final String hash;
        private final long size;

        private AssetObject(String hash, long size) {
            this.hash = hash;
            this.size = size;
        }

        public String getHash() {
            return hash;
        }

        public long getSize() {
            return size;
        }
    }
}
