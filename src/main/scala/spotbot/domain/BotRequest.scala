package spotbot.domain

import javax.persistence.{Entity, GeneratedValue, Id}
import org.hibernate.validator.constraints.NotEmpty
import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
class BotRequest(@(Id @field) @(GeneratedValue @field) @BeanProperty var id: Long,
          @BeanProperty @(NotEmpty @field) var twitterName: String,
          @BeanProperty @(NotEmpty @field) var idMarker: String,
          @BeanProperty @(NotEmpty @field) var requestDate: Long,
          @BeanProperty @(NotEmpty @field) var probabilityBot: Double,
          //@BeanProperty @(NotEmpty @field) var features: BotFeatureVect
                ) {

  def this() = this(0, null, null, 0, 0)
}

