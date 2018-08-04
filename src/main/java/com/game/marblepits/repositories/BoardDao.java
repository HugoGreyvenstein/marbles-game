package com.game.marblepits.repositories;

import com.game.marblepits.entities.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDao extends CrudRepository<Board, Long>
{}
