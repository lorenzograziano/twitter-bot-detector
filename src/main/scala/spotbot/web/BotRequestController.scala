package spotbot.web

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import spotbot.domain.{BotRequest, TwitterAccount}
import spotbot.logic.{LogisticRegression, ModelTraining}
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
  def ask(model: Model, @Valid botRequest: BotRequest, bindingResult: BindingResult) = {

    //Initialize bot
    val bot = new TwitterAccount()
    bot.twitterName = botRequest.twitterName

    //Get features and make prediction
    val vec = ModelTraining.getX( List(bot.getBotWithFeatures()))
    val prob = LogisticRegression.hTheta(vec.head)

    //Save botRequest
    botRequest.probabilityBot = prob
    botRequest.requestDate = System.currentTimeMillis()
    botRequestRepository.save(botRequest)

    //Show results
    model.addAttribute("botName", bot.twitterName)
    model.addAttribute("probability", prob)
    "botRequest/askResult"
    }

}