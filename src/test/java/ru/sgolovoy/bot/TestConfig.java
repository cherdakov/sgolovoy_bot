package ru.sgolovoy.bot;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.sgolovoy.bot.telegram.Bot;

@Configuration
@Import(BotApplication.class)
public class TestConfig {

    @Bean
    @Primary
    TelegramBotsApi telegramBotsApi() {
        return Mockito.mock(TelegramBotsApi.class);
    }
}
