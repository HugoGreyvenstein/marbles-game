package com.game.marblepits.controllers;

import com.game.marblepits.engine.GameEngine;
import com.game.marblepits.entities.Board;
import com.game.marblepits.repositories.BoardDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
@AllArgsConstructor
public class GameController
{
    private BoardDao boardDao;

    private GameEngine gameEngine;

    @GetMapping("/{id}")
    public String getGame(@PathVariable String id, Model model) throws Exception
    {
        Board board = boardDao.findById(Long.parseLong(id)).orElseThrow(() -> new Exception("Not found"));
        model.addAttribute("board", board);
        return "game";
    }

    @PutMapping(value = "/{id}")
    public String makeMove(@PathVariable String id, @RequestParam String position, Model model) throws Exception
    {
        Board board = boardDao.findById(Long.parseLong(id)).orElseThrow(() -> new Exception("Not found"));
        model.addAttribute("board", gameEngine.makeMove(board, Integer.parseInt(position)));

        return "game";
    }

    @DeleteMapping("/{id}")
    public String deleteGame(@PathVariable String id, Model model) throws Exception
    {
        Board board = boardDao.findById(Long.parseLong(id)).orElseThrow(() -> new Exception("Not found"));
        boardDao.delete(board);
        model.addAttribute("board", board);

        return "index";
    }

    @GetMapping("")
    public String findAllGames(Model model)
    {
        Iterable<Board> allBoards = boardDao.findAll();
        model.addAttribute("allBoards", allBoards);
        return "games";
    }

    @PostMapping("")
    public String startGame(Model model)
    {
        model.addAttribute("board", gameEngine.newGame());
        return "game";
    }
}