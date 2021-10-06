import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.text.SimpleDateFormat

class ReleaseInfoTask extends DefaultTask{
    ReleaseInfoTask(){
        group = "libertyYu"
        description  = "update the release info"
    }

    @TaskAction
    void doAction(){
        updateInfo()
    }

    void updateInfo(){
        String versionCodeMsg = project.extensions.releaseInfoYu.versionCode
        String versionNameMsg = project.extensions.releaseInfoYu.versionName
        String versionInfoMsg = project.extensions.releaseInfoYu.versionInfo
        String fileName = project.extensions.releaseInfoYu.fileName
        StringWriter stringWriter = new StringWriter()
        MarkupBuilder xmlBuilder = new MarkupBuilder(stringWriter)
        def file = project.file(fileName)
        if (file == null && !destFile.exists()) {
            file.createNewFile()
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        if (file.text != null && file.text.size() <= 0) {
            //没有内容
            xmlBuilder.releases {
                release {
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                    versionTime(dateString)
                }
            }
            //直接写入
            file.withWriter { writer -> writer.append(stringWriter.toString())
            }
        } else {
            //已有其它版本内容
            xmlBuilder.release {
                versionCode(versionCodeMsg)
                versionName(versionNameMsg)
                versionInfo(versionInfoMsg)
                versionTime(dateString)
            }
            //插入到最后一行前面
            def lines = file.readLines()
            def lengths = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { line, index ->
                    if (index != lengths) {
                        writer.append(line + '\r\n')
                    } else if (index == lengths) {
                        writer.append('\r\r\n' + stringWriter.toString() + '\r\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }
    }

}