package xyz.yooniks.easterchests.data;

import org.diorite.config.Config;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.CustomKey;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.Header;

import java.util.Arrays;
import java.util.List;

@Header("Plugin's author: yooniks")
@Footer("MY-LOBBY.PL - coming soon..")
public interface EasterChestsConfig extends Config {

    @Comment("Name of select's inventory")
    @CustomKey("select-inventory-name")
    default String getSelectInventoryName() {
        return "Wybierz typ otwierania..";
    }

    @Comment("Size of select's inventory")
    @CustomKey("select-inventory-size")
    default int getSelectInventorySize() {
        return 9 * 6;
    }

    @Comment("Name of opening item")
    @CustomKey("name-chest")
    default String getChestName() {
        return "&a&lWielkanocna skrzynia";
    }

    @Comment("Lore of opening item")
    @CustomKey("lore-chest")
    default List<? extends String> getChestLore() {
        return Arrays.asList("&8# &6Poloz na ziemi, aby &eOTWORZYC&6!", "&8# &7www.youtube.com/c/Enchanted3");
    }

    @Comment({"Message when player opened chest",
            "Available variables: {PLAYER} - player's name, {TYPE} - opening type, {ITEMS} list of dropped items"})
    @CustomKey("open-message")
    default String getOpenMessage() {
        return "&8[-------&b&lEASTER CHESTS&8-------]\n" +
                "&7Gracz &6{PLAYER} &7otworzyl wielkanocna skrzynke!\n" +
                "&7Typ otwierania: &6{TYPE}\n" +
                "&7Wydropione itemy: &6{ITEMS}" +
                "\n&8[-------&b&lEASTER CHESTS&8-------]";
    }

    @Comment("List of drop")
    @CustomKey("chest-drop")
    default List<? extends String> getDropList() {
        return Arrays.asList("material:DIAMOND_SWORD name:&6Przykladowy_drop lore:&6Pierwsza_linia%newline%&6Kolejna_linia",
                "material:DIAMOND_PICKAXE enchants:DIG_SPEED;5%newline%DURABILITY;3",
                "material:GOLDEN_APPLE data:1");
    }
}
