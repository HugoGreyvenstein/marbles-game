package com.game.marblepits.entities;

import com.game.marblepits.engine.PlayerHand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Slf4j
public class Board
{
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;
    private Player currentPlayer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Map<Player, PlayerPit> pits = new LinkedHashMap<>();

    public void initialize()
    {
        currentPlayer = Player.PLAYER_1;
        pits.put(Player.PLAYER_1, new PlayerPit());
        pits.put(Player.PLAYER_2, new PlayerPit());
    }

    public int[] getPlayer1Pits()
    {
        int[] pits = Arrays.copyOf(this.pits.get(Player.PLAYER_1).getPits(), 6);

        ArrayUtils.reverse(pits);
        return pits;
    }

    public int[] getPlayer2Pits()
    {
        return pits.get(Player.PLAYER_2).getPits();
    }

    public int getPlayer1LargePit()
    {
        return pits.get(Player.PLAYER_1).getLargePit();
    }

    public int getPlayer2LargePit()
    {
        return pits.get(Player.PLAYER_2).getLargePit();
    }

    public void sowFrom(int position)
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

        if (!lastInPit) {
            currentPlayer = Player.other(currentPlayer);
        }
    }

    public boolean shouldEndGame()
    {

        List<Integer> integerStream = pits.values().stream().map(PlayerPit::pitsSum).collect(Collectors.toCollection(ArrayList::new));
        log.trace("pits-sum={}", integerStream);
        return pits.values().stream().map(PlayerPit::pitsSum).anyMatch(i -> i == 0);
    }

    public int getPlayerScore(Player player)
    {
        int score = pits.get(player).score();
        log.info("player={}, score={}", player, score);
        return score;
    }

    public Player getLeader()
    {
        int player1Score = pits.get(Player.PLAYER_1).score();
        int player2Score = pits.get(Player.PLAYER_2).score();

        if (player1Score > player2Score) {
            return Player.PLAYER_1;
        } else if (player2Score > player1Score) {
            return Player.PLAYER_2;
        }
        return Player.TIE;
    }

    public enum Player
    {
        PLAYER_1,
        PLAYER_2,
        TIE;

        public static Player other(Player player)
        {
            if (PLAYER_1.equals(player))
                return PLAYER_2;
            return PLAYER_1;
        }
    }
}
