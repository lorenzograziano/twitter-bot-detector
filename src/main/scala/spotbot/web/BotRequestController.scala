package spotbot.web

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import spotbot.domain.{BotFeatureVector, BotRequest, TwitterAccount}
import spotbot.logic.{LogisticRegression, ModelTraining, TwitterUtils}
import spotbot.service.BotRequestRepository

@Controller
@RequestMapping(Array("/botRequest"))
class BotRequestController @Autowired()(private val botRequestRepository: BotRequestRepository) {

  @GetMapping(params = Array("askForm"))
  def createAskForm(model: Model) = {
    model.addAttribute("botRequest", new BotRequest())
    "botRequest/ask"
  }

  @PostMapping(value = Array("/ask"))
  def ask(model: Model, @Valid bot: TwitterAccount, bindingResult: BindingResult) = {

    val vec = ModelTraining.getX( List(bot.getBotWithFeatures()))
    val prob = LogisticRegression.hTheta(vec.head)

    model.addAttribute("botName", bot.twitterName)
    model.addAttribute("probability", prob)

    "botRequest/askResult"
    }

}