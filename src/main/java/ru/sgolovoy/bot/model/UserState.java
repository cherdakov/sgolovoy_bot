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
@Table(name = "user_state", schema = "bot")
public class UserState {
    @Id
    @Column(name = "user_id")
    Long userId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    State state;

    @Column(name = "meta")
    String meta;

    public UserState(Long userId, State state) {
        this.userId = userId;
        this.state = state;
    }
}
