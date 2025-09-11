package accounting.controller;

import accounting.model.ContactForm;
import accounting.service.MailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final MailService mailService;
    private final TelegramService telegramService;

    public ContactController(MailService mailService, TelegramService telegramService) {
        this.mailService = mailService;
        this.telegramService = telegramService;
    }


    @GetMapping("/kontakt")
    public String contact(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new ContactForm());
        }
        return "kontakt";
    }

    @PostMapping("/kontakt")
    public String submit(@Valid @ModelAttribute("form") ContactForm form,
                         BindingResult result,
                         RedirectAttributes ra,
                         Model model) {
        if (result.hasErrors()) {
            return "kontakt";
        }
        try {
            // Изпращане на имейл
            mailService.sendContact(form);

            // Изпращане на Telegram известие
            String tgMessage = String.format(
                    "Нов контакт!\nИме: %s\nИмейл: %s\nСъобщение: %s",
                    form.getName(),
                    form.getEmail(),
                    form.getMessage()
            );
            telegramService.sendMessage(tgMessage);

            ra.addFlashAttribute("success", true);
            return "redirect:/kontakt";
        } catch (Exception ex) {
            log.error("Грешка при изпращане на имейл", ex);
            model.addAttribute("sendError", "Възникна грешка при изпращането. Опитайте отново по-късно.");
            return "kontakt";
        }
   }
}
