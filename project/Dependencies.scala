import sbt._

object Dependencies {
  
  val mockito = "org.mockito" % "mockito-core" % "2.7.22"
  val assertj = "org.assertj" % "assertj-core" % "3.6.2"
  val junitInterface = "com.novocode" % "junit-interface" % "0.11"
  val wiremock = "com.github.tomakehurst" % "wiremock" % "2.6.0"
  val lombok = "org.projectlombok" % "lombok" % "1.16.16"
  val guava = "com.google.guava" % "guava" % "21.0"
  val unirest = "com.mashape.unirest" % "unirest-java" % "1.4.9"
  val gson = "com.google.code.gson" % "gson" % "2.8.0"
  val unitTestDependencies =  Seq(
      mockito % Test,
      assertj % Test,
      junitInterface % Test
  )
}
