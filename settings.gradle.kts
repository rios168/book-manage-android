pluginManagement {
    repositories {
        gradlePluginPortal()
//        maven { url = uri("https://mirrors.163.com/maven/repository/maven-public/") }
//        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
//        maven { url = uri("https://maven.aliyun.com/repository/public") }
//        maven { url = uri("https://maven.aliyun.com/repository/central") }
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
        mavenLocal()
        mavenCentral()
        google()
    }
}
dependencyResolutionManagement {
    repositories {
//        maven { url = uri("https://mirrors.163.com/maven/repository/maven-public/") }
//        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
//        maven { url = uri("https://maven.aliyun.com/repository/public") }
//        maven { url = uri("https://maven.aliyun.com/repository/central") }
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
        mavenLocal()
        mavenCentral()
        google()
    }
}

rootProject.name = "BookManagement"
include(":app")
