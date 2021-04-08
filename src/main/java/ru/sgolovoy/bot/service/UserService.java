package ru.sgolovoy.bot.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.User;
import ru.sgolovoy.bot.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserStateService userStateService;

    public UserService(UserRepository userRepository, UserStateService userStateService) {
        this.userRepository = userRepository;
        this.userStateService = userStateService;
    }

    public Optional<User> getUser(long tgIg) {
        return userRepository.findById(tgIg);
    }

    public void updateName(Context context) {
        String name = context.getData();
        userRepository.save(new User(context.getTgId(), name));
        context.setUserName(name);
    }

    public void handleName(Context update) {
        Optional<User> schoolUser = userRepository.findById(update.getTgId());
        String name = schoolUser.get().getName();
    }

    public void updateName(User user) {
        userRepository.save(user);
    }

}
