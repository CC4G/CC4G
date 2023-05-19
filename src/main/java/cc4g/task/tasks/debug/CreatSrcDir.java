package cc4g.task.tasks.debug;

import cc4g.task.Task;
import cn.frish2021.gradle.Game.GameUtil;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class CreatSrcDir extends Task {
    @TaskAction
    public void create() {
        File src = new File(getProject().getRootDir(),"src/main/java");
        File res = new File(getProject().getRootDir(),"src/main/resources");
        if (!src.exists()) {
            src.mkdirs();
        }
        if (!res.exists()) {
            res.mkdirs();
        }
    }
}
