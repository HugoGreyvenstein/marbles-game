package com.game.marblepits.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SowResult
{
    int position;
    int stones;
    int pitStones;

    public void decrementStones()
    {
        stones--;
    }

    public boolean hasStones()
    {
        return this.stones > 0;
    }
}
