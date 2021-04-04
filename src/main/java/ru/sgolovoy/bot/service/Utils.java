package ru.sgolovoy.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

public class Utils {

    public static SendMessage sendWelcomeBack(String name) {
        return reply("welcome back " + name);
    }

    public static SendMessage sendMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        return sendMessage;
    }

    public static SendMessage reply(String text) {
        return sendMessage(text);
    }
}
