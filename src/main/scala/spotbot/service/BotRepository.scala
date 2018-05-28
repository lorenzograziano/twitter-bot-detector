
package spotbot.service

import org.springframework.data.repository.CrudRepository
import spotbot.domain.TwitterAccount
import java.lang.Long

import org.springframework.stereotype.Repository

@Repository
trait BotRepository extends CrudRepository[TwitterAccount, Long]