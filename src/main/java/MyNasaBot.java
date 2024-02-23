import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

/**
 * @author AramaJava 22.02.2024
 */

public class MyNasaBot extends TelegramLongPollingBot {


    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public MyNasaBot(String botName, String botToken) throws TelegramApiException {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String answer = update.getMessage().getText();
            String [] separatedAnswer = answer.split(" ");
            String action = separatedAnswer[0];

            String NASA_URL = "https://api.nasa.gov/planetary/apod?api_key=t4x2K4RSeJDSNiOfMDdXxFJCYFsSlDoFpfFgc1qj";
            switch (action) {
                case "/help":
                    sendMessage("Я бот присылающий картинку дня, напиши /start", chatId);
                    break;
                case "/start":
                case "/image":
                    String url = Utils.getUrl(NASA_URL);
                    sendMessage(url, chatId);
                    break;
                case "/date" :
                    String date = separatedAnswer[1];
                    url = Utils.getUrl(NASA_URL + "&date=" + date);
                    sendMessage(url, chatId);
                    break;
                default:
                    sendMessage("Я не понимаю чего ты хочешь!", chatId);
            }

        }
    }

    void sendMessage(String msg, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(msg);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }
}
