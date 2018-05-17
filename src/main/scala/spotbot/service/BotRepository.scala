package spotbot.service

import org.springframework.data.repository.CrudRepository
import spotbot.domain.Bot
import java.lang.Long

trait BotRepository extends CrudRepository[Bot, Long]