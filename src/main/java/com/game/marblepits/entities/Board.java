package com.game.marblepits.entities;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Board
{
    private Map<Player, PlayerPit> pits = new HashMap<>();

    public Board()
    {
        pits.put(Player.PLAYER_1, new PlayerPit());
        pits.put(Player.PLAYER_2, new PlayerPit());
    }

    public enum Player
    {
        PLAYER_1,
        PLAYER_2
    }
}
