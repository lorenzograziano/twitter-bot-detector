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

        if(j % 100 == 0) {
          System.out.println(j)
          System.out.println(res)
        }
        LogisticRegression.theta = res

        val step = LogisticRegression.calculateGradient(X, Y).map(x => x * alpha)

        res = res.zip(step).map(couple => couple._1 - couple._2)
      }
    )

    LogisticRegression.theta = res

  }

}
