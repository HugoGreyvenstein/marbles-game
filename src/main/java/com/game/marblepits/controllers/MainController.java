package com.game.marblepits.controllers;

import com.game.marblepits.engine.GameEngine;
import com.game.marblepits.entities.Board;
import com.game.marblepits.repositories.BoardDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
public class MainController
{
    private GameEngine gameEngine;

    private BoardDao boardDao;

    public MainController(GameEngine gameEngine, BoardDao boardDao)
    {
        this.gameEngine = gameEngine;
        this.boardDao = boardDao;
    }

    @GetMapping("")
    public String index(Model model)
    {
        return "index";
    }

    @PostMapping("/start-game")
    public String startGame(Model model)
    {
        model.addAttribute("board", gameEngine.newGame());
        return "game";
    }

    @GetMapping("/game/{id}")
    public String findGame(@RequestPart String id, Model model) throws Exception
    {
        Board board = boardDao.findById(Long.parseLong(id)).orElseThrow(() -> new Exception("not found"));
        model.addAttribute("board", board);

        return "game";
    }
}
