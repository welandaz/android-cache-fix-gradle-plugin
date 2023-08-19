package org.gradle.android

import spock.lang.Unroll

import static java.util.regex.Pattern.quote

class PluginApplicationTest extends AbstractTest {

    @Unroll
    def "does not apply workarounds with Android #androidVersion"() {
        def projectDir = temporaryFolder.newFolder()
        SimpleAndroidApp.builder(projectDir, cacheDir)
            .withAndroidVersion(androidVersion)
            .build()
            .writeProject()

        expect:
        def result = withGradleVersion(TestVersions.latestGradleVersion().version)
            .withProjectDir(projectDir)
            .withArguments("tasks", "--stacktrace")
            .buildAndFail()
        result.output =~ /Android plugin ${quote(androidVersion)} is not supported by Android cache fix plugin. Supported Android plugin versions: .*./

        where:
        androidVersion << ["4.2.2"]

    }
}
