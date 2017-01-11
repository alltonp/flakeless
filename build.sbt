import scala.util.Try


name := "flakeless"

organization := "im.mange"

version := Try(sys.env("TRAVIS_BUILD_NUMBER")).map("0.0." + _).getOrElse("1.0-SNAPSHOT")

scalaVersion:= "2.12.1"

//crossScalaVersions := Seq("2.10.4"/*, "2.11.0"*/)

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "[2.53.1,2.99.9]" % "provided"//,
//  "com.codeborne" % "phantomjsdriver" % "[1.3.0,1.99.9]" % "provided"//,
//  "org.seleniumhq.selenium" % "selenium-java" % "[2.53.1,2.99.9]" % "provided"//,
//  "org.seleniumhq.selenium" % "selenium-java" % "2.53.1" % "test"
//	"junit" % "junit" % "4.11" % "test->default",
//	"org.scalatest" %% "scalatest" % "2.2.0" % "test"
)

//libraryDependencies := {
//  CrossVersion.partialVersion(scalaVersion.value) match {
//    case Some((2, scalaMajor)) if scalaMajor >= 11 => libraryDependencies.value :+ "org.scala-lang.modules" %% "scala-xml" % "1.0.1"
//    case _ => libraryDependencies.value
//  }
//}

//net.virtualvoid.sbt.graph.Plugin.graphSettings

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
