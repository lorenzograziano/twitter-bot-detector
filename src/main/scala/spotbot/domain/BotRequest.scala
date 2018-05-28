package spotbot.domain

import javax.persistence._
import scala.beans.BeanProperty

@Entity
class BotRequest extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @BeanProperty
  var id: Long = _

  @BeanProperty
  @Column(name = "twitterName")
  var twitterName: String = _

  @BeanProperty
  @Column(name = "Asker")
  var asker: String = _

  @BeanProperty
  @Column(name = "requestDate")
  var requestDate: Long = _

  @BeanProperty
  @Column(name = "probabilityBot")
  var probabilityBot: Double = _

}

