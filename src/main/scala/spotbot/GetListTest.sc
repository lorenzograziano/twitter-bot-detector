import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities._

import scalaj.http.{Http, HttpOptions}
import scala.concurrent.Await
import scala.concurrent.duration.Duration

val restClient = TwitterRestClient(
  ConsumerToken("YxTYcBjSPPJ3EkHnQ4fMwZ8HY", "TbfnuWRNxu4zlGZY7gRUUcLWilfiX23iOyN3Ry8oD4MKL7ASjD"),
  AccessToken("997092367361495041-XlhTtX5sozW0Mm4YUkU8IP7gC3d31Cc", "kI7KRO5Q3UO8QCfSD0BfcvKrwMP9LVaxMIGxJYzFXCgB3")
)

//val botLists = Seq(
//  ("tiny_universe", "hugovk"),
//  ("omnibots", "botALLY"),
//  ("Activist Bots", "Leonardo_UPRM"),
//  ("Self-care bots ", "clairesayswhat"),
//  ("Chatbots on Twitter ", "SamSchmir"))

//def extractBot(listUser: Seq[(String, String)]): Set[String] = {
//  var bots = Seq.empty
//  listUser.map{
//    (list, user) =>
//
//      bots ++ restClient.listMembersBySlugAndOwnerName(list, user)
//
//  }

val botList = restClient.listMembersBySlugAndOwnerName("omnibots", "botALLY", count = 100)

val bots: RatedData[Users] = Await.result(botList, Duration(1000, "seconds"))

val botsName = bots.data.users.map(_.screen_name)

val userList = restClient.listMembersBySlugAndOwnerName("tech", "davidebradford", count = 500)

val users = Await.result(userList, Duration(1000, "seconds"))

val usersName = users.data.users.map(_.screen_name)

botsName.foreach{
   bot =>

    Http("http://localhost:8080/bots/create").postData(s"""{"twitterName":$bot,"idMarker":"Admin"}""")
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString
}

usersName.foreach{
  user =>

    Http("http://localhost:8080/bots/create").postData(s"""{"twitterName":$user,"idMarker":"Admin"}""")
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString
}