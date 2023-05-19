package cc4g.task.tasks;

import cc4g.task.Task;
import cn.frish2021.gradle.Game.GameUtil;
import cn.frish2021.gradle.sources.SourcesUtil;
import cn.frish2021.gradle.zip.ZipFileUrl;
import cn.frish2021.gradle.zip.ZipUtil;
import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

public class DownloadGameResources extends Task {
    @TaskAction
    public void downloadGameSources() {
        File localResources = GameUtil.getGameResources(getProject(), extension);
        try {
            FileUtils.writeByteArrayToFile(localResources, SourcesUtil.getClientResources(extension));
            ZipUtil.unZip(localResources, ZipFileUrl.getResources(getProject()).getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
