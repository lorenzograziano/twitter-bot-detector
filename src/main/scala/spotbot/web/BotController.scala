package spotbot.web

import java.lang.Long

import javax.validation.Valid
import spotbot.domain.{ListUserAccount, TwitterAccount}
import spotbot.service.BotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import spotbot.logic.{ModelTraining, TwitterUtils}

@Controller
@RequestMapping(Array("/bots"))
class BotController @Autowired()(private val botRepository: BotRepository) {

  @GetMapping
  def list(model: Model) = {
    val bots = botRepository.findAll()
    model.addAttribute("bots", bots)
    "bots/list"
  }

  @GetMapping(Array("/edit/{id}"))
  def edit(@PathVariable("id") id: Long, model: Model) = {
    model.addAttribute("bot", botRepository.findOne(id))
    "bots/edit"
  }

  @GetMapping(params = Array("form"))
  def createForm(model: Model) = {
    model.addAttribute("bot", new TwitterAccount())
    "bots/create"
  }

  @GetMapping(params = Array("formListUser"))
  def createFormListAccount(model: Model) = {
    model.addAttribute("listUser", new ListUserAccount())
    "bots/createList"
  }

  @PostMapping(value = Array("/createList"))
  def createList(@Valid listUser: ListUserAccount, bindingResult: BindingResult) =
    if (bindingResult.hasErrors()) {
      "bots/createList"
    } else {
      System.out.println("Creator: "+listUser.ownerList)

      val listUserAccount = TwitterUtils.getListOfTwitterAccount(listUser)
      System.out.println("first user: "+listUserAccount.head)
      listUserAccount.foreach{
        accountName =>
          val account = new TwitterAccount()
          account.twitterName = accountName
          account.idMarker = "Admin"
          account.markingDate = System.currentTimeMillis()
          account.isBot = listUser.isBotList
          botRepository.save(account.getBotWithFeatures)
      }
      "redirect:/bots"
    }

  @PostMapping(value = Array("/create"))
  def create(@Valid bot: TwitterAccount, bindingResult: BindingResult) =
    if (bindingResult.hasErrors()) {
      "bots/create"
    } else {
      bot.markingDate = System.currentTimeMillis()
      bot.isBot = true
      botRepository.save(bot.getBotWithFeatures)
      "redirect:/bots"
    }
  @PostMapping(value = Array("/createUser"))
  def createUser(@Valid user: TwitterAccount, bindingResult: BindingResult) = {
      user.markingDate = System.currentTimeMillis()
      user.isBot = false
      user.idMarker = "Admin"
      botRepository.save(user.getBotWithFeatures)
      "redirect:/bots"
    }


  @PostMapping(value = Array("/update"))
  def update(@Valid bot: TwitterAccount, bindingResult: BindingResult) =
    if (bindingResult.hasErrors()) {
      "bots/edit"
    } else {
      botRepository.save(bot)
      "redirect:/bots"
    }

  @GetMapping(value = Array("/train"))
  def train(model: Model) = {
      val result = ModelTraining.train(botRepository)
      model.addAttribute("precision", result._1)
      model.addAttribute("recall", result._2)
      model.addAttribute("accuracy", result._3)

    "/bots/resultOfTrain"
    }



  @GetMapping(value = Array("/delete/{id}"))
  def delete(@PathVariable("id") id: Long) = {
    botRepository.delete(id)
    "redirect:/bots"
  }

}