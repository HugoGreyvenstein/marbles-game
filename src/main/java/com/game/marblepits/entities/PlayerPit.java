package com.game.marblepits.entities;

import com.game.marblepits.engine.PlayerHand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPit
{
    @Id
    @GeneratedValue
    private Long id;

    private int[] pits = new int[] {6, 6, 6, 6, 6, 6};
    private int largePit = 0;

    public PlayerHand startSowingFrom(int position)
    {
        int stones = this.pits[position];
        this.pits[position] = 0;
        return continueSowingFrom(position + 1, stones);
    }

    public PlayerHand continueSowingFrom(int position, int stones)
    {
        int pitStones = -1;
        for (; position < pits.length && stones > 0; position++, stones--) {
            pitStones = pits[position]++;
        }
        return new PlayerHand(position, stones, pitStones);
    }

    public int takeStonesAt(int position)
    {
        int stones = pits[position];
        pits[position] = 0;
        return stones;
    }

    public void incrementLargePit()
    {
        this.largePit++;
    }

    public void addToLargePit(int amount)
    {
        largePit += amount;
    }

    public int pitsSum()
    {
        return Arrays.stream(pits).sum();
    }

    public int score()
    {
        return largePit + pitsSum();
    }
}