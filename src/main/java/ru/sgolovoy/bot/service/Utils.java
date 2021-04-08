package ru.sgolovoy.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.sgolovoy.bot.model.Command;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.Training;
import ru.sgolovoy.bot.model.TrainingType;

import java.util.List;
import java.util.stream.Collectors;

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

    public static SendMessage trainingList(List<Training> trainings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите тренировку");
        sendMessage.setReplyMarkup(makeKeyboard(trainings));
        return sendMessage;
    }

    public static SendMessage trainingTypeKeyboard() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Тип тренировки");
        sendMessage.setReplyMarkup(makeTrainingTypeKeyboard());
        return sendMessage;
    }

    public static InlineKeyboardMarkup makeKeyboard(List<Training> trainings) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = trainings.stream().
                map(t -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(t.pretty());
                    button.setCallbackData(t.getId().toString());
                    return button;
                })
                .map(List::of)
                .collect(Collectors.toList());
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup makeTrainingTypeKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(
                trainingTypeButton(TrainingType.BEACH),
                trainingTypeButton(TrainingType.CLASSIC)
        )));
        return inlineKeyboardMarkup;
    }

    static InlineKeyboardButton trainingTypeButton(TrainingType trainingType) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(trainingType.name());
        button.setCallbackData(trainingType.name());
        return button;
    }

    public static SendMessage mainMenu(Context context) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Отлично, " + context.getUserName() + ", выберите пункт меню");
        sendMessage.setReplyMarkup(makeMainMenuKeyboard());
        return sendMessage;
    }

    public static ReplyKeyboardMarkup makeMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> buttons =
                Command.COMMANDS.stream()
                        .map(c -> {
                            KeyboardRow keyboardRow = new KeyboardRow();
                            keyboardRow.add(c);
                            return keyboardRow;
                        })
                        .collect(Collectors.toList());

        replyKeyboardMarkup.setKeyboard(buttons);
        return replyKeyboardMarkup;
    }
}
