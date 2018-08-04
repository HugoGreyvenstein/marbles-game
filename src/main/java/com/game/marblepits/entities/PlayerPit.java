package com.game.marblepits.entities;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
class PlayerPit
{
    @Id
    @GeneratedValue
    private Long id;

    private final int[] pits = new int[] {6, 6, 6, 6, 6, 6};
    private int largePit = 0;
}