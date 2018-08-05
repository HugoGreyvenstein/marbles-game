package com.game.marblepits.entities;

import com.game.marblepits.engine.PlayerHand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class Board
{
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;
    private Player currentPlayer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Map<Player, PlayerPit> pits = new HashMap<>();

    public void initialize()
    {
        currentPlayer = Player.PLAYER_1;
        pits.put(Player.PLAYER_1, new PlayerPit());
        pits.put(Player.PLAYER_2, new PlayerPit());
    }

    public Player sowFrom(int position)
    {
        PlayerPit currentPlayersPit = pits.get(currentPlayer);
        PlayerHand hand = currentPlayersPit.startSowingFrom(position);
        boolean lastInPit = false;
        if (hand.hasStones() && hand.getPosition() == 6) {
            currentPlayersPit.incrementLargePit();
            hand.decrementStones();
            lastInPit = true;
        } else if (!hand.hasStones() && hand.getInitialStones() == 0) {
            int stones = currentPlayersPit.takeStonesAt(hand.getPosition() - 1);
            stones += pits.get(Player.other(currentPlayer)).takeStonesAt(6 - hand.getPosition());
            currentPlayersPit.addToLargePit(stones);
        }

        Player whosePit = Player.other(currentPlayer);
        while (hand.hasStones()) {
            lastInPit = false;
            PlayerPit currentPit = pits.get(whosePit);
            hand = currentPit.continueSowingFrom(0, hand.getStones());

            if (whosePit == currentPlayer && hand.getPosition() == 6) {
                currentPit.incrementLargePit();
                hand.decrementStones();
                lastInPit = true;
            }

            if (!hand.hasStones() && hand.getInitialStones() == 0) {
                int stones = currentPit.takeStonesAt(hand.getPosition() - 1);
                stones += pits.get(Player.other(whosePit)).takeStonesAt(6 - hand.getPosition());
                currentPlayersPit.addToLargePit(stones);
            }
            whosePit = Player.other(whosePit);
        }

        if (lastInPit) {
            return currentPlayer;
        }
        currentPlayer = Player.other(currentPlayer);
        return currentPlayer;
    }

    public boolean shouldEndGame()
    {
        return pits.values().stream().map(PlayerPit::pitsSum).anyMatch(i -> i == 0);
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
