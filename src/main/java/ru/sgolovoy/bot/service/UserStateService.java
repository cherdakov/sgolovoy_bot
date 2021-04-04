package ru.sgolovoy.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sgolovoy.bot.model.State;
import ru.sgolovoy.bot.model.UserState;
import ru.sgolovoy.bot.repository.UserStateRepository;

import java.util.Optional;

import static ru.sgolovoy.bot.model.State.*;

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
        }
        return MAIN_MENU;
    }

    public UserState toNextState(Long userId) {
        Optional<UserState> oldState = userStateRepository.findById(userId);
        State nextState = getNextState(oldState.map(UserState::getState).orElse(null));
        UserState userState = new UserState(userId, nextState);
        userStateRepository.save(userState);
        return userState;
    }

    public UserState setState(Long userId, State state) {
        userStateRepository.save(new UserState(userId, state));
        return new UserState(userId, state);
    }

    public SendMessage getCurrentStateDefaultMessage(State state) {
        switch (state) {
            case INTRODUCE:
                return Utils.sendMessage("Представьтесь, пожалуйста");
        }
        return Utils.sendMessage("Тут должно быть главное меню");
    }

    public UserState getState(Long tgId) {
        return userStateRepository.getOne(tgId);
    }
}
