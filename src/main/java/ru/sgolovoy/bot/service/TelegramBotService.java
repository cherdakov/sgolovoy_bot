package ru.sgolovoy.bot.service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.sgolovoy.bot.model.Command;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.State;
import ru.sgolovoy.bot.model.User;
import ru.sgolovoy.bot.model.UserState;

@Slf4j
@Service
public class TelegramBotService {

    private final UserService userService;
    private final UserStateService userStateService;
    private final TrainingService trainingService;
    private final QueueService queueService;
    private final ReplyService replyService;

    public TelegramBotService(UserService userService,
                              UserStateService userStateService,
                              TrainingService trainingService,
                              QueueService queueService,
                              ReplyService replyService) {
        this.userService = userService;
        this.userStateService = userStateService;
        this.trainingService = trainingService;
        this.queueService = queueService;
        this.replyService = replyService;
    }

    @Scheduled(fixedDelay = 1)
    void tryTake() {
        Context context = queueService.take();
        if (tryHandleCommand(context)) {
            userStateService.setState(context);
        } else {
            tryHandleState(context);
        }
        replyService.reply(context);
    }

    boolean tryHandleCommand(Context context) {
        UserState newState = context.getUserState();
        switch (context.getData()) {
            case Command.START:
                if (context.getUserName() == null) {
                    newState.setState(State.INTRODUCE);
                } else {
                    newState.setState(State.MAIN_MENU);
                }
                return true;
            case Command.CHANGE_NAME:
                newState.setState(State.INTRODUCE);
                return true;
            case Command.NAME:
                userService.handleName(context);
                newState.setState(State.MAIN_MENU);
                return true;
            case Command.EDIT_TRAINING:
                newState.setState(State.PICK_TRAINING_TO_EDIT);
                return true;
            case Command.CREATE_TRAINING:
                long trainingId = trainingService.createTraining(context);
                newState.setState(State.SET_TRAINING_TYPE);
                newState.setMeta(Long.toString(trainingId));
                return true;
        }
        return false;
    }


    void tryHandleState(Context context) {
        UserState userState = userStateService.getState(context.getTgId());
        switch (userState.getState()) {
            case INTRODUCE:
                userService.updateName(context);
                break;
            case SET_TRAINING_TIME:
                trainingService.setTime(context);
                break;
            case PICK_TRAINING_TO_EDIT:
                String traningId = context.getData();
                context.getUserState().setMeta(traningId);
                break;
            case SET_TRAINING_TYPE:
                trainingService.setType(context);
                break;
        }
        userStateService.toNextState(context);
    }

    void setTrainingTime(Context context) {
//

    }

}
