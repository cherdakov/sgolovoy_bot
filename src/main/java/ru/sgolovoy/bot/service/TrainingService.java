package ru.sgolovoy.bot.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import ru.sgolovoy.bot.model.Context;
import ru.sgolovoy.bot.model.Training;
import ru.sgolovoy.bot.model.TrainingType;
import ru.sgolovoy.bot.model.UserState;
import ru.sgolovoy.bot.repository.TrainingRepository;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;


    public TrainingService(TrainingRepository repository) {
        this.trainingRepository = repository;
    }

    public long createTraining(Context update) {
        Training training = trainingRepository.save(new Training());
        return training.getId();
    }

    public void setTime(Context context) {
        Instant time = Instant.parse(context.getData());
        long trainingId = Long.parseLong(context.getUserState().getMeta());
        Training training = trainingRepository.getOne(trainingId);
        training.setTime(time);
        trainingRepository.save(training);
    }

    public void setType(Context update) {
        TrainingType type = TrainingType.valueOf(update.getData());
        long trainingId = Long.parseLong(update.getUserState().getMeta());
        Training training = trainingRepository.getOne(trainingId);
        training.setType(type);
        trainingRepository.save(training);
    }

    List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }
}
