package cc4g.task;

import cc4g.extensions.CC4GExtension;
import org.gradle.api.DefaultTask;

public class Task extends DefaultTask {
    public CC4GExtension extension;

    public Task() {
        extension = getProject().getExtensions().getByType(CC4GExtension.class);
    }
}
