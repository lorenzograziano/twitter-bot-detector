package spotbot.logic

import spotbot.utils.FileSystemUtils
import scala.util.Random

object LogisticRegression {

  var lambda: Double = _

  private val rnd = new Random()

  var theta: Array[Double] = FileSystemUtils.readThetaLRFromJson()

  def sigmoid(z: Double): Double = 1.0 / (1.0 + math.exp(-z))


  def hTheta(x: Array[Double]): Double = sigmoid(x.zip(theta).map(x => x._1 * x._2).sum)


  def costFunction(x: Array[Double], y: Double): Double = y * math.log(hTheta(x)) - (1 - y) * math.log(hTheta(x))


  def calculateError(X: Array[Array[Double]] , Y: Array[Double]): Double = {
    val m = X.size
    (1 / m) * X.map( x => costFunction(x, Y(X.indexOf(x)))).sum + (lambda / (2 * m)) * theta.map( x => x * x).sum
  }

  def calculateGradient(X: Array[Array[Double]] , Y: Array[Double]): Array[Double] = {
    val m = X.size
    (0 until theta.length).map {
      j => {
        if (j == 0) (1.0 / m) * X.map(x => (hTheta(x) - Y(X.indexOf(x))) * x(j)).sum
        else (1.0 / m) * X.map( x => (hTheta(x) - Y(X.indexOf(x))) * x(j) ).sum + (lambda / m) * theta(j)
      }

    }.toArray
  }

}
