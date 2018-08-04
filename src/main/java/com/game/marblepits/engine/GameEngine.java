package com.game.marblepits.engine;

import com.game.marblepits.repositories.BoardDao;
import com.game.marblepits.entities.Board;
import org.springframework.stereotype.Service;

@Service
public class GameEngine
{
    private BoardDao boardDao;

    public GameEngine(BoardDao boardDao)
    {
        this.boardDao = boardDao;
    }

    public Board newGame()
    {
        return boardDao.save(new Board());
    }


}
