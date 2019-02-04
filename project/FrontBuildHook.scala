import java.net.InetSocketAddress
import play.sbt.PlayRunHook
import sbt._
import scala.sys.process.Process

object FrontBuildHook {
  def apply(base: File): PlayRunHook = {
    object FrontBuildProcess extends PlayRunHook {
      var process: Option[Process] = None

      override def beforeStarted(): Unit = {}

      override def afterStarted(addr: InetSocketAddress): Unit = {
        process = Option(Process(Seq("cmd", "/c", "npm run build"), base / "front").run)
      }

      override def afterStopped(): Unit = {
        process.foreach(_.destroy())
        Process("cmd /c rmdir /s /q vue", base / "public").run
        ()
      }
    }
    FrontBuildProcess
  }
}