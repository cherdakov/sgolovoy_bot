package ru.sgolovoy.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "training", schema = "bot")
public class Training {
    @Id
    private Long id;
    @Column(name = "time")
    Instant time;
    @Column(name = "trainer_id")
    Long trainerId;
}
