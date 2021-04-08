package ru.sgolovoy.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "bot")
public class User {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_trainer")
    private Boolean isTrainer = false;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
