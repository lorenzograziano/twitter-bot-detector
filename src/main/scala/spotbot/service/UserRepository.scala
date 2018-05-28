package spotbot.service

import spotbot.domain.User
import org.springframework.data.repository.CrudRepository
import java.lang.Long

trait UserRepository extends CrudRepository[User, Long]
