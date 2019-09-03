import java.net.InetSocketAddress
import play.sbt.PlayRunHook
import sbt._
import scala.sys.process.Process

object FrontBuildHook {
  def apply(base: File): PlayRunHook = {
    object FrontBuildProcess extends PlayRunHook {
      var process: Option[Process] = None

      var npmInstall = FrontCommands.install
      var npmBuild = FrontCommands.build
      var rmdir = "rm -r vue"
      var runPostgres = "docker run --rm --name playdb -p 5432:5432  -e POSTGRES_DB=playdb -e POSTGRES_USER=dev -e POSTGRES_PASSWORD=secret -d postgres:9.6"
      var stopPostgres = "docker rm -f playdb"
      
      if (System.getProperty("os.name").toLowerCase().contains("win")){
        npmInstall = "cmd /c " + npmInstall
        npmBuild = "cmd /c " + npmBuild
        rmdir = "cmd /c rmdir /s /q vue"
      }

      override def beforeStarted(): Unit = {
        if(!(base/ "front" / "node_modules").exists()) Process(npmInstall, base / "front").!
        
        //PostgresDBを起動
        Process(runPostgres).!
      }

      override def afterStarted(): Unit = {
        process = Option(Process(npmBuild, base / "front").run)
      }

      override def afterStopped(): Unit = {
        process.foreach(_.destroy())
        Process(rmdir, base / "public").!
        //Postgresのコンテナを削除
        Process(stopPostgres).!
        ()
      }
    }
    FrontBuildProcess
  }
}