package spotbot.domain

import javax.persistence.{Entity, GeneratedValue, Id}
import org.hibernate.validator.constraints.NotEmpty
import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
class Bot(@(Id @field) @(GeneratedValue @field) @BeanProperty var id: Long,
           @BeanProperty @(NotEmpty @field) var twitterName: String,
           @BeanProperty @(NotEmpty @field) var idMarker: String,
           @BeanProperty @(NotEmpty @field) var markingDate: Long
//           @BeanProperty @(NotEmpty @field) var features: BotFeatureVect
          ) {

  def this() = this(0, null, null, 0)
}

