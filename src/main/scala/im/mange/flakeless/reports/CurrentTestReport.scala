package im.mange.flakeless.reports

import java.nio.charset.StandardCharsets
import java.util.Base64

import im.mange.flakeless.Flakeless
import im.mange.flakeless.innards.ReportAssets
import org.openqa.selenium.{OutputType, TakesScreenshot}

object CurrentTestReport {
  import java.nio.file.{Files, Path, Paths}

  def apply(flakeless: Flakeless, captureImage: Boolean = true) {
    try {
      val flightNumber = flakeless.getCurrentFlightNumber
      val filepath = s"${flakeless.config.reportDirectory}/${"%04d".format(flightNumber)}/"
      //TODO: should use little createDir
      Files.createDirectories(Paths.get(filepath))

      val when = System.currentTimeMillis()

      val imagePath = path(filepath, s"$when.png")
      if (captureImage) write(imagePath, screenshot(flakeless))

      val jsonFlightData: String = flakeless.jsonFlightData(flightNumber)

//      println(jsonFlightData + "\n")

      val b64 = Base64.getEncoder.encodeToString(jsonFlightData.getBytes(StandardCharsets.UTF_8))

      val htmlPath = path(filepath, s"report.html")
      write(htmlPath, htmlContent(when, flakeless, b64).getBytes)

      ReportAssets.writeFlakelessJs(flakeless.config.reportDirectory)

      val fileSystemReportPath = htmlPath.toAbsolutePath.toString

      flakeless.config.reportHost match {
        case None => System.err.println("*** Test Report: " + fileSystemReportPath)
        case Some(h) => System.err.println(s"*** Test Report: ${h}/${htmlPath.toString.replaceAll("\\\\", "/")} (or ${fileSystemReportPath})")

      }
    } catch {
      case t: Exception => System.err.println(s"*** Failed to write Test Report something bad happened ***\nProblem was: ${t.getMessage}")
    }

    //TODO: might be able to kill the lozenge now ...
    def htmlContent(when: Long, flakeless: Flakeless, data: String) =
s"""
  |<html>
  |<head>
  |<script type="text/javascript" src="../flakeless.js"></script>
  |<style>
  |
  |.container {
  |  display: -webkit-flex;
  |  display: flex;
  |}
  |
  |.flex1 {
  |  -webkit-flex: 1;
  |          flex: 1;
  |}
  |
  |.flex2 {
  |  -webkit-flex: 2;
  |          flex: 2;
  |}
  |
  |.message {
  |  color: grey;
  |}
  |.pass {
  |  color: #00cc00;
  |}
  |.fail {
  |  color: cc0000;
  |}
  |.dunno {
  |  color: cccc00;
  |}
  |
  |</style>
  |</head>
  |<body>
  |  <div class="container">
  |    <div class="flex2" style="font-family: Courier New;" id="content"></div>
  |    <div class="flex2" style="background-color: grey;" class="container"><img src="$when.png" style="zoom: 75%;"></div>
  |  </div>
  |  <script>
  |    var data = '${data.replaceAll("\n", "").replaceAll("'", "")}';
  |    var app = Elm.FlightReport.embed(document.getElementById('content'));
  |    app.ports.flightData.send(data);
  |  </script>
  |</body>
  |</html>
""".stripMargin

  }

  private def write(path: Path, content: Array[Byte]) = Files.write(path, content)

  private def screenshot(flakeless: Flakeless) =
    flakeless.rawWebDriver.asInstanceOf[TakesScreenshot].getScreenshotAs(OutputType.BYTES)

  private def path(filepath: String, filename: String) = Paths.get(filepath + filename)
}
