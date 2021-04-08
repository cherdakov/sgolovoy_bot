package ru.sgolovoy.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "training", schema = "bot")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "time")
    Instant time;

    @Column(name = "trainer_id")
    Long trainerId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    TrainingType type;

    @Column(name = "visible")
    Boolean visible = false;

    public String pretty() {
        return id + "" + type + time;
    }
}
