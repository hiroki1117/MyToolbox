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

      if (System.getProperty("os.name").toLowerCase().contains("win")){
        npmInstall = "cmd /c " + npmInstall
        npmBuild = "cmd /c " + npmBuild
        rmdir = "cmd /c rmdir /s /q vue"
      }

      override def beforeStarted(): Unit = {
        if(!(base/ "front" / "node_modules").exists()) Process(npmInstall, base / "front").!
      }

      override def afterStarted(addr: InetSocketAddress): Unit = {
        process = Option(Process(npmBuild, base / "front").run)
      }

      override def afterStopped(): Unit = {
        process.foreach(_.destroy())
        Process(rmdir, base / "public").run
        ()
      }
    }
    FrontBuildProcess
  }
}