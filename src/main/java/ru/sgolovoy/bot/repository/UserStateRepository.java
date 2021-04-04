package ru.sgolovoy.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgolovoy.bot.model.Training;
import ru.sgolovoy.bot.model.UserState;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserStateRepository extends JpaRepository<UserState, Long> {

}
