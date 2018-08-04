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

    public Board.Player makeMove(Board board, int position)
    {
        Board.Player currentPlayer = board.sowFrom(position);
        boardDao.save(board);
        return currentPlayer;
    }
}
