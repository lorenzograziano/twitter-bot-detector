package spotbot.logic

import spotbot.utils.{FileSystemUtils, ThetaLRJson}


class LogisticRegressionOptimizer(X: Array[Array[Double]],
                                  Y: Array[Double],
                                  theta: Array[Double],
                                  lambda: Double,
                                  numIter: Int,
                                  alpha: Double) {

  def optimize() = {

    var res = theta
    LogisticRegression.lambda = lambda

    (1 to numIter).foreach(
      j => {
        if(j % 100 == 0) {
          System.out.println(j)
          System.out.println(res.toVector)
        }
        LogisticRegression.theta = res
        val step = LogisticRegression.calculateGradient(X, Y).map(x => x * alpha)
        res = res
          .zip(step)
          .map(
            couple => couple._1 - couple._2
          )
      }
    )

    val jsonTheta = ThetaLRJson(modelVersion = 1.0, parameters = res.toList)
    FileSystemUtils.writeThetaLRJson(jsonTheta)

    LogisticRegression.theta = res

  }

}
