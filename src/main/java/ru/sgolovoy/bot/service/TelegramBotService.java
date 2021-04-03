package ru.sgolovoy.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TelegramBotService {

    public static final String START_COMMAND = "/start";
    private final UserService userService;

    public TelegramBotService(UserService userService) {
        this.userService = userService;
    }

    public BotApiMethod onUpdateReceived(Update update) {
        User user = update.getMessage().getFrom();
        if (START_COMMAND.equals(update.getMessage().getText())) {
            Long id = user.getId();
            Optional<ru.sgolovoy.bot.model.User> schoolUser = userService.getUser(id);
            if (schoolUser.isPresent()) {
                return sendWelcomeBack(update.getMessage().getChat(), schoolUser.get().getName());
            } else {
                userService.create(id, UUID.randomUUID().toString());
            }
        }
        return reply(update.getMessage().getChat(), "ok");
    }


    private SendMessage sendWelcomeBack(Chat chat, String name) {
        return reply(chat, "welcome back " + name);
    }

    private SendMessage sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    private SendMessage reply(Chat chat, String text) {
        return sendMessage(chat.getId().toString(), text);
    }
}
