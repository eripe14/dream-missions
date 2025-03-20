package cc.dreamcode.missions.mission;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.utilities.MenuUtil;
import cc.dreamcode.missions.mission.filter.FilterMode;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.*;

public class MissionConfig extends OkaeriConfig {

    @Comment
    @Comment("Czy plugin ma wysylac wiadomosci do wszystkich graczy online o tym, ze misja serwerowa zostala zakonczona?")
    public boolean broadcastMissionFinish = true;

    @Comment
    @Comment("Pod jakim slotem w menu misji ma sie znajdowac przycisk od wyjscia z menu?")
    @Comment("Jesli nie potrzebujesz takiego przycisku, wowczas wpisz w ponizsze pole -1.")
    public int missionMenuCloseSlot = MenuUtil.countSlot(6, 5);

    @Comment
    @Comment("Jak ma wygladac gui z misjami?")
    public BukkitMenuBuilder missionMenuBuilder = new BukkitMenuBuilder("&a&lMisje serwerowe", 6, new MapBuilder<Integer, ItemStack>()
            .put(MenuUtil.countSlot(6, 5), ItemBuilder.of(XMaterial.BARRIER.parseItem())
                    .setName("&cWyjdź z menu.")
                    .toItemStack())
            .build());

    @Comment("W konfiguracji misji w ikonie jest uzywany placeholder {status}")
    @Comment("Mozesz go tutaj zdeklarowac jak ma wygladac")
    public String missionStatusInProgress = "&eW trakcie";
    public String missionStatusFinished = "&aZakończone";

    @Comment
    @Comment("Lista przykladowych misji")
    @Comment("Dostepne typy misji: (MINED_BLOCKS, EATEN_ITEMS, PLACED_BLOCKS, RUNNING_DISTANCE, WRITTEN_MESSAGE, CAUGHT_ITEMS, KILLED_ENTITIES)")
    @Comment("Dostepne filtry materialow: (ALL, SPECIFIC, EXCLUDE)")
    @Comment("Jako nazwe przedmiotu w gui, mozecie uzyc {name}, wtedy nazwa przedmiotu bedzie taka sama jak misji")
    @Comment("Dostepne zmienne:")
    @Comment("{player} - kazdy gracz online dostanie nagrode")
    @Comment("{status} - status misji (W trakcie, Zakończone)")
    @Comment("{percentage} - procent wykonania misji")
    @Comment("{time} - czas do zresetowania misji")
    @Comment("{complete} - aktualny progress misji w formie liczbowej")
    @Comment("{remaining} - pozostaly progress misji w formie liczbowej")
    public Map<Integer, Mission> missions = new MapBuilder<Integer, Mission>()
            .put(1, new Mission(
                            1,
                            "Wykopcie 10 bloków",
                            MissionType.MINED_BLOCKS,
                            10,
                    "give {player} minecraft:diamond 1",
                            Duration.ofMinutes(1),
                            FilterMode.ALL,
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.DIAMOND_PICKAXE.parseMaterial())
                                            .setName("&7{name}")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Wykopcie 10 bloków",
                                                    "&8» &7Progres: &e{percentage}%",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    10
                            )
                    )
            )
            .put(2, new Mission(
                            2,
                            "Postawcie 10 STONE'A",
                            MissionType.PLACED_BLOCKS,
                            10,
                    "REWARD",
                            Duration.ofDays(1),
                            FilterMode.SPECIFIC,
                            ListBuilder.of(XMaterial.STONE),
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.STONE.parseMaterial())
                                            .setName("&7Postawie 10 stone'a")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Postawie 10 stone'a",
                                                    "&8» &7Postawiono stone'a: &e{complete}",
                                                    "&8» &7Pozostalo do postawienia: &e{remaining}",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    12
                            )
                    )
            )
            .put(3, new Mission(
                            3,
                            "Zabijcie 10 mobów",
                            MissionType.KILLED_ENTITIES,
                            10,
                    "REWARD",
                            Duration.ofDays(1),
                            FilterMode.ALL,
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.ZOMBIE_HEAD.parseMaterial())
                                            .setName("&7Zabijcie 10 mobów")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Zabijcie 10 mobów",
                                                    "&8» &7Progres: &e{percentage}%",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    14
                            )
                    )
            )
            .put(4, new Mission(
                            4,
                            "Zjedzcie 10 jabłek",
                            MissionType.EATEN_ITEMS,
                            10,
                    "REWARD",
                            Duration.ofDays(1),
                            FilterMode.SPECIFIC,
                            ListBuilder.of(XMaterial.APPLE),
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.APPLE.parseMaterial())
                                            .setName("&7Zjedzcie 10 jabłek")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Zjedzcie 10 jabłek",
                                                    "&8» &7Progres: &e{percentage}%",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    16
                            )
                    )
            )
            .put(5, new Mission(
                            5,
                            "Przebiegnijcie 100 metrów",
                            MissionType.RUNNING_DISTANCE,
                            100,
                    "REWARD",
                            Duration.ofDays(1),
                            FilterMode.ALL,
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.DIAMOND_BOOTS.parseMaterial())
                                            .setName("&7Przebiegnijcie 100 metrów")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Przebiegnijcie 100 metrów",
                                                    "&8» &7Progres: &e{percentage}%",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    19
                            )
                    )
            )
            .put(6, new Mission(
                            6,
                            "Złapcie 1 ryb",
                            MissionType.CAUGHT_ITEMS,
                            1,
                            "REWARD",
                            Duration.ofDays(1),
                            FilterMode.SPECIFIC,
                            ListBuilder.of(XMaterial.COD, XMaterial.SALMON),
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.COD.parseMaterial())
                                            .setName("&7Złapcie jedną rybę")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Złapcie jedną rybę",
                                                    "&8» &7Progres: &e{percentage}%",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    21
                            )
                    )
            )
            .put(7, new Mission(
                            7,
                            "Napiszcie 5 wiadomość",
                            MissionType.WRITTEN_MESSAGE,
                            5,
                            "REWARD",
                            Duration.ofDays(1),
                            FilterMode.ALL,
                            new MissionIcon(
                                    ItemBuilder.of(XMaterial.WRITABLE_BOOK.parseMaterial())
                                            .setName("&7Napiszcie 5 wiadomość")
                                            .setLore(
                                                    "",
                                                    "&8» &7Status: {status}",
                                                    "&8» &7Napiszcie 5 wiadomość",
                                                    "&8» &7Progres: &e{percentage}%",
                                                    "&8» &7Misja resetuje sie za: &e{time}",
                                                    "&8» &7Nagroda: &e100 monet"
                                            )
                                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .toItemStack(),
                                    23
                            )
                    )
            )
            .build();

}