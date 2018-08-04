package com.game.marblepits.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController
{
    @GetMapping("")
    public String index(Model model)
    {
        return "index";
    }

    @PostMapping("/start-game")
    public String startGame(Model model)
    {
        return "game";
    }
}
