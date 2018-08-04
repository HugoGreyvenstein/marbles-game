package com.game.marblepits;

import com.game.marblepits.engine.PlayerHand;
import com.game.marblepits.entities.PlayerPit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Slf4j
public class PlayerPitTests
{
    @Test
    public void initialPlayerPitTest()
    {
        PlayerPit playerPit = createPlayerPit();
        assertEquals(0, playerPit.getLargePit());
        assertTrue(Arrays.stream(playerPit.getPits()).allMatch(i -> i == 6));
    }

    @Test
    public void startSowingFromTest()
    {
        PlayerPit playerPit = createPlayerPit();
        PlayerHand hand = playerPit.startSowingFrom(0);
        assertArrayEquals(new int[] {0, 7, 7, 7, 7, 7}, playerPit.getPits());
        assertEquals(new PlayerHand(6, 1, 6), hand);

        playerPit = createPlayerPit();
        hand = playerPit.startSowingFrom(1);
        assertArrayEquals(new int[] {6, 0, 7, 7, 7, 7}, playerPit.getPits());
        assertEquals(new PlayerHand(6, 2, 6), hand);

        hand = playerPit.startSowingFrom(5);
        assertArrayEquals(new int[] {6, 0, 7, 7, 7, 0}, playerPit.getPits());
        assertEquals(new PlayerHand(6, 7, -1), hand);

        hand = playerPit.startSowingFrom(1);
        assertArrayEquals(new int[] {6, 0, 7, 7, 7, 0}, playerPit.getPits());
        assertEquals(new PlayerHand(2, 0, -1), hand);

        playerPit = createPlayerPit();
        int[] pits = playerPit.getPits();
        pits[0] = 3;
        hand = playerPit.startSowingFrom(0);
        assertArrayEquals(new int[] {0, 7, 7, 7, 6, 6}, playerPit.getPits());
        assertEquals(new PlayerHand(4, 0, 6), hand);
    }

    @Test
    public void continueSowingFromTest()
    {
        PlayerPit playerPit = createPlayerPit();
        PlayerHand hand = playerPit.continueSowingFrom(0, 3);
        assertArrayEquals(new int[] {7, 7, 7, 6, 6, 6}, playerPit.getPits());
        assertEquals(new PlayerHand(3, 0, 6), hand);

        playerPit = createPlayerPit();
        hand = playerPit.continueSowingFrom(1, 8);
        assertArrayEquals(new int[] {6, 7, 7, 7, 7, 7}, playerPit.getPits());
        assertEquals(new PlayerHand(6, 3, 6), hand);

        playerPit = createPlayerPit();
        hand = playerPit.continueSowingFrom(5, 1);
        assertArrayEquals(new int[] {6, 6, 6, 6, 6, 7}, playerPit.getPits());
        assertEquals(new PlayerHand(6, 0, 6), hand);
    }

    @Test
    public void testTakeStonesAt()
    {
        PlayerPit playerPit = createPlayerPit();
        assertEquals(6, playerPit.takeStonesAt(0));
        assertArrayEquals(new int[] {0, 6, 6, 6, 6, 6}, playerPit.getPits());
    }

    @Test
    public void testIncrementLargePit()
    {
        PlayerPit playerPit = createPlayerPit();
        assertEquals(0, playerPit.getLargePit());

        playerPit.incrementLargePit();
        assertEquals(1, playerPit.getLargePit());
    }

    @Test
    public void testAddToLargePit()
    {
        PlayerPit playerPit = createPlayerPit();
        assertEquals(0, playerPit.getLargePit());
        playerPit.addToLargePit(100);
        assertEquals(100, playerPit.getLargePit());
    }

    @Test
    public void testPitsSum()
    {
        PlayerPit playerPit = createPlayerPit();
        assertEquals(36, playerPit.pitsSum());
    }

    @Test
    public void testScore()
    {
        PlayerPit playerPit = createPlayerPit();
        assertEquals(36, playerPit.score());
        playerPit.setLargePit(4);
        assertEquals(40, playerPit.score());
    }

    private PlayerPit createPlayerPit()
    {
        return new PlayerPit();
    }

}
