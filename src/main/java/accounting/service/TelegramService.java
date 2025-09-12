package accounting.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        try {
            // Изпращане без URL-кодиране
            String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=HTML",
                botToken,
                chatId,
                message.replaceAll("&", "&amp;")
                       .replaceAll("<", "&lt;")
                       .replaceAll(">", "&gt;")
            );
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.err.println("Неуспешно изпращане на Telegram съобщение: " + e.getMessage());
        }
    }
}
