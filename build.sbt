import sbt.url

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
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.txt")),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url("https://github.com/EmilDafinov/scala-ad-sdk"),
      connection = "scm:git:git@github.com:EmilDafinov/scala-ad-sdk.git"
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

lazy val javaProjectSettings = Seq (
  // Do not append Scala versions to the generated artifacts
  crossPaths := false,
  // This forbids including Scala related libraries into the dependency
  autoScalaLibrary := false
)

lazy val model = project in file("./model")

lazy val domainDnsOps = (project in file("."))
  .settings(javaProjectSettings)  
  .settings(projectMetadataSettings)
  .settings(versionSettings)
  .settings(publicationSettings)
  .settings(jacoco.settings)
  .settings(

    organization := "com.github.dafutils",
    name := "domain-dns-ops",
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),

    libraryDependencies ++= Seq(
      "org.mockito" % "mockito-core" % "2.7.22" % Test,
      "org.assertj" % "assertj-core" % "3.6.2" % Test,
      "com.novocode" % "junit-interface" % "0.11" % Test
    )
  ).dependsOn(model)
