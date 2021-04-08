package ru.sgolovoy.bot.model;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
public class Context {
    Update update;
    boolean isCallback;
    boolean isMessage;
    long tgId;
    String chatId;
    String data;
    UserState userState;
    String userName;

    public Context(Update update,
                   boolean isCallback,
                   boolean isMessage,
                   long tgId,
                   String chatId,
                   String data,
                   UserState userState,
                   String userName) {
        this.update = update;
        this.isCallback = isCallback;
        this.isMessage = isMessage;
        this.tgId = tgId;
        this.chatId = chatId;
        this.data = data;
        this.userState = userState;
        this.userName = userName;
    }

}
