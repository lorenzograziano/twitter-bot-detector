package spotbot.domain

case class BotFeatureVector (numTweets: Int,
                           averageNumActivityPerDay: Double,
                           percentageRetweets: Double,
                           followingAccounts: Int,
                           followersCount: Int,
                           isVerified: Int,
                           publicList: Int,
                           percentageOfCompletion: Double
                          )