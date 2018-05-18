package spotbot.logic

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, RatedData, Tweet}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object getBot {

val restClient = TwitterRestClient(
  ConsumerToken("YxTYcBjSPPJ3EkHnQ4fMwZ8HY", "TbfnuWRNxu4zlGZY7gRUUcLWilfiX23iOyN3Ry8oD4MKL7ASjD"),
    AccessToken("997092367361495041-XlhTtX5sozW0Mm4YUkU8IP7gC3d31Cc", "kI7KRO5Q3UO8QCfSD0BfcvKrwMP9LVaxMIGxJYzFXCgB3")
)

// List of tweet
val timeLine = restClient.userTimelineForUser("noiano")

val test: Future[RatedData[Seq[Tweet]]] = timeLine

val result = Await.result(test,Duration(20, "seconds"))

val tweet = result.data.head

println(tweet.text)

println(tweet.created_at)


println(result.data(1).text)
println(result.data(1).id)
val tweet2 =
Await.result(restClient.getTweet(result.data(1).id), Duration(20, "seconds"))


println(tweet.user)
println(tweet2.data.user)

}
