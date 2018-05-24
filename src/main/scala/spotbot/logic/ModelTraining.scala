package spotbot.logic

import java.util

import spotbot.domain.TwitterAccount
import spotbot.service.BotRepository


object ModelTraining {

  def train(botRepository: BotRepository): (Double, Double) = {
    /**
      * Retrieve data from DB
      **/
    val allTwitterAccounts = botRepository.findAll()

    val allTwitterAccountList: List[TwitterAccount] = List()
    val accountIterator: util.Iterator[TwitterAccount] = allTwitterAccounts.iterator()

    accountIterator
      .forEachRemaining(x => allTwitterAccountList :+ x)

    val botList = allTwitterAccountList
      .filter(account => account.isBot)
    val splitBot = (botList.length * 0.8).round.toInt
    val (trainingSetBot, validationSetBot) = botList.splitAt(splitBot)

    val userList = allTwitterAccountList
      .filter(account => !account.isBot)
    val splitUser = (userList.length * 0.8).round.toInt
    val (trainingSetUser, validationSetUser) = botList.splitAt(splitUser)

    val trainingSetList = trainingSetBot ++ trainingSetUser
    val validationSet = validationSetBot ++ validationSetUser

    val x = getX(trainingSetList)
    val y = getY(trainingSetList)
    val theta = Vector(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)


    /**
      * Logistic Regression Model
      **/
    val optimizer =
      new LogisticRegressionOptimizer(x, y, theta, lambda = .1, numIter = 10000, alpha = .1)

    optimizer.optimize()

    /**
      * Validation
      * */
    val xTest = getX(validationSet)
    val yTest: Vector[Double] = getY(validationSet)

    val yPred: Vector[Double] = xTest.map(
      x => LogisticRegression.hTheta(x)
    )

    val truePositive = yPred.zip(yTest)
      .count{
        case(yP: Double, yT: Double) => yP == yT && yP == 1
      }

    val falsePositive = yPred.zip(yTest)
      .count {
        case(yP: Double, yT: Double) => yP == 1 && yP != yT
      }

    val falseNegative = yPred.zip(yTest)
      .count {
        case(yP: Double, yT: Double) => yP == 0 && yP != yT
      }

    val precision = truePositive.toDouble / (truePositive + falsePositive)
    val recall = truePositive.toDouble / (truePositive + falseNegative)

    (precision, recall)

  }

  def getX(list: List[TwitterAccount]): Vector[Vector[Double]] =
    list.map(
      twitterAccount =>
        Vector(
          1.0,
          twitterAccount.getNumTweets,
          twitterAccount.getAverageNumActivityPerDay,
          twitterAccount.getPercentageRetweets,
          twitterAccount.getFollowingAccounts,
          twitterAccount.getFollowersCount,
          twitterAccount.getIsVerified,
          twitterAccount.getPublicList,
          twitterAccount.getPercentageOfCompletion
        )
    ).toVector

  def getY(list:List[TwitterAccount]):Vector[Double] =
    list.map(
      twitterAccount =>
        if (twitterAccount.getIsBot) 1.0 else 0.0
    ).toVector
}