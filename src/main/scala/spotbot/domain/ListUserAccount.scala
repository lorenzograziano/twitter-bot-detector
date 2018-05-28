package spotbot.domain

import javax.persistence._

import scala.beans.BeanProperty

@Entity
class ListUserAccount extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @BeanProperty
  var id: Long = _

  @BeanProperty
  @Column(name = "ownerList")
  var ownerList: String = _

  @BeanProperty
  @Column(name = "slug")
  var slug: String = _

  @BeanProperty
  @Column(name = "isBotList")
  var isBotList: Boolean = _
}