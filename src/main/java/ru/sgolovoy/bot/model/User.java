package ru.sgolovoy.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "bot")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
}
