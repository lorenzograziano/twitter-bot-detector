package spotbot.logic

import java.util
import spotbot.domain.TwitterAccount
import spotbot.service.BotRepository


object ModelTraining {

  def train(botRepository: BotRepository) = {
    /**
      * Retrieve data from DB
      **/
    val trainingSet = botRepository.findAll()

    val trainingSetList: List[TwitterAccount] = List()
    val accountIterator: util.Iterator[TwitterAccount] = trainingSet.iterator()

    accountIterator
      .forEachRemaining(x => trainingSetList :+ x)


    val x: Vector[Vector[Double]] = trainingSetList.map(
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

    val y: Vector[Double] = trainingSetList.map(
      twitterAccount =>
        if (twitterAccount.getIsBot) 1.0 else 0.0
    ).toVector

    val theta = Vector(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)


    /**
      * Logistic Regression Model
      **/

    val optimizer =
      new LogisticRegressionOptimizer(x, y, theta, lambda = .1, numIter = 10000, alpha = .1)

    optimizer.optimize()

  }

}
