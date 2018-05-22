package spotbot.web

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import spotbot.domain.{TwitterAccount, BotRequest}
import spotbot.service.BotRequestRepository

@Controller
@RequestMapping(Array("/bots"))
class BotRequestController @Autowired()(private val botRequestRepository: BotRequestRepository) {

  @GetMapping(params = Array("askForm"))
  def createAskForm(model: Model) = {
    model.addAttribute("botRequest", new BotRequest())
    "/ask"
  }

  @PostMapping(value = Array("/ask"))
  def ask(@Valid bot: TwitterAccount, bindingResult: BindingResult) =
    if (bindingResult.hasErrors()) {
      "bots/create"
    } else {
      "bots/create"
    }

}