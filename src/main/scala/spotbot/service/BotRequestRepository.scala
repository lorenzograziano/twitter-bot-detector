package spotbot.service

import org.springframework.data.repository.CrudRepository
import spotbot.domain.BotRequest
import java.lang.Long

trait BotRequestRepository extends CrudRepository[BotRequest, Long]
