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
import accounting.service.TelegramService;

@Controller
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final TelegramService telegramService;

    public ContactController(TelegramService telegramService) {
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
        // Ако искаш имейл – запази това
        // mailService.sendContact(form);

        // Telegram съобщение
        String tgMessage = "Нов контакт!\n" +
                           "Име: " + form.getName() + "\n" +
                           "Имейл: " + form.getEmail() + "\n" +
                           "Съобщение: " + form.getMessage();

        telegramService.sendMessage(tgMessage);

        ra.addFlashAttribute("success", true);
        return "redirect:/kontakt";
    } catch (Exception ex) {
        log.error("Грешка при изпращане на съобщение", ex);
        model.addAttribute("sendError", "Възникна грешка при изпращането. Опитайте отново по-късно.");
        return "kontakt";
        }
    }
}

