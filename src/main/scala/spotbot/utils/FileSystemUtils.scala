package spotbot.utils

import java.io.{File, PrintWriter}
import argonaut._
import Argonaut._
import scala.io.Source
import scala.util.Random


object FileSystemUtils {
  val path = "models/"
  val thetaLRFileName = "theta_logistic_regression.json"

  def writeThetaLRJson(thetaJson: ThetaLRJson, path: String = path, fileName: String = thetaLRFileName) = {
    if(!new File(path).exists())
      new File(path).mkdirs()
    val writer = new PrintWriter(new File(path+fileName))
    writer.write(thetaJson.asJson.toString)
    writer.close()
  }

  def readThetaLRFromJson(path: String = path+thetaLRFileName):Array[Double] = {
    val rnd = new Random()
    val rndParams = (1 to 8).map(_ => rnd.nextGaussian()).toArray

    if(new File(path).exists) {
      val stringTheta = Source.fromFile(path).mkString
      val parsedTheta = Parse.decodeOption[ThetaLRJson](stringTheta)

      parsedTheta.getOrElse(
        ThetaLRJson(0.0, parameters = rndParams.toList)
      ).parameters.toArray
    } else
      rndParams
  }

}
