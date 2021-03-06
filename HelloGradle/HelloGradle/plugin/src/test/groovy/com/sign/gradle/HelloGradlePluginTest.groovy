/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package com.sign.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import spock.lang.Specification

/**
 * A simple unit test for the 'com.sign.gradle.greeting' plugin.
 */
class HelloGradlePluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("com.sign.gradle.greeting")

        then:
        project.tasks.findByName("greeting") != null
    }
}
