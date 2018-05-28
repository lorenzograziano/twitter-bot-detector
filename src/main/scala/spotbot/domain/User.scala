package spotbot.domain

import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
import scala.annotation.meta.field

@Entity
class User(@(Id @field) @(GeneratedValue @field) @BeanProperty var id: Long,
           @BeanProperty @(NotEmpty @field) var name: String,
           @BeanProperty @(NotEmpty @field) var lastName: String,
           @BeanProperty @(NotEmpty @field) var nickname: String,
           @BeanProperty @(NotEmpty @field) var mail: String,
           @BeanProperty @(NotEmpty @field) var registrationDate: Long) {

  def this() = this(null, null, null, null, null, null)
}