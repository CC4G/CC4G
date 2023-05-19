package cc4g.task.tasks;

import cc4g.task.Task;
import cn.frish2021.gradle.Game.GameUtil;
import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

public class DownloadGameTask extends Task {
    @TaskAction
    public void downloadGame() {
        File localClient = GameUtil.getLocalClient(extension, getProject());
        try {
            FileUtils.writeByteArrayToFile(localClient, GameUtil.getClientJar(extension));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
