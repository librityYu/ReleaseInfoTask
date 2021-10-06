import org.gradle.api.Plugin
import org.gradle.api.Project

// 打包的数据 插件
class ReleaseInfoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 为工程添加额外参数
        ReleaseInfoExtension  releaseInfoExtension = project.extensions.create('releaseInfoYu', ReleaseInfoExtension)
        ReleaseInfoTask  releaseInfoTask =  project.tasks.create('releaseInfoTask', ReleaseInfoTask)

        project.afterEvaluate {
            project.tasks.findByName(releaseInfoExtension.afterTask).finalizedBy(releaseInfoTask)
        }

    }
}