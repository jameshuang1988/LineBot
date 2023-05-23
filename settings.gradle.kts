pluginManagement {
	repositories {
		maven("https://plugins.gradle.org/m2/")
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}
rootProject.name = "LineBot"
