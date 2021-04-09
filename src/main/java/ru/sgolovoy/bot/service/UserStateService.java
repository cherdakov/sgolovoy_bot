package ru.sgolovoy.bot.service;

import org.springframework.stereotype.Service;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.State;
import ru.sgolovoy.bot.model.UserState;
import ru.sgolovoy.bot.repository.UserStateRepository;

import static ru.sgolovoy.bot.model.State.COMMIT_TRAINING;
import static ru.sgolovoy.bot.model.State.INTRODUCE;
import static ru.sgolovoy.bot.model.State.MAIN_MENU;
import static ru.sgolovoy.bot.model.State.SET_TRAINING_TIME;
import static ru.sgolovoy.bot.model.State.SET_TRAINING_TYPE;

@Service
public class UserStateService {

    private final UserStateRepository userStateRepository;

    public UserStateService(UserStateRepository userStateRepository) {
        this.userStateRepository = userStateRepository;
    }

    private State getNextState(State state) {
        if (state == null) {
            return INTRODUCE;
        }
        switch (state) {
            case TRAINING_INFO:
                return COMMIT_TRAINING;
            case SET_TRAINING_TYPE:
                return SET_TRAINING_TIME;
            case PICK_TRAINING_TO_EDIT:
                return SET_TRAINING_TYPE;
        }
        return MAIN_MENU;
    }

    public void toNextState(Context context) {
        UserState userState = context.getUserState();
        userState.setState(getNextState(userState.getState()));
        setState(context);
    }

    public void setState(Context context) {
        userStateRepository.save(context.getUserState());
    }

    public UserState getState(Long tgId) {
        return userStateRepository.findById(tgId).orElse(new UserState(tgId, State.NONE));
    }
}
