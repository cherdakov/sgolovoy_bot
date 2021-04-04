package ru.sgolovoy.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgolovoy.bot.model.Training;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
