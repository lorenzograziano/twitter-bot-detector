package spotbot.logic

class LogisticRegressionOptimizer(X: Vector[Vector[Double]],
                                  Y: Vector[Double],
                                  theta: Vector[Double],
                                  lambda: Double,
                                  numIter: Int,
                                  alpha: Double) {

  def optimize() = {

    var res = theta

    LogisticRegression.lambda = lambda

    (1 to numIter).foreach(
      j => {

        LogisticRegression.theta = res

        val step = LogisticRegression.calculateGradient(X, Y).map(x => x * alpha)

        res = res.zip(step).map(couple => couple._1 - couple._2)
      }
    )

    LogisticRegression.theta = res

  }

}
