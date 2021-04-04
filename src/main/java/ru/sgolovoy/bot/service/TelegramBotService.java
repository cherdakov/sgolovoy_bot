package ru.sgolovoy.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sgolovoy.bot.model.User;
import ru.sgolovoy.bot.model.UserState;

@Slf4j
@Service
public class TelegramBotService {

    public static final String START_COMMAND = "/start";
    public static final String CHANGE_NAME_COMMAND = "/change_name";
    public static final String NAME_COMMAND = "/name";
    private final UserService userService;
    private final UserStateService userStateService;

    public TelegramBotService(UserService userService, UserStateService userStateService) {
        this.userService = userService;
        this.userStateService = userStateService;
    }

    public BotApiMethod onUpdateReceived(Update update) {
        Long tgId = update.getMessage().getFrom().getId();
        SendMessage sendMessage = tryHandleCommand(update, tgId);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        return sendMessage;
    }

    SendMessage tryHandleCommand(Update update, Long tgId) {
        String text = update.getMessage().getText();
        switch (text) {
            case START_COMMAND:
                return userService.handleStart(update);
            case CHANGE_NAME_COMMAND:
                return userService.handleChangeName(update);
            case NAME_COMMAND:
                return userService.handleName(update);
        }
        return tryHandleState(update, tgId);
    }

    SendMessage tryHandleState(Update update, Long tgId) {
        UserState userState = userStateService.getState(tgId);
        switch (userState.getState()) {
            case INTRODUCE:
                String name = update.getMessage().getText();
                return userService.updateName(new User(tgId, name));
        }
        return Utils.reply("Ok");
    }

}
