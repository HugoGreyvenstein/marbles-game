package com.game.marblepits.game;

import com.game.marblepits.entities.Board;
import com.game.marblepits.entities.PlayerPit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.game.marblepits.entities.Board.Player.PLAYER_1;
import static com.game.marblepits.entities.Board.Player.PLAYER_2;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Slf4j
public class BoardTests
{
    private Board board;

    @Before
    public void setUp()
    {
        board = new Board();
        board.initialize();
    }

    @Test
    public void testShouldEndGame()
    {
        Board board = new Board();
        board.initialize();

        assertFalse(board.shouldEndGame());

        PlayerPit pit = mock(PlayerPit.class);
        when(pit.pitsSum()).thenReturn(0);
        board.getPits().put(PLAYER_2, pit);

        assertTrue(board.shouldEndGame());
    }

    @Test
    public void testSowFrom()
    {
        board.sowFrom(0);
        log.info("Board={}", board);
        assertEquals(PLAYER_1, board.getCurrentPlayer());
        assertEquals(new PlayerPit(), board.getPits().get(PLAYER_2));
        assertEquals(new PlayerPit(null, new int[] {0, 7, 7, 7, 7, 7}, 1), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {6, 6, 6, 6, 6, 6}, 0), board.getPits().get(PLAYER_2));

        board.sowFrom(1);
        assertEquals(PLAYER_2, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {0, 0, 8, 8, 8, 8}, 2), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {7, 7, 6, 6, 6, 6}, 0), board.getPits().get(PLAYER_2));

        board.sowFrom(0);
        assertEquals(PLAYER_1, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {0, 0, 8, 8, 8, 8}, 2), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {0, 8, 7, 7, 7, 0}, 9), board.getPits().get(PLAYER_2));

        Map<Board.Player, PlayerPit> pits = new HashMap<>();
        pits.put(PLAYER_1, new PlayerPit(null, new int[] {3, 1, 1, 1, 0, 0}, 0));
        pits.put(PLAYER_2, new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0));
        board.setPits(pits);
        board.setCurrentPlayer(PLAYER_1);
        board.sowFrom(0);
        assertEquals(PLAYER_2, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {0, 2, 2, 2, 0, 0}, 0), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0), board.getPits().get(PLAYER_2));

        pits = new HashMap<>();
        pits.put(PLAYER_1, new PlayerPit(null, new int[] {14, 0, 0, 0, 0, 0}, 0));
        pits.put(PLAYER_2, new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0));
        board.setPits(pits);
        board.setCurrentPlayer(PLAYER_1);
        board.sowFrom(0);
        assertEquals(PLAYER_2, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {1, 2, 1, 1, 1, 1}, 1), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {2, 2, 2, 2, 2, 2}, 0), board.getPits().get(PLAYER_2));

        pits = new HashMap<>();
        pits.put(PLAYER_1, new PlayerPit(null, new int[] {13, 0, 0, 0, 0, 0}, 0));
        pits.put(PLAYER_2, new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0));
        board.setPits(pits);
        board.setCurrentPlayer(PLAYER_1);
        board.sowFrom(0);
        assertEquals(PLAYER_2, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {0, 1, 1, 1, 1, 1}, 4), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {2, 2, 2, 2, 2, 0}, 0), board.getPits().get(PLAYER_2));

        pits = new HashMap<>();
        pits.put(PLAYER_1, new PlayerPit(null, new int[] {13, 0, 1, 0, 0, 0}, 0));
        pits.put(PLAYER_2, new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0));
        board.setPits(pits);
        board.setCurrentPlayer(PLAYER_1);
        board.sowFrom(5);
        assertEquals(PLAYER_2, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {13, 0, 1, 0, 0, 0}, 0), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0), board.getPits().get(PLAYER_2));

        pits = new HashMap<>();
        pits.put(PLAYER_1, new PlayerPit(null, new int[] {13, 0, 1, 0, 0, 0}, 0));
        pits.put(PLAYER_2, new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 0));
        board.setPits(pits);
        board.setCurrentPlayer(PLAYER_1);
        board.sowFrom(2);
        assertEquals(PLAYER_2, board.getCurrentPlayer());
        assertEquals(new PlayerPit(null, new int[] {13, 0, 0, 0, 0, 0}, 2), board.getPits().get(PLAYER_1));
        assertEquals(new PlayerPit(null, new int[] {1, 1, 0, 1, 1, 1}, 0), board.getPits().get(PLAYER_2));
    }

    @Test
    public void testWinningCondition()
    {
        Map<Board.Player, PlayerPit> pits = new HashMap<>();
        pits.put(PLAYER_1, new PlayerPit(null, new int[] {0, 0, 0, 0, 0, 0}, 1));
        pits.put(PLAYER_2, new PlayerPit(null, new int[] {1, 1, 1, 1, 1, 1}, 10));
        board.setPits(pits);
        assertEquals(1, board.getPlayerScore(PLAYER_1));
        assertEquals(16, board.getPlayerScore(PLAYER_2));
        assertTrue(board.shouldEndGame());
    }
}
