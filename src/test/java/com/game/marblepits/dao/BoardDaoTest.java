package com.game.marblepits.dao;

import com.game.marblepits.entities.Board;
import com.game.marblepits.entities.PlayerPit;
import com.game.marblepits.repositories.BoardDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardDaoTest
{
    @Autowired
    private BoardDao boardDao;

    private Board board;

    @Before
    public void setUp()
    {
        Board board = new Board();
        board.setCurrentPlayer(Board.Player.PLAYER_1);

        Map<Board.Player, PlayerPit> pits = new HashMap<>();
        pits.put(Board.Player.PLAYER_1, new PlayerPit());
        pits.put(Board.Player.PLAYER_2, new PlayerPit());
        board.setPits(pits);
        board.setId(1L);
        this.board = board;
    }

    @Test
    public void testBoardPersist() throws Exception
    {
        Board board = boardDao.save(this.board);

        Board byId = boardDao.findById(board.getId()).orElseThrow(() -> new Exception("failed to retrieve board"));
        Assert.assertEquals(this.board, byId);
    }


}
