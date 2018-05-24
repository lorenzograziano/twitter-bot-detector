package spotbot.logic

import spotbot.domain.TwitterAccount
import spotbot.service.BotRepository

import scala.collection.immutable
import scala.util.Random


object ModelTraining {

  def train(botRepository: BotRepository): (Double, Double) = {
    /**
      * Retrieve data from DB
      **/
    val allTwitterAccounts = botRepository.findAll()

    var allTwitterAccountList: List[TwitterAccount] = List()
    val accountIterator = allTwitterAccounts.iterator()

    while (accountIterator.hasNext){
      allTwitterAccountList = allTwitterAccountList :+ accountIterator.next()
    }

    val botList = allTwitterAccountList
      .filter(account => account.isBot)
    val splitBot = (botList.length * 0.75).round.toInt
    val (trainingSetBot, validationSetBot) = botList.splitAt(splitBot)

    val userList = allTwitterAccountList
      .filter(account => !account.isBot)
    val splitUser = (userList.length * 0.75).round.toInt

    val (trainingSetUser, validationSetUser) = userList.splitAt(splitUser)
    val trainingSetList = trainingSetBot ++ trainingSetUser
    val validationSet = validationSetBot ++ validationSetUser

    System.out.println(s"userList ${userList.size}")
    System.out.println(s"traningSetUser ${trainingSetUser.size}")
    System.out.println(s"validationSetUser ${validationSetUser.size}")
    System.out.println(s"botList ${botList.size}")
    System.out.println(s"trainingSetBot ${trainingSetBot.size}")
    System.out.println(s"validationSetBot ${validationSetBot.size}")

    val x: Array[Array[Double]] = getX(trainingSetList)
    val normX = normalize(x)

    val y = getY(trainingSetList)

    /**
      * Logistic Regression Model
      **/
    val optimizer =
      new LogisticRegressionOptimizer(normX, y, LogisticRegression.theta, lambda = 0.01, numIter = 10000, alpha = 0.1)

    optimizer.optimize()

    /**
      * Validation
      * */
    val xTest = getX(validationSet)
    val xTestNorm = normalize(xTest)
    val yTest: Array[Double] = getY(validationSet)

    val yPred: Array[Double] = xTestNorm.map(
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

    System.out.println("positive " + yTest.count(x=>x==1))
    System.out.println("negative " + yTest.count(x=>x==0))

    System.out.println("truePositive " + truePositive)
    System.out.println("falsePositive "+ falsePositive)
    System.out.println("falseNegative "+ falseNegative)

    System.out.println("Precision " + precision)
    System.out.println("Recall "+ recall)





    (precision, recall)

  }

  def getX(list: List[TwitterAccount]): Array[Array[Double]] =
    list.map(
      twitterAccount =>
        Array(
          1.0,
          twitterAccount.getNumTweets.toDouble,
          twitterAccount.getAverageNumActivityPerDay,
          twitterAccount.getPercentageRetweets,
          twitterAccount.getFollowingAccounts.toDouble,
          twitterAccount.getFollowersCount.toDouble,
          twitterAccount.getPublicList.toDouble,
          twitterAccount.getPercentageOfCompletion
        )
    ).toArray

  def getY(list:List[TwitterAccount]):Array[Double] =
    list.map(
      twitterAccount =>
        if (twitterAccount.getIsBot) 1.0 else 0.0
    ).toArray

  def normalize(x: Array[Array[Double]]): Array[Array[Double]] = {

    val xT: Array[Array[Double]] = x.transpose

    val xAvg: Seq[Double] = xT.map{

      feature =>

        feature.foldLeft((0.0, 1)) { case ((avg, idx), next) => (avg + (next - avg)/idx, idx + 1) }._1

    }.toArray

    val xStdDev: Seq[Double] = xT.zip(xAvg).map{

      case (feature, mean) =>

        val sqrMeanError = feature.map( x => (x - mean)*(x - mean) )

        sqrMeanError.foldLeft((0.0, 1)) { case ((avg, idx), next) => (avg + (next - avg)/idx, idx + 1) }._1

    }.toArray

    val normX: Array[Array[Double]] = x.map(
      y =>
        y.zip(0.0 +: xAvg.tail).zip(1.0 +: xStdDev.tail).map(
          z =>
            (z._1._1 - z._1._2) / (z._2)
        )
    )

    normX

  }
}
