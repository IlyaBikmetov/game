package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {
    List<Player> readAll(String name,
                         String title,
                         Race race,
                         Profession profession,
                         Long after,
                         Long before,
                         Boolean banned,
                         Integer minExperience,
                         Integer maxExperience,
                         Integer minLevel,
                         Integer maxLevel,
                         PlayerOrder order);

    Player create(PlayerPojo playerPojo);

    Player read(Long id);

    Player update(PlayerPojo player, Long id);

    boolean delete(Long id);
}
