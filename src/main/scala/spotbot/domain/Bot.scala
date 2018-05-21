package spotbot.domain

import javax.persistence._
import spotbot.logic.TwitterUtils.getFeatures
import scala.beans.BeanProperty


@Entity
class Bot extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @BeanProperty
  var id: Long = _

  @BeanProperty
  @Column(name = "twitterName")
  var twitterName: String = _

  @BeanProperty
  @Column(name = "idMarker")
  var idMarker: String = _

  @BeanProperty
  @Column(name = "markingDate")
  var markingDate: Long = _

  /**Features*/
  @BeanProperty
  @Column(name = "numTweets")
  var numTweets: Int = _

  @BeanProperty
  @Column(name = "averageNumActivityPerDay")
  var averageNumActivityPerDay: Double = _

  @BeanProperty
  @Column(name = "percentageRetweets")
  var percentageRetweets: Double = _

  @BeanProperty
  @Column(name = "followingAccounts")
  var followingAccounts: Int = _

  @BeanProperty
  @Column(name = "followersCount")
  var followersCount: Int = _

  @BeanProperty
  @Column(name = "isVerified")
  var isVerified: Int = _

  @BeanProperty
  @Column(name = "publicList")
  var publicList: Int = _

  @BeanProperty
  @Column(name = "percentageOfCompletion")
  var percentageOfCompletion: Double = _

  def getBotWithFeatures(): Bot = {
    val featureVector = getFeatures(this)
    this.setNumTweets(featureVector.numTweets)
    this.setAverageNumActivityPerDay(featureVector.averageNumActivityPerDay)
    this.setPercentageRetweets(featureVector.percentageRetweets)
    this.setFollowingAccounts(featureVector.followingAccounts)
    this.setFollowersCount(featureVector.followersCount)
    this.setIsVerified(featureVector.isVerified)
    this.setPublicList(featureVector.publicList)
    this.setPercentageOfCompletion(featureVector.percentageOfCompletion)

    this
  }


}