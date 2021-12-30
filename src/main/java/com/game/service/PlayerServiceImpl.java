package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    // Хранилище клиентов
    private static final Map<Long, Player> PLAYER_REPOSITORY_MAP = new HashMap<>();

    // Переменная для генерации ID клиента
    private static final AtomicInteger PLAYER_ID_HOLDER = new AtomicInteger();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PLAYER_REPOSITORY_MAP.put(1L, new Player(1L, "Ниус", "Приходящий Без Шума", Race.HOBBIT, Profession.ROGUE, sdf.parse("2010-10-12").getTime(), false, 58347, 33, 1153));
            PLAYER_REPOSITORY_MAP.put(2L, new Player(2L, "Никрашш", "НайтВульф", Race.ORC, Profession.WARLOCK, sdf.parse("2010-02-14").getTime(), false, 174403, 58, 2597));
            PLAYER_REPOSITORY_MAP.put(3L, new Player(3L, "Эззэссэль", "шипящая", Race.DWARF, Profession.CLERIC, sdf.parse("2006-02-28").getTime(), true, 804, 3, 196));
            PLAYER_REPOSITORY_MAP.put(4L, new Player(4L, "Бэлан", "Тсе Раа", Race.DWARF, Profession.ROGUE, sdf.parse("2008-02-25").getTime(), true, 44553, 29, 1947));
            PLAYER_REPOSITORY_MAP.put(5L, new Player(5L, "Элеонора", "Бабушка", Race.HUMAN, Profession.SORCERER, sdf.parse("2006-01-07").getTime(), true, 63986, 35, 2614));
            PLAYER_REPOSITORY_MAP.put(6L, new Player(6L, "Эман", "Ухастый Летун", Race.ELF, Profession.SORCERER, sdf.parse("2004-06-21").getTime(), false, 163743, 56, 1557));
            PLAYER_REPOSITORY_MAP.put(7L, new Player(7L, "Талан", "Рожденный в Бронксе", Race.GIANT, Profession.ROGUE, sdf.parse("2005-05-15").getTime(), false, 68950, 36, 1350));
            PLAYER_REPOSITORY_MAP.put(8L, new Player(8L, "Арилан", "Благотворитель", Race.ELF, Profession.SORCERER, sdf.parse("2006-08-10").getTime(), false, 61023, 34, 1977));
            PLAYER_REPOSITORY_MAP.put(9L, new Player(9L, "Деракт", "Эльфёнок Красное Ухо", Race.ELF, Profession.ROGUE, sdf.parse("2010-06-22").getTime(), false, 156630, 55, 2970));
            PLAYER_REPOSITORY_MAP.put(10L, new Player(10L, "Архилл", "Смертоносный", Race.GIANT, Profession.PALADIN, sdf.parse("2005-01-12").getTime(), false, 76010, 38, 1990));
            PLAYER_REPOSITORY_MAP.put(11L, new Player(11L, "Эндарион", "Маленький эльфенок", Race.ELF, Profession.DRUID, sdf.parse("2001-04-24").getTime(), false, 103734, 45, 4366));
            PLAYER_REPOSITORY_MAP.put(12L, new Player(12L, "Фаэрвин", "Темный Идеолог", Race.HUMAN, Profession.NAZGUL, sdf.parse("2010-09-06").getTime(), false, 7903, 12, 1197));
            PLAYER_REPOSITORY_MAP.put(13L, new Player(13L, "Харидин", "Бедуин", Race.TROLL, Profession.WARRIOR, sdf.parse("2009-09-08").getTime(), false, 114088, 47, 3512));
            PLAYER_REPOSITORY_MAP.put(14L, new Player(14L, "Джур", "БоРец с жАжДой", Race.ORC, Profession.DRUID, sdf.parse("2009-07-14").getTime(), false, 29573, 23, 427));
            PLAYER_REPOSITORY_MAP.put(15L, new Player(15L, "Грон", "Воин обреченный на бой", Race.GIANT, Profession.PALADIN, sdf.parse("2005-04-28").getTime(), false, 174414, 58, 2586));
            PLAYER_REPOSITORY_MAP.put(16L, new Player(16L, "Морвиел", "Копье Калимы", Race.ELF, Profession.CLERIC, sdf.parse("2010-03-15").getTime(), false, 49872, 31, 2928));
            PLAYER_REPOSITORY_MAP.put(17L, new Player(17L, "Ннуфис", "ДиамантоваЯ", Race.HUMAN, Profession.ROGUE, sdf.parse("2001-09-03").getTime(), false, 162477, 56, 2823));
            PLAYER_REPOSITORY_MAP.put(18L, new Player(18L, "Ырх", "Троль гнет ель", Race.TROLL, Profession.WARRIOR, sdf.parse("2001-04-08").getTime(), true, 136860, 51, 940));
            PLAYER_REPOSITORY_MAP.put(19L, new Player(19L, "Блэйк", "Серый Воин", Race.HUMAN, Profession.ROGUE, sdf.parse("2005-05-23").getTime(), false, 151039, 54, 2961));
            PLAYER_REPOSITORY_MAP.put(20L, new Player(20L, "Нэсс", "Бусинка", Race.TROLL, Profession.WARRIOR, sdf.parse("2008-02-09").getTime(), true, 64945, 35, 1655));
            PLAYER_REPOSITORY_MAP.put(21L, new Player(21L, "Ферин", "Воитель", Race.TROLL, Profession.WARRIOR, sdf.parse("2003-07-08").getTime(), false, 120006, 48, 2494));
            PLAYER_REPOSITORY_MAP.put(22L, new Player(22L, "Солках", "Ученик Магии", Race.ELF, Profession.SORCERER, sdf.parse("2001-11-07").getTime(), false, 152996, 54, 1004));
            PLAYER_REPOSITORY_MAP.put(23L, new Player(23L, "Сцинк", "Титан Войны", Race.GIANT, Profession.WARRIOR, sdf.parse("2008-01-04").getTime(), true, 86585, 41, 3715));
            PLAYER_REPOSITORY_MAP.put(24L, new Player(24L, "Айша", "Искусительница", Race.HUMAN, Profession.CLERIC, sdf.parse("2010-01-25").getTime(), false, 106181, 45, 1919));
            PLAYER_REPOSITORY_MAP.put(25L, new Player(25L, "Тант", "Черт закAтай вату", Race.DWARF, Profession.PALADIN, sdf.parse("2010-10-03").getTime(), false, 33889, 25, 1211));
            PLAYER_REPOSITORY_MAP.put(26L, new Player(26L, "Трениган", "Великий Волшебник", Race.ELF, Profession.SORCERER, sdf.parse("2004-05-17").getTime(), false, 91676, 42, 2924));
            PLAYER_REPOSITORY_MAP.put(27L, new Player(27L, "Вуджер", "Печальный", Race.TROLL, Profession.NAZGUL, sdf.parse("2010-10-04").getTime(), false, 93079, 42, 1521));
            PLAYER_REPOSITORY_MAP.put(28L, new Player(28L, "Камираж", "БAнкир", Race.DWARF, Profession.CLERIC, sdf.parse("2005-08-05").getTime(), true, 79884, 39, 2116));
            PLAYER_REPOSITORY_MAP.put(29L, new Player(29L, "Ларкин", "СвЯтой", Race.HOBBIT, Profession.CLERIC, sdf.parse("2003-07-10").getTime(), false, 111868, 46, 932));
            PLAYER_REPOSITORY_MAP.put(30L, new Player(30L, "Зандир", "Темновидец", Race.ELF, Profession.WARLOCK, sdf.parse("2003-05-24").getTime(), false, 29654, 23, 346));
            PLAYER_REPOSITORY_MAP.put(31L, new Player(31L, "Балгор", "пещерный Урук", Race.ORC, Profession.NAZGUL, sdf.parse("2005-02-23").getTime(), false, 18869, 18, 131));
            PLAYER_REPOSITORY_MAP.put(32L, new Player(32L, "Регарн", "Любитель ОЛивье", Race.GIANT, Profession.WARRIOR, sdf.parse("2006-12-23").getTime(), false, 144878, 53, 3622));
            PLAYER_REPOSITORY_MAP.put(33L, new Player(33L, "Анжелли", "Молодой Боец", Race.DWARF, Profession.WARRIOR, sdf.parse("2010-04-08").getTime(), false, 59281, 33, 219));
            PLAYER_REPOSITORY_MAP.put(34L, new Player(34L, "Джерис", "Имперский Воин", Race.ORC, Profession.WARRIOR, sdf.parse("2001-05-12").getTime(), false, 173807, 58, 3193));
            PLAYER_REPOSITORY_MAP.put(35L, new Player(35L, "Жэкс", "Ярочкино Солнышко", Race.GIANT, Profession.WARRIOR, sdf.parse("2008-01-04").getTime(), false, 848, 3, 152));
            PLAYER_REPOSITORY_MAP.put(36L, new Player(36L, "Филуэль", "Химик и Карпускулярник.", Race.ELF, Profession.WARLOCK, sdf.parse("2008-08-03").getTime(), false, 48496, 30, 1104));
            PLAYER_REPOSITORY_MAP.put(37L, new Player(37L, "Яра", "Прельстивая", Race.HUMAN, Profession.CLERIC, sdf.parse("2004-06-12").getTime(), false, 138306, 52, 4794));
            PLAYER_REPOSITORY_MAP.put(38L, new Player(38L, "Иллинас", "Иероглиф", Race.HOBBIT, Profession.WARRIOR, sdf.parse("2007-06-03").getTime(), false, 115546, 47, 2054));
            PLAYER_REPOSITORY_MAP.put(39L, new Player(39L, "Ардонг", "Вспышк A", Race.HUMAN, Profession.WARLOCK, sdf.parse("2009-09-16").getTime(), false, 24984, 21, 316));
            PLAYER_REPOSITORY_MAP.put(40L, new Player(40L, "Аттирис", "и.о.Карвандоса", Race.ELF, Profession.SORCERER, sdf.parse("2010-04-15").getTime(), true, 60520, 34, 2480));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    Comparator<Player> getComparator(PlayerOrder order) {
        switch (order) {
            case NAME:
                return Comparator.comparing(p -> p.getName());
            case EXPERIENCE:
                return Comparator.comparing(p -> p.getExperience());
            case BIRTHDAY:
                return Comparator.comparing(p -> p.getBirthday());
            case LEVEL:
                return Comparator.comparing(p -> p.getLevel());
            default:
                return Comparator.comparing(p -> p.getId());
        }
    }

    @Override
    public List<Player> readAll(String name,
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
                                PlayerOrder order) {

        return new ArrayList<>(PLAYER_REPOSITORY_MAP.values().stream()
                .filter(s -> s.getName().toLowerCase().indexOf(name.toLowerCase()) > -1 || name.isEmpty())
                .filter(s -> s.getTitle().toLowerCase().indexOf(title.toLowerCase()) > -1 || title.isEmpty())
                .filter(s -> s.getRace().equals(race) || race == null)
                .filter(s -> s.getProfession().equals(profession) || profession == null)
                .filter(s -> s.getBirthday() + 18000000 >= after || after == 0)
                .filter(s -> s.getBirthday() <= before || before == 9223372036854775807L)
                .filter(s -> s.isBanned() == banned || banned == null)
                .filter(s -> s.getExperience() >= minExperience || minExperience == -2147483648)
                .filter(s -> s.getExperience() <= maxExperience || maxExperience == 2147483647)
                .filter(s -> s.getLevel() >= minLevel || minLevel == -2147483648)
                .filter(s -> s.getLevel() <= maxLevel || maxLevel == 2147483647)
                .sorted(getComparator(order))
                .collect(Collectors.toList()));
    }

    @Override
    public Player create(PlayerPojo p) {
        try {
            if (p != null
                    && p.getName() != null
                    && p.getName().length() > 0
                    && p.getName().length() <= 12
                    && p.getTitle() != null
                    && p.getTitle().length() > 0
                    && p.getTitle().length() <= 30
                    && p.getRace() != null
                    && p.getProfession() != null
                    && p.getExperience() >= 0
                    && p.getExperience() <= 10000000
                    && p.getBirthday() >= 0) {
                try {
                    Date dateBeg = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
                    Date dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse("31.12.3000");
                    if (p.getBirthday() >= dateBeg.getTime() && p.getBirthday() <= dateEnd.getTime()) {
                        Long playerId = Long.valueOf(PLAYER_REPOSITORY_MAP.size() + 1);
                        Player player = new Player(playerId,
                                p.getName(),
                                p.getTitle(),
                                p.getRace(),
                                p.getProfession(),
                                p.getBirthday(),
                                p.isBanned(),
                                p.getExperience());
                        PLAYER_REPOSITORY_MAP.put(playerId, player);
                        return player;
                    }
                } catch (ParseException e) {}
            }
        } catch (NullPointerException e) {}
        return null;
    }

    @Override
    public Player read(Long id) {
        if (PLAYER_REPOSITORY_MAP.containsKey(id)) {
            return PLAYER_REPOSITORY_MAP.get(id);
        }
        return null;
    }

    @Override
    public Player update(PlayerPojo p, Long id) {
        try {
            if (p != null
                    && (p.getName() == null || (p.getName().length() > 0 && p.getName().length() <= 12))
                    && (p.getTitle() == null || (p.getTitle().length() > 0 && p.getTitle().length() <= 30))
                    && (p.getExperience() == null || (p.getExperience() <= 10000000 && p.getExperience() >= 0))) {
                try {
                    Date dateBeg = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
                    Date dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse("31.12.3000");
                    if (p.getBirthday() == null || (p.getBirthday() >= dateBeg.getTime() && p.getBirthday() <= dateEnd.getTime())) {
                        Player player = PLAYER_REPOSITORY_MAP.get(id);
                        if (p.getName() != null) {
                            player.setName(p.getName());
                        }
                        if (p.getTitle() != null) {
                            player.setTitle(p.getTitle());
                        }
                        if (p.getRace() != null) {
                            player.setRace(p.getRace());
                        }
                        if (p.getProfession() != null) {
                            player.setProfession(p.getProfession());
                        }
                        if (p.getExperience() != null) {
                            player.setExperience(p.getExperience());
                        }
                        if (p.getBirthday() != null) {
                            player.setBirthday(p.getBirthday());
                        }
                        if (p.isBanned() != null) {
                            player.setBanned(p.isBanned());
                        }
                        return player;
                    }
                } catch (ParseException e) {}
            }
        } catch (NullPointerException e) {}
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return PLAYER_REPOSITORY_MAP.remove(id) != null;
    }
}
