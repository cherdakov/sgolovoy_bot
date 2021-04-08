package ru.sgolovoy.bot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sgolovoy.bot.model.Command;
import ru.sgolovoy.bot.telegram.Bot;

@SpringBootTest(classes = TestConfig.class)
class BotApplicationTests {

    @Autowired
    Bot bot;

    @Test
    void startTest() {
        bot.onUpdateReceived(newMessageUpdate(Command.START));
        bot.onUpdateReceived(newMessageUpdate("Кирилл"));
    }

    public static Update newMessageUpdate(String text) {
        Update update = new Update();
        Message message = new Message();
        message.setText(text);
        message.setChat(new Chat(1613L, "Type"));
        message.setFrom(new User(777L, "Киря", false));
        update.setMessage(message);
        return update;
    }


}
