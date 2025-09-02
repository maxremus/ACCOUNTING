package accounting.service;

import accounting.model.ContactForm;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContact(ContactForm f) {
        SimpleMailMessage m = new SimpleMailMessage();
        // до кого да пристигат запитванията
        m.setTo("marian_87_vd@abv.bg");  // смени с твоя имейл
        m.setSubject("Ново запитване от сайта: " + safe(f.getName()));
        m.setText("""
                Име: %s
                Email: %s
                Телефон: %s

                Съобщение:
                %s
                """.formatted(
                safe(f.getName()),
                safe(f.getEmail()),
                f.getPhone() == null ? "" : safe(f.getPhone()),
                safe(f.getMessage())
        ));
        // от кой адрес да се изпрати (обикновено това е spring.mail.username)
        // не всички SMTP позволяват произволен From:
        // m.setFrom("no-reply@your-domain.bg");
        m.setFrom("marian_87_vd@abv.bg");       // From = твоя ABV адрес
        m.setReplyTo(f.getEmail());     // за „Отговор до“ – клиента
        // така могат да ти „Отговорят“ директно към клиента:
        if (f.getEmail() != null && !f.getEmail().isBlank()) {
            m.setReplyTo(f.getEmail());
        }
        mailSender.send(m);
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }
}
