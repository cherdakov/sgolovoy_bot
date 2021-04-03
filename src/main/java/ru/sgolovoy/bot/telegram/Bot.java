package ru.sgolovoy.bot.telegram;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sgolovoy.bot.service.TelegramBotService;
import ru.sgolovoy.bot.service.UserService;

@Service
@Slf4j
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final TelegramBotService telegramBotService;

    public Bot(TelegramBotService telegramBotService) {
        super();
        this.telegramBotService = telegramBotService;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        BotApiMethod botApiMethod = telegramBotService.onUpdateReceived(update);
        sendApiMethod(botApiMethod);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
