package ru.jadae.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private final String playerName;
    private boolean isActive = false;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void move(){

    }

}
