import sbt._

object Dependencies {
  
  val mockito = "org.mockito" % "mockito-core" % "2.7.22"
  val assertj = "org.assertj" % "assertj-core" % "3.6.2"
  val junitInterface = "com.novocode" % "junit-interface" % "0.11"
  val wiremock = "com.github.tomakehurst" % "wiremock" % "2.6.0"

  val unitTestDependencies =  Seq(
      mockito % Test,
      assertj % Test,
      junitInterface % Test
  )
}
