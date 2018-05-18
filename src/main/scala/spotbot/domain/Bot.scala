package spotbot.domain

import javax.persistence._

import org.hibernate.validator.constraints.NotEmpty

import scala.annotation.meta.field
import scala.beans.BeanProperty

//@Entity
//class Bot(@(Id @field) @(GeneratedValue @field) @BeanProperty var id: Int,
//           @BeanProperty @(NotEmpty @field) var twitterName: String,
//           @BeanProperty @(NotEmpty @field) var idMarker: String,
//           @BeanProperty @(NotEmpty @field) var markingDate: Int
////           @BeanProperty @(NotEmpty @field) var features: BotFeatureVect
//          ) {
//
//  def this() = this(0, null, null, 0)
//}

@Entity
class Bot extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}