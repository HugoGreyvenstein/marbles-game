package com.game.marblepits.controllers;

import com.game.marblepits.engine.GameEngine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class IndexController
{
    private GameEngine gameEngine;

    @GetMapping("/")
    public String index(Model model)
    {
        return "index";
    }
}
