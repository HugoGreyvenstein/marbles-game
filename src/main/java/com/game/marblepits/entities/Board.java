package com.game.marblepits.entities;

import com.game.marblepits.engine.SowResult;
import lombok.Getter;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
public class Board
{
    private Player currentPlayer;
    private Map<Player, PlayerPit> pits = new HashMap<>();

    public void initialize()
    {
        currentPlayer = Player.PLAYER_1;
        pits.put(Player.PLAYER_1, new PlayerPit());
        pits.put(Player.PLAYER_2, new PlayerPit());
    }

    public Player sowFrom(int position)
    {
        PlayerPit pit = pits.get(currentPlayer);
        SowResult result = pit.startSowingFrom(position);
        if (result.hasStones() && result.getPosition() == 6) {
            result.setPosition(0);
            pit.incrementLargePit();
            result.decrementStones();
        }

        Player whosePit = Player.other(currentPlayer);
        while (result.hasStones() && result.getPosition() == 6) {
            PlayerPit currentPit = pits.get(whosePit);
            result = currentPit.continueSowingFrom(result.getPosition(), result.getStones());

            if (whosePit == currentPlayer) {
                currentPit.incrementLargePit();
                result.decrementStones();
            }
            whosePit = Player.other(whosePit);
        }

        if (currentPlayer.equals(whosePit)) {
            currentPlayer = whosePit;
        }

        return currentPlayer;
    }

    private boolean shouldEndGame()
    {
        return pits.get(Player.PLAYER_1).pitsSum() == 0 || pits.get(Player.PLAYER_2).pitsSum() == 0;
    }

    public enum Player
    {
        PLAYER_1,
        PLAYER_2;

        public static Player other(Player player)
        {
            if (PLAYER_1.equals(player))
                return PLAYER_2;
            return PLAYER_1;
        }
    }
}
