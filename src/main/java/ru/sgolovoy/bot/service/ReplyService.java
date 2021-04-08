package ru.sgolovoy.bot.service;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.State;
import ru.sgolovoy.bot.telegram.Bot;

@Service
public class ReplyService {

    private final Bot bot;
    private final TrainingService trainingService;
    private final UserStateService userStateService;

    public ReplyService(Bot bot, TrainingService trainingService, UserStateService userStateService) {
        this.bot = bot;
        this.trainingService = trainingService;
        this.userStateService = userStateService;
    }

    void reply(Context context) {
        bot.sendMessage(getCurrentStateDefaultMessage(context), context.getChatId());
    }

    public SendMessage getCurrentStateDefaultMessage(Context context) {
        State state = context.getUserState().getState();
        switch (state) {
            case MAIN_MENU:
                return Utils.mainMenu(context);
            case INTRODUCE:
                return Utils.sendMessage("Представьтесь, пожалуйста");
            case PICK_TRAINING_TO_EDIT:
                return Utils.trainingList(trainingService.getAllTrainings());
            case SET_TRAINING_TYPE:
                return Utils.trainingTypeKeyboard();
            case SET_TRAINING_TIME:
                return Utils.reply("Укажите время");
        }
        return Utils.sendMessage("Тут должно быть главное меню");
    }
}
