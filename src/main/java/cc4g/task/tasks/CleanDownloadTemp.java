package cc4g.task.tasks;

import cc4g.task.Task;
import cn.frish2021.gradle.FileUtil;
import cn.frish2021.gradle.Game.GameUtil;
import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CleanDownloadTemp extends Task {
    @TaskAction
    public void cleanDownloadTemp() {
        File temp = GameUtil.getTempDir(getProject());
        try {
            if (temp.exists()) {
                File[] tempFiles = temp.listFiles();
                for (File file : tempFiles) {
                    Files.delete(file.toPath());
                }
                Files.delete(temp.toPath());
            } else {
                getProject().getLogger().error("抱歉，无法清理下载缓存，原因：文件已经不存在了");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
