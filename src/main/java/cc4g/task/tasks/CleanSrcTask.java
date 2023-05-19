package cc4g.task.tasks;

import cc4g.task.Task;
import cn.frish2021.gradle.FileUtil;
import cn.frish2021.gradle.Game.GameUtil;
import org.gradle.api.tasks.TaskAction;

public class CleanSrcTask extends Task {
    @TaskAction
    public void cleanSrcTask() {
        if (GameUtil.getRunsDir(getProject()).exists()) {
            FileUtil.deleteDirectory(GameUtil.getRunsDir(getProject()));
        }

        if (GameUtil.getSrcDir(getProject()).exists()) {
            FileUtil.deleteDirectory(GameUtil.getSrcDir(getProject()));
        }
    }
}
