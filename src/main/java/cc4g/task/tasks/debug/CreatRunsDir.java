package cc4g.task.tasks.debug;

import cc4g.task.Task;
import cn.frish2021.gradle.Game.GameUtil;
import org.gradle.api.tasks.TaskAction;

public class CreatRunsDir extends Task {
    @TaskAction
    public void create() {
        if (!GameUtil.getTempDir(getProject()).exists()) {
            GameUtil.getTempDir(getProject()).mkdirs();
        }

        if (!GameUtil.getRunsDir(getProject()).exists()) {
            GameUtil.getRunsDir(getProject()).mkdirs();
        }
    }
}
