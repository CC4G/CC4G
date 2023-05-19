package cn.frish2021.gradle.zip;

import org.gradle.api.Project;

import java.io.File;

public class ZipFileUrl {
    public static File getSources(Project project) {
        return new File(project.getProjectDir(),"src/main/java");
    }

    public static File getResources(Project project) {
        return new File(project.getProjectDir(),"src/main/resources");
    }
}
