import org.gradle.api.Plugin
import org.gradle.api.Project

public class HelloPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.register("hello-plugin") {
            doLast {
                println("Hello from plugin 'com.frewen.aura.gradle.plugin'")
            }
        }
    }
}