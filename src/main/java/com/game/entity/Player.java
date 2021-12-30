package com.game.entity;

import com.game.controller.PlayerOrder;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.function.Function;

public class Player {
    private Long id; //ID игрока
    private String name; //Имя персонажа (до 12 знаков включительно)
    private String title; //Титул персонажа (до 30 знаков включительно)
    private Race race; //Раса персонажа
    private Profession profession; //Профессия персонажа
    private Integer experience; //Опыт персонажа. Диапазон значений 0..10,000,000
    private Integer level; //Уровень персонажа
    private Integer untilNextLevel; //Остаток опыта до следующего уровня
    private Long birthday; //Дата регистрации. Диапазон значений года 2000..3000 включительно
    private Boolean banned; //Забанен / не забанен

    public Player(Long id, String name, String title, Race race, Profession profession, long birthday, Boolean banned, Integer experience, Integer level, Integer untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Player(Long id, String name, String title, Race race, Profession profession, long birthday, Boolean banned, Integer experience) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = Math.toIntExact(Math.round(Math.sqrt(2500 + 200.0 * experience) - 50) / 100);
        this.untilNextLevel = 50 * (this.level + 1) * (this.level + 2) - experience;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
        this.level = Math.toIntExact(Math.round(Math.sqrt(2500 + 200.0 * experience) - 50) / 100);
        this.untilNextLevel = 50 * (this.level + 1) * (this.level + 2) - experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public static int compareByNameField(PlayerOrder order) {
        return 1;
    }
}
