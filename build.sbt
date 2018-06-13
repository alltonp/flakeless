import scala.util.Try
import xerial.sbt.Sonatype._

name := "flakeless"

organization := "im.mange"

version := Try(sys.env("TRAVIS_BUILD_NUMBER")).map("0.0." + _).getOrElse("1.0-SNAPSHOT")

scalaVersion:= "2.12.4"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "example" / "scala"

libraryDependencies ++= Seq(
  //TIP: always provided, so clients can choose
  "org.seleniumhq.selenium" % "selenium-java" % "[2.53.1,3.99.9]" % "provided",

  //TIP: not provided, to avoid rogue milestone releases
  "org.json4s"     %% "json4s-native" % "3.2.11"
    exclude("joda-time", "joda-time")
  ,

  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

sonatypeSettings

publishTo <<= version { project_version ⇒
  val nexus = "https://oss.sonatype.org/"
  if (project_version.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/alltonp/flakeless"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv("SONATYPE_USER"), System.getenv("SONATYPE_PASSWORD"))

pomExtra :=
    <scm>
      <url>git@github.com:alltonp/flakeless.git</url>
      <connection>scm:git:git@github.com:alltonp/flakeless.git</connection>
    </scm>
    <developers>
      <developer>
        <id>alltonp</id>
      </developer>
    </developers>
