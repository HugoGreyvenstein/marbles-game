package com.game.marblepits.entities;

import com.game.marblepits.engine.SowResult;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
@Getter
class PlayerPit
{
    @Id
    @GeneratedValue
    private Long id;

    private final int[] pits = new int[] {6, 6, 6, 6, 6, 6};
    private int largePit = 0;

    public SowResult startSowingFrom(int position)
    {
        int stones = this.pits[position];
        this.pits[position] = 0;
        return continueSowingFrom(position, stones);
    }

    public SowResult continueSowingFrom(int position, int stones)
    {
        int pitStones = -1;
        for (position++; position < pits.length && stones > 0; position++, stones--) {
            pitStones = pits[position]++;
        }

//        if (position == 6) {
//            position = 0;
//        }

        if (pitStones == -1) throw new RuntimeException();

        return new SowResult(position, stones, pitStones);
    }

    public void incrementLargePit()
    {
        this.largePit++;
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