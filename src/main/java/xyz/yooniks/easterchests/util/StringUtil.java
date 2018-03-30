package xyz.yooniks.easterchests.util;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StringUtil {

    private static String itemToString(ItemStack is) {
        final StringBuilder builder = new StringBuilder();
        builder.append(is.getType().toString().toLowerCase().replace("_", ""));
        builder.append(" ");
        builder.append("x");
        builder.append(is.getAmount());
        builder.append(" ");
        if (is.hasItemMeta() && is.getItemMeta().getDisplayName() != null) {
            builder.append("&8(&a");
            builder.append(is.getItemMeta().getDisplayName());
            builder.append("&8)&r");
        }
        return builder.toString();
    }

    public static String itemsToString(List<ItemStack> items) {
        final StringBuilder builder = new StringBuilder();
        for (ItemStack is : items) {
            builder.append(itemToString(is));
            builder.append(" ");
        }
        return builder.toString();
    }
}
