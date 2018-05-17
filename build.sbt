import Dependencies._

//testCompile("org.springframework.boot:spring-boot-starter-test")

lazy val springVersion = "1.5.3.RELEASE"
lazy val thymeleafVersion = "2.1.5.RELEASE"

resolvers += Resolver.sonatypeRepo("releases")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.bk",
      scalaVersion := "2.12.2",
      version      := "0.3.0"
    )),
    name := "twitter-bot-detector",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % springVersion,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-data-jpa" % springVersion,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-actuator" % springVersion,
    libraryDependencies += "org.thymeleaf" % "thymeleaf-spring4" % thymeleafVersion,
    libraryDependencies += "nz.net.ultraq.thymeleaf" % "thymeleaf-layout-dialect" % "1.4.0",
    libraryDependencies += "com.h2database" % "h2" % "1.4.195",
    libraryDependencies += "org.webjars" % "bootstrap" % "3.1.1",
    libraryDependencies += "com.danielasfregola" %% "twitter4s" % "5.5",
    libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.5"
  )


// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
mainClass in (Compile, run) := Some("spotbot.Application")