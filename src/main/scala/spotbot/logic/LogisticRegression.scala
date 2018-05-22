package spotbot.logic

object LogisticRegression {

  var lambda: Double = _

  var theta: Vector[Double] = _

  def sigmoid(z: Double): Double = 1.0 / (1.0 + math.exp(-z))


  def hTheta(x: Vector[Double]): Double = sigmoid(x.zip(theta).map(x => x._1 * x._2).sum)


  def costFunction(x: Vector[Double], y: Double): Double = y * math.log(hTheta(x)) - (1 - y) * math.log(hTheta(x))


  def calculateError(X: Vector[Vector[Double]] , Y: Vector[Double]): Double = {
    val m = X.size
    (1 / m) * X.map( x => costFunction(x, Y(X.indexOf(x)))).sum + (lambda / (2 * m)) * theta.map( x => x * x).sum
  }

  def calculateGradient(X: Vector[Vector[Double]] , Y: Vector[Double]): Vector[Double] = {
    val m = X.size
    (0 until theta.length).map {
      j =>
        (1 / m) * X.map( x => (hTheta(x) - Y(X.indexOf(x))) * x(j) ).sum + (lambda / m) * theta(j)
    }.toVector
  }

}