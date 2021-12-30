package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerPojo;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/rest/players")
    public ResponseEntity<List<Player>> readAll(@RequestParam(value = "name", defaultValue = "") String name,
                                                @RequestParam(value = "title", defaultValue = "") String title,
                                                @RequestParam(value = "race", defaultValue = "") Race race,
                                                @RequestParam(value = "profession", defaultValue = "") Profession profession,
                                                @RequestParam(value = "after", defaultValue = "0") Long after,
                                                @RequestParam(value = "before", defaultValue = "9223372036854775807") Long before,
                                                @RequestParam(value = "banned", defaultValue = "") Boolean banned,
                                                @RequestParam(value = "minExperience", defaultValue = "-2147483648") Integer minExperience,
                                                @RequestParam(value = "maxExperience", defaultValue = "2147483647") Integer maxExperience,
                                                @RequestParam(value = "minLevel", defaultValue = "-2147483648") Integer minLevel,
                                                @RequestParam(value = "maxLevel", defaultValue = "2147483647") Integer maxLevel,
                                                @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                                                @RequestParam(value = "order", defaultValue = "ID") PlayerOrder order
    ) {
        final List<Player> players = playerService.readAll(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order);
        final List<Player> playersPage = new ArrayList<Player>();
        int pos = 0;
        for (int i = 0; i < pageSize; i++) {
            pos = pageNumber * pageSize + i;
            if (pos < players.size()) {
                playersPage.add(players.get(pos));
            }
        }
        return new ResponseEntity<>(playersPage, HttpStatus.OK);
    }

    @GetMapping(value = "/rest/players/count")
    public ResponseEntity<Integer> count(@RequestParam(value = "name", defaultValue = "") String name,
                                         @RequestParam(value = "title", defaultValue = "") String title,
                                         @RequestParam(value = "race", defaultValue = "") Race race,
                                         @RequestParam(value = "profession", defaultValue = "") Profession profession,
                                         @RequestParam(value = "after", defaultValue = "0") Long after,
                                         @RequestParam(value = "before", defaultValue = "9223372036854775807") Long before,
                                         @RequestParam(value = "banned", defaultValue = "") Boolean banned,
                                         @RequestParam(value = "minExperience", defaultValue = "-2147483648") Integer minExperience,
                                         @RequestParam(value = "maxExperience", defaultValue = "2147483647") Integer maxExperience,
                                         @RequestParam(value = "minLevel", defaultValue = "-2147483648") Integer minLevel,
                                         @RequestParam(value = "maxLevel", defaultValue = "2147483647") Integer maxLevel,
                                         @RequestParam(value = "order", defaultValue = "ID") PlayerOrder order) {
        final List<Player> players = playerService.readAll(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order);
        return new ResponseEntity<>(players.size(), HttpStatus.OK);
    }

    @PostMapping(value = "/rest/players")
    public ResponseEntity<?> create(@RequestBody PlayerPojo playerP) {
        Player player = playerService.create(playerP);
        if (player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/rest/players/{id}")
    public ResponseEntity<Player> read(@PathVariable(name = "id") String s) {
        try {
            Long id = Long.valueOf(s);
            if (id == 0) {
                throw new NumberFormatException();
            }
            final Player player = playerService.read(id);
            return player != null
                    ? new ResponseEntity<>(player, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/rest/players/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") String s, @RequestBody PlayerPojo playerP) {
        try {
            Long id = Long.valueOf(s);
            if (id <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Player player = playerService.read(id);
            if (player == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            player = playerService.update(playerP, id);
            return player != null
                    ? new ResponseEntity<>(player, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/rest/players/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String s) {
        try {
            Long id = Long.valueOf(s);
            if (id <= 0) {
                throw new NumberFormatException();
            }
            final boolean deleted = playerService.delete(id);
            return deleted
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
