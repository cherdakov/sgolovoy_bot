package ru.sgolovoy.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sgolovoy.bot.model.User;
import ru.sgolovoy.bot.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void create(Long tgId, String name) {
        userRepository.save(new User(tgId, name));
    }

    public void handleStart(Update update) {
        update.getMessage();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }
}
