package cc4g.task.tasks.workspace;

import cc4g.task.Task;
import cn.frish2021.gradle.Game.GameUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GenIdeaRuns extends Task {
    @TaskAction
    public void genIdeaRuns() {
        StringBuilder vmArgs = new StringBuilder("-Djava.library.path=" + GameUtil.getGameNativeDir(getProject(), extension).getAbsolutePath());
        StringBuilder programArgs = new StringBuilder();

        programArgs.append("--assetsDir").append(" ").append(GameUtil.getAssetsDir(getProject()).getAbsolutePath()).append(" ");
        programArgs.append("--assetIndex").append(" ").append(extension.gameVersion).append(" ");
        programArgs.append("--version").append(" ").append("Minecraft 1.8.9").append(" ");
        programArgs.append("--accessToken").append(" ").append("0").append(" ");
        programArgs.append("--gameDir").append(" ").append(getProject().getRootProject().file("runs").getAbsolutePath()).append(" ");

        try {
            String idea = IOUtils.toString(Objects.requireNonNull(GenIdeaRuns.class.getResourceAsStream("/CC4GLaunchClient.xml")), StandardCharsets.UTF_8);

            idea = idea.replace("%NAME%", "runClient");
            idea = idea.replace("%MAIN_CLASS%", "net.minecraft.client.main.Main");
            idea = idea.replace("%IDEA_MODULE%", getModule());
            idea = idea.replace("%PROGRAM_ARGS%", programArgs.toString().replaceAll("\"", "&quot;"));
            idea = idea.replace("%VM_ARGS%", vmArgs.toString().replaceAll("\"", "&quot;"));
            String projectPath = getProject() == getProject().getRootProject() ? "" : getProject().getPath().replace(":", "_");
            File ideaConfigurationDir = getProject().getRootProject().file(".idea");
            File runConfigurations = new File(ideaConfigurationDir, "runConfigurations");
            File clientRunConfiguration = new File(runConfigurations, "CC4GLaunchClient" + projectPath + ".xml");
            if (!runConfigurations.exists()) {
                runConfigurations.mkdir();
            }
            FileUtils.write(clientRunConfiguration, idea, StandardCharsets.UTF_8);
            File run = getProject().getRootProject().file("runs");
            if (!run.exists()) {
                run.mkdir();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getModule() {
        Project project = getProject();
        StringBuilder stringBuilder = new StringBuilder(project.getName() + ".main");
        while ((project = project.getParent()) != null) {
            stringBuilder.insert(0, project.getName() + ".");
        }
        return stringBuilder.toString();
    }
}