package xyz.yooniks.easterchests.util;

import org.bukkit.inventory.ItemStack;
import xyz.yooniks.easterchests.data.EasterChestsConfig;

import java.util.ArrayList;
import java.util.List;

public class DropUtil {

    private final static List<ItemStack> drops = new ArrayList<>();

    public DropUtil(EasterChestsConfig config) {
        for (String dropFromString : config.getDropList()) {
            final ItemStack drop = Parser.parseItemStack(dropFromString);
            drops.add(drop);
        }
    }

    public static List<ItemStack> getDrops() {
        return drops;
    }
}
