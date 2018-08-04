package com.game.marblepits;

import com.game.marblepits.engine.PlayerHand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PlayerHandTests
{
    @Test
    public void testDecrementStones()
    {
        PlayerHand playerHand = new PlayerHand(0, 1, 1);
        assertEquals(1, playerHand.getStones());
        playerHand.decrementStones();
        assertEquals(0, playerHand.getStones());
    }

    @Test
    public void testHasStones()
    {
        PlayerHand playerHand = new PlayerHand(0, 1, 1);
        assertTrue(playerHand.hasStones());
        playerHand.decrementStones();
        assertFalse(playerHand.hasStones());
    }
}
