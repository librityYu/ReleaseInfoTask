class ReleaseInfoExtension {
    String versionCode
    String versionName
    String versionInfo
    String fileName
    String afterTask = "assembleDebug"

    ReleaseInfoExtension() {

    }

    @Override
    String toString() {
        """
        versionCode = ${versionCode}
        versionName = ${versionName}
        versionInfo = ${versionInfo}
        fileName = ${fileName}
        afterTask = ${afterTask}
        """
    }
}