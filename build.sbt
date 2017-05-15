import sbt.url
import Dependencies._

lazy val versionSettings = Seq(
  isSnapshot := {
    dynverGitDescribeOutput.value forall { gitVersion =>
      gitVersion.hasNoTags() || gitVersion.isDirty() || gitVersion.commitSuffix.distance > 0
    }
  },

  //  The 'version' setting is not set on purpose: its value is generated automatically by the sbt-dynver plugin
  //  based on the git tag/sha. Here we're just tacking on the maven-compatible snapshot suffix if needed
  version := {
    if (isSnapshot.value)
      version.value + "-SNAPSHOT"
    else
      version.value
  }
)

lazy val publicationSettings = Seq(
  publishTo := {
    if (isSnapshot.value)
      Some(s"Artifactory Realm" at "https://oss.jfrog.org/artifactory/oss-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
    else
      publishTo.value //Here we are assuming that the sbt-bintray plugin does its magic and the publish settings are set to
    //point to Bintray
  },
  credentials := {
    if (isSnapshot.value)
      List(
        Credentials(
          realm = "Artifactory Realm",
          host = "oss.jfrog.org",
          userName = System.getenv("BINTRAY_USER"),
          passwd = System.getenv("BINTRAY_PASSWORD")
        )
      )
    else
      credentials.value
  },
  publishArtifact in Test := false,
  bintrayReleaseOnPublish := !isSnapshot.value,
  bintrayVcsUrl := scmInfo.value.map(_.browseUrl.toString)
)

lazy val projectMetadataSettings = Seq(
  organization := "com.github.dafutils",
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.txt")),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url("https://github.com/dafutils/domain-dns-ops"),
      connection = "scm:git:git@github.com:dafutils/domain-dns-ops.git"
    )
  ),
  developers := List(
    Developer(
      id = "edafinov",
      name = "Emil Dafinov",
      email = "emiliorodo@gmail.com",
      url = url("https://github.com/EmilDafinov")
    )
  )
)

lazy val javaProjectSettings = Seq(
  // Do not append Scala versions to the generated artifacts
  crossPaths := false,
  // This forbids including Scala related libraries into the dependency
  autoScalaLibrary := false
)

lazy val unitTestSettings = Seq (
  libraryDependencies ++= unitTestDependencies,
  //Enable JUnit in the build 
  testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
)

lazy val commonProjectSettings = 
               javaProjectSettings ++ 
               projectMetadataSettings ++
               versionSettings ++
               publicationSettings ++
               jacoco.settings ++ 
               unitTestSettings

lazy val model = project
  .settings(
    commonProjectSettings,
    libraryDependencies += lombok
  )

lazy val api = project 
  .dependsOn(model)
  .settings(commonProjectSettings)  

lazy val godaddy = project
  .dependsOn(api)
  .settings(
    commonProjectSettings,
    libraryDependencies ++= Seq(unirest, gson, wiremock)
  )

lazy val hostway = project
  .dependsOn(api)
  .settings(
    commonProjectSettings,
    libraryDependencies ++= Seq(unirest, gson, wiremock)
  )  

lazy val domainDnsOps = (project in file("."))
  .aggregate(model, api, godaddy)
