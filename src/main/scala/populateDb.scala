object populateDb extends App{


  import java.io.{BufferedWriter, File, FileWriter}

  import com.danielasfregola.twitter4s.TwitterRestClient
  import com.danielasfregola.twitter4s.entities._


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

  val botList = restClient.listMembersBySlugAndOwnerName("omnibots", "botALLY", count = 250)

  val bots: RatedData[Users] = Await.result(botList, Duration(1000, "seconds"))

  val botsName = bots.data.users.map(_.screen_name)

  val firstUserList = restClient.listMembersBySlugAndOwnerName("verified accounts", "verified", count = 450)

  val firstUsers = Await.result(firstUserList, Duration(1000, "seconds"))

  val firstUsersName = firstUsers.data.users.map(_.screen_name)

  val secondUserList = restClient.listMembersBySlugAndOwnerName("Players", "MLB", count = 450)

  val secondUsers = Await.result(secondUserList, Duration(1000, "seconds"))

  val secondUsersName = secondUsers.data.users.map(_.screen_name)

  val file = new File("/home/stefano/Documents/bot.txt")
  val fileUser = new File("/home/stefano/Documents/user.txt")

  val bw = new BufferedWriter(new FileWriter(file))
  val user = new BufferedWriter(new FileWriter(fileUser))


  botsName.foreach{
    bot => bw.write(bot +"\n")
  }
  bw.close()

  firstUsersName.zip(secondUsersName).foreach{
    f => {
      user.write(f._1 + "\n")
      user.write(f._2 + "\n")
    }
  }
  user.close()
}
