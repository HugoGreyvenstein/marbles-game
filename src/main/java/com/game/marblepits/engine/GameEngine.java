package com.game.marblepits.engine;

import com.game.marblepits.entities.Board;
import com.game.marblepits.repositories.BoardDao;
import org.springframework.stereotype.Component;

@Component
public class GameEngine
{
    private BoardDao boardDao;

    public GameEngine(BoardDao boardDao)
    {
        this.boardDao = boardDao;
    }

    public Board newGame()
    {
        Board board = new Board();
        board.initialize();
        return boardDao.save(board);
    }

    public Board makeMove(Board board, int position)
    {
        board.sowFrom(position);
        return boardDao.save(board);
    }

    public boolean isFinished(Board board)
    {
        return board.shouldEndGame();
    }
}
