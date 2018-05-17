package spotbot.web

import java.lang.Long

import javax.validation.Valid
import spotbot.domain.Bot
import spotbot.service.BotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

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
    model.addAttribute("bot", new Bot())
    "bots/create"
  }

  @PostMapping
  def create(@Valid bot: Bot, bindingResult: BindingResult) =
    if (bindingResult.hasErrors()) {
      "bots/create"
    } else {
      botRepository.save(bot)
      "redirect:/bots"
    }


  @PostMapping(value = Array("/update"))
  def update(@Valid bot: Bot, bindingResult: BindingResult) =
    if (bindingResult.hasErrors()) {
      "bots/edit"
    } else {
      botRepository.save(bot)
      "redirect:/bots"
    }


  @GetMapping(value = Array("/delete/{id}"))
  def delete(@PathVariable("id") id: Long) = {
    botRepository.delete(id)
    "redirect:/bots"
  }

}