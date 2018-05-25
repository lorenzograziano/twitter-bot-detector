package spotbot.logic

import spotbot.domain.TwitterAccount
import spotbot.service.BotRepository

import scala.collection.immutable
import scala.util.Random


object ModelTraining {

  var xAvg: Array[Double] = _

  var xStdDev: Array[Double] = _

  def train(botRepository: BotRepository): (Double, Double, Double) = {
    /**
      * Retrieve data from DB
      **/
    val allTwitterAccounts = botRepository.findAll()

    var allTwitterAccountList: List[TwitterAccount] = List()
    val accountIterator = allTwitterAccounts.iterator()

    while (accountIterator.hasNext){
      allTwitterAccountList = allTwitterAccountList :+ accountIterator.next()
    }

    val botList: immutable.Seq[TwitterAccount] = allTwitterAccountList
      .filter(account => account.isBot)
    val splitBot = (botList.length * 0.75).round.toInt

    val userList: immutable.Seq[TwitterAccount] = allTwitterAccountList
      .filter(account => !account.isBot)
    val splitUser = (userList.length * 0.75).round.toInt

    val data = getXY(botList ++ userList)

    calculateMeanAndStDev(data.map(_._1))

    val normDataUser = normalize(data.filter(_._2 == 0.0).map(_._1))
    val normDataBot = normalize(data.filter(_._2 == 1.0).map(_._1))

    val (trainingSetUser, validationSetUser) = normDataUser.splitAt(splitUser)
    val (trainingSetBot, validationSetBot) = normDataBot.splitAt(splitBot)
    val trainingSetX = trainingSetBot ++ trainingSetUser
    val trainingSetY = trainingSetBot.map( x => 0.0) ++ trainingSetUser.map( x => 1.0)
    val validationSetX = validationSetBot ++ validationSetUser
    val validationSetY = validationSetBot.map( x => 0.0) ++ validationSetUser.map( x => 1.0)

    System.out.println(s"userList ${userList.size}")
    System.out.println(s"traningSetUser ${trainingSetUser.size}")
    System.out.println(s"validationSetUser ${validationSetUser.size}")
    System.out.println(s"botList ${botList.size}")
    System.out.println(s"trainingSetBot ${trainingSetBot.size}")
    System.out.println(s"validationSetBot ${validationSetBot.size}")

    /**
      * Logistic Regression Model
      **/
    val optimizer =
      new LogisticRegressionOptimizer(trainingSetX, trainingSetY, LogisticRegression.theta, lambda = 0.1, numIter = 10000, alpha = 0.1)

    optimizer.optimize()

    /**
      * Validation
      * */

    val yPred: Array[Double] = validationSetX.map(
      x => LogisticRegression.hTheta(x)
    )

    val result = yPred.zip(validationSetY)

    val truePositive = result
      .count{
        case(yP: Double, yT: Double) => yP == yT && yP == 1
      }

    val falsePositive = result
      .count {
        case(yP: Double, yT: Double) => yP == 1 && yP != yT
      }

    val falseNegative = result
      .count {
        case(yP: Double, yT: Double) => yP == 0 && yP != yT
      }

    val precision = truePositive.toDouble / (truePositive + falsePositive)
    val recall = truePositive.toDouble / (truePositive + falseNegative)

    val accuracy = result.count {
      case(yP: Double, yT: Double) => yP == yT
    }.toDouble/result.length.toDouble

    System.out.println("Precision " + precision)
    System.out.println("Recall "+ recall)
    System.out.println("Accuracy "+ accuracy)

    (precision, recall, accuracy)

  }

  def getXY(list: Seq[TwitterAccount]): (Array[(Array[Double], Double)]) =
    list.map(
      twitterAccount =>
        (Array(
          1.0,
          twitterAccount.getNumTweets.toDouble,
          twitterAccount.getAverageNumActivityPerDay,
          twitterAccount.getPercentageRetweets,
          twitterAccount.getFollowingAccounts.toDouble,
          twitterAccount.getFollowersCount.toDouble,
          twitterAccount.getPublicList.toDouble,
          twitterAccount.getPercentageOfCompletion),
          if (twitterAccount.getIsBot) 1.0 else 0.0
        )
    ).toArray

  def calculateMeanAndStDev(x: Array[Array[Double]]) = {

    val xT: Array[Array[Double]] = x.transpose

    xAvg = xT.map {

      feature =>

        feature.foldLeft((0.0, 1)) { case ((avg, idx), next) => (avg + (next - avg) / idx, idx + 1) }._1

    }

    xStdDev = xT.zip(xAvg).map {

      case (feature, mean) =>

        val sqrMeanError = feature.map(x => (x - mean) * (x - mean))

        sqrMeanError.foldLeft((0.0, 1)) { case ((avg, idx), next) => (avg + (next - avg) / idx, idx + 1) }._1

    }.map {

      z => math.sqrt(z)

    }

  }

  def normalize(x: Array[Array[Double]]): Array[Array[Double]] = {

    val normX: Array[Array[Double]] = x.map(
      y =>
        y.zip(0.0 +: xAvg.tail).zip(1.0 +: xStdDev.tail).map(
          z =>
            (z._1._1 - z._1._2) / z._2
        )
    )

    normX

  }
}
