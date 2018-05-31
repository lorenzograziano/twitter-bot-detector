package spotbot.utils

import argonaut._
import Argonaut._

case class ThetaLRJson(modelVersion: Double, parameters: List[Double])

object ThetaLRJson {
  implicit def ThetaCodecJson =
    casecodec2(ThetaLRJson.apply, ThetaLRJson.unapply)("model_version", "parameters")

}