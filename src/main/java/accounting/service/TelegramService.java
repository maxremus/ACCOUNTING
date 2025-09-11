package accounting.service;

import org.springframework.stereotype.Service; // Липсващ импорт
import org.springframework.beans.factory.annotation.Value; // Липсващ импорт
import org.springframework.web.client.RestTemplate; // Липсващ импорт
import java.nio.charset.StandardCharsets; // Липсващ импорт
import org.springframework.web.util.UriUtils; // Липсващ импорт

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        try {
            String url = String.format(
                    "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                    botToken,
                    chatId,
                    UriUtils.encode(message, StandardCharsets.UTF_8)
            );
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            // Ако има грешка, логваме я, но не спираме приложението
            System.err.println("Неуспешно изпращане на Telegram съобщение: " + e.getMessage());
        }
    }
}
