package ru.sgolovoy.bot.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.User;
import ru.sgolovoy.bot.model.UserState;

@Slf4j
@Service
public class QueueService {
    BlockingQueue<Context> blockingQueue = new ArrayBlockingQueue<>(100);
    private final UserStateService userStateService;
    private final UserService userService;

    public QueueService(UserStateService userStateService, UserService userService) {
        this.userStateService = userStateService;
        this.userService = userService;
    }

    @SneakyThrows
    public void put(Update update) {
        var isCallback = update.getCallbackQuery() != null;
        var isMessage = update.getMessage() != null;
        var message = isMessage ? update.getMessage() : update.getCallbackQuery().getMessage();
        var chatId = message.getChatId().toString();
        var tgId = message.getChatId();
        var data = isCallback ? update.getCallbackQuery().getData() : message.getText();
        String userName = userService.getUser(tgId).map(User::getName).orElse(null);
        UserState state = userStateService.getState(tgId);
        blockingQueue.put(new Context(update, isCallback, isMessage, tgId, chatId, data, state, userName));
    }

    @SneakyThrows
    public Context take() {
        return blockingQueue.take();
    }
}
