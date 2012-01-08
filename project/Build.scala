import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "anorm-sample"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      // Add your project dependencies here,
      // "play" % "play-test_2.9.1" % "2.0-RC1-SNAPSHOT" % "test"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
