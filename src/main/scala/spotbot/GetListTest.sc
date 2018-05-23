import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities._
import spotbot.domain.TwitterAccount

import scala.concurrent.Await
import scala.concurrent.duration.Duration

val restClient = TwitterRestClient(
  ConsumerToken("YxTYcBjSPPJ3EkHnQ4fMwZ8HY", "TbfnuWRNxu4zlGZY7gRUUcLWilfiX23iOyN3Ry8oD4MKL7ASjD"),
  AccessToken("997092367361495041-XlhTtX5sozW0Mm4YUkU8IP7gC3d31Cc", "kI7KRO5Q3UO8QCfSD0BfcvKrwMP9LVaxMIGxJYzFXCgB3")
)

val bot1 = new TwitterAccount()

bot1.twitterName = "botALLY"
bot1.idMarker =  "SS"
bot1.markingDate = System.currentTimeMillis()

val list = restClient.listMembersBySlugAndOwnerName("omnibots",bot1.twitterName)

val a: RatedData[Users] = Await.result(list, Duration(1000, "seconds"))

val b = a.data.users.map(_.name)

b.length

