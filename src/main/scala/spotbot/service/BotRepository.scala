
package spotbot.service

import org.springframework.data.repository.CrudRepository
import spotbot.domain.Bot
import java.lang.Long

import org.springframework.stereotype.Repository

@Repository
trait BotRepository extends CrudRepository[Bot, Long]