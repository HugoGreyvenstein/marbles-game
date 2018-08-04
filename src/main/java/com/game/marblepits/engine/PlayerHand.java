package com.game.marblepits.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayerHand
{
    int position;
    int stones;
    int initialStones;

    public void decrementStones()
    {
        stones--;
    }

    public boolean hasStones()
    {
        return this.stones > 0;
    }
}
