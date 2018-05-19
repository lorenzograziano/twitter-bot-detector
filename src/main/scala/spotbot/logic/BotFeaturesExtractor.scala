package spotbot.logic

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities._
import spotbot.domain.{Bot, BotFeatureVector, BotFeatureVector}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object BotFeaturesExtractor {

val restClient = TwitterRestClient(
  ConsumerToken("YxTYcBjSPPJ3EkHnQ4fMwZ8HY", "TbfnuWRNxu4zlGZY7gRUUcLWilfiX23iOyN3Ry8oD4MKL7ASjD"),
    AccessToken("997092367361495041-XlhTtX5sozW0Mm4YUkU8IP7gC3d31Cc", "kI7KRO5Q3UO8QCfSD0BfcvKrwMP9LVaxMIGxJYzFXCgB3")
)


  def getFeatures(bot: Bot): BotFeatureVector = {

    //Get all the tweets for the selected user
    val userTimeLine = restClient.userTimelineForUser(bot.twitterName)

    val userInfo = restClient.user(bot.twitterName)

    val tweetWrapper: RatedData[Seq[Tweet]] = Await.result(userTimeLine,Duration(20, "seconds"))

    val infosWrapper: RatedData[User] = Await.result(userInfo, Duration(20, "seconds"))

    val tweets: Seq[Tweet] = tweetWrapper.data
    val user: User = infosWrapper.data

    val numTweets: Int = tweets.length

    val averageNumActivityPerDay: Double = average(tweets.groupBy(_.created_at).map(_._2.length).toSeq)

    val percentageRetweets: Double = tweets.count(_.retweeted_status.isDefined)/numTweets

    val followingAccounts: Int = user.friends_count

    val followersCount: Int = user.followers_count

    val isVerified: Int = if ( user.verified ) 1 else 0

    val publicList: Int = user.listed_count

    val percentageOfCompletion = countQualifyingInfo(user)

    //Other potential useful features
    //val statusCount = user.statuses_count
    //val createdAt = user.created_at

    BotFeatureVector(numTweets,
      averageNumActivityPerDay,
      percentageRetweets,
      followingAccounts,
      followersCount,
      isVerified,
      publicList,
      percentageOfCompletion)

  }


  private def average(s: Seq[Int]): Double = {

    s.foldLeft((0.0, 1)) { case ((avg, idx), next) => (avg + (next - avg)/idx, idx + 1) }._1

  }


  private def countQualifyingInfo(user: User): Double = {

    val profileInfos = Seq(user.location.isDefined,
    user.url.isDefined,
    user.blocking,
    !user.default_profile,
    !user.default_profile_image,
    user.description.isDefined,
    user.email.isDefined)

    profileInfos.count(x => x)/7.0

  }

}