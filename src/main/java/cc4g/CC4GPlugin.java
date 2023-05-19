package cc4g;

import cc4g.extensions.CC4GExtension;
import cc4g.task.tasks.*;
import cc4g.task.tasks.debug.CreatRunsDir;
import cc4g.task.tasks.debug.CreatSrcDir;
import cc4g.task.tasks.workspace.GenIdeaRuns;
import cn.frish2021.gradle.Game.GameUtil;
import cn.frish2021.gradle.Game.LibrariesUtil;
import cn.frish2021.gradle.url.GameUrl;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class CC4GPlugin implements Plugin<Project>
{
    @Override
    public void apply(Project project)
    {
        project.getProject().getLogger().lifecycle("---------------CC4G--------------");
        project.getProject().getLogger().lifecycle("1, 作者: Frish2021,ArismaCanGG");
        project.getProject().getLogger().lifecycle("2, 这个是Minecraft 编写客户端用的框架");
        project.getProject().getLogger().lifecycle("3, 主页:https://cc4g.github.io");
        project.getProject().getLogger().lifecycle("4, github:https://github.com/CC4G");
        project.getProject().getLogger().lifecycle("5, CC4G Version: 0.1.0");
        project.getProject().getLogger().lifecycle("---------------------------------");

        project.getExtensions().create("cc4g", CC4GExtension.class);

        project.afterEvaluate(p ->
        {
            //Other Repositories
            p.getProject().getRepositories().jcenter();
            p.getProject().getRepositories().mavenLocal();
            p.getProject().getRepositories().mavenCentral();

            //Frish2021 Repositories
            p.getProject().getRepositories().maven(mavenArtifactRepository ->
            {
                mavenArtifactRepository.setName("Minecraft Repositories");
                mavenArtifactRepository.setUrl(GameUrl.GAME_LIBRARIES);
            });

            p.getProject().getRepositories().maven(mavenArtifactRepository ->
            {
                mavenArtifactRepository.setName("CC4G Repositories");
                mavenArtifactRepository.setUrl(GameUrl.CC4G_Repositories);
            });

            p.getPlugins().apply("java");
            p.getPlugins().apply("idea");

            CC4GExtension extension = p.getExtensions().getByType(CC4GExtension.class);
            if (extension.gameVersion == null) {
                project.getProject().getLogger().error("cc4g插件拓展的gameVersion变量没有参数");
            } else {
                LibrariesUtil.getLibraries(extension).forEach(library -> p.getDependencies().add("implementation", library));
            }

            if (extension.cc4g_client_api) {
                project.getDependencies().add("implementation","cc4g:cc4g-api:1.0.2");
            }

            GameUtil.WorkSpace(p, extension);

            project.getTasks().create("downloadGameJar", DownloadGameTask.class,downloadGameTask -> downloadGameTask.setGroup("cc4g"));
            project.getTasks().create("downloadGameAssets", DownloadAssetsTask.class, downloadAssetsTask -> downloadAssetsTask.setGroup("cc4g"));
            project.getTasks().create("downloadGameSources", DownloadGameSources.class, downloadGameSources -> downloadGameSources.setGroup("cc4g"));
            project.getTasks().create("downloadGameResources", DownloadGameResources.class, downloadGameResources -> downloadGameResources.setGroup("cc4g"));
            project.getTasks().create("cleanDownloadTemp", CleanDownloadTemp.class, cleanDownloadTemp -> cleanDownloadTemp.setGroup("cc4g"));
            project.getTasks().create("genIdeaRuns", GenIdeaRuns.class, genIdeaRuns -> genIdeaRuns.setGroup("cc4g"));
            project.getTasks().create("creatTempDir", CreatRunsDir.class, creatRunsDir -> creatRunsDir.setGroup("cc4g-debug"));
            project.getTasks().create("creatSourcesDir", CreatSrcDir.class, creatSrcDir -> creatSrcDir.setGroup("cc4g"));
            project.getTasks().create("cleanWorkSpace", CleanSrcTask.class, cleanSrcTask -> cleanSrcTask.setGroup("cc4g"));
            project.getTasks().create("ideaRuns", NullTask.class, nullTask -> nullTask.setGroup("cc4g"));

            p.getTasks().getByName("ideaRuns").finalizedBy(
                    p.getTasks().getByName("cleanDownloadTemp"),
                    p.getTasks().getByName("genIdeaRuns"),
                    p.getTasks().getByName("downloadGameResources"),
                    p.getTasks().getByName("downloadGameSources"),
                    p.getTasks().getByName("downloadGameAssets"),
                    p.getTasks().getByName("downloadGameJar"),
                    p.getTasks().getByName("creatSourcesDir"));
        });
    }
}
