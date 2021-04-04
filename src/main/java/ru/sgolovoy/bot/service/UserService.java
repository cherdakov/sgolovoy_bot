package ru.sgolovoy.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sgolovoy.bot.model.State;
import ru.sgolovoy.bot.model.User;
import ru.sgolovoy.bot.model.UserState;
import ru.sgolovoy.bot.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserStateService userStateService;

    public UserService(UserRepository userRepository, UserStateService userStateService) {
        this.userRepository = userRepository;
        this.userStateService = userStateService;
    }

    public SendMessage handleStart(Update update) {
        Long tgId = update.getMessage().getFrom().getId();
        Optional<User> schoolUser = userRepository.findById(tgId);
        if (schoolUser.isPresent()) {
            return Utils.sendWelcomeBack(schoolUser.get().getName());
        } else {
            UserState userState = userStateService.toNextState(tgId);
            return userStateService.getCurrentStateDefaultMessage(userState.getState());
        }
    }

    public SendMessage handleChangeName(Update update) {
        Long tgId = update.getMessage().getFrom().getId();
        UserState userState = userStateService.setState(tgId, State.INTRODUCE);
        return userStateService.getCurrentStateDefaultMessage(userState.getState());
    }


    public SendMessage handleName(Update update) {
        Long tgId = update.getMessage().getFrom().getId();
        Optional<User> schoolUser = userRepository.findById(tgId);
        String name = schoolUser.get().getName();
        return Utils.reply(name);
    }

    public SendMessage updateName(User user) {
        userRepository.save(user);
        UserState userState = userStateService.toNextState(user.getId());
        return userStateService.getCurrentStateDefaultMessage(userState.getState());
    }

}
