package xyz.yooniks.easterchests.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.yooniks.easterchests.EasterChestsPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class Parser {

    public static ItemStack parseItemStack(String string) {

        final ItemStack is = new ItemStack(Material.DIAMOND);
        final ItemMeta im = is.getItemMeta();

        final String[] args = string.split(" ");
        for (String arg : args) {
            final String[] splitArg = arg.split(":");
            final String key = splitArg[0];
            final String value = splitArg[1];
            if (key.equalsIgnoreCase("material")) {
                final Material mat = Material.matchMaterial(value);
                if (mat == null) continue;
                is.setType(mat);
            } else if (key.equalsIgnoreCase("amount")) {
                if (!IntegerUtil.isInteger(value)) continue;
                is.setAmount(Integer.parseInt(value));
            } else if (key.equalsIgnoreCase("name")) {
                final String name = value.replace("_", " ");
                im.setDisplayName(ChatUtil.fixColor(name));
            } else if (key.equalsIgnoreCase("lore")) {
                final List<String> lore = new LinkedList<>();
                final String[] splitLore = value.split("%newline%");
                for (String s : splitLore) {
                    lore.add(ChatUtil.fixColor(s.replace("_", " ")));
                }
                im.setLore(lore);
            } else if (key.equalsIgnoreCase("data") || key.equalsIgnoreCase("durability")) {
                if (!IntegerUtil.isInteger(value)) continue;
                final short data = (short) Integer.parseInt(value);
                is.setDurability(data);
            } else if (key.equalsIgnoreCase("enchants")) {
                final String[] enchantmentArray = value.split("%newline%");
                for (String s : enchantmentArray) {
                    final String[] enchantmentSplit = s.split(";");
                    if (enchantmentSplit.length < 1) continue;
                    final Enchantment ench;
                    try {
                        ench = Enchantment.getByName(enchantmentSplit[0]);
                    } catch (Exception ex) {
                        EasterChestsPlugin.getInst().getLogger()
                                .log(Level.WARNING, "Enchantment: " + enchantmentSplit[0] + " does not exist!");
                        continue;
                    }
                    if (!IntegerUtil.isInteger(enchantmentSplit[1])) continue;
                    final int level = Integer.parseInt(enchantmentSplit[1]);
                    if (ench != null)
                        im.addEnchant(ench, level, true);
                }
            }
        }

        is.setItemMeta(im);
        return is;
    }
}
