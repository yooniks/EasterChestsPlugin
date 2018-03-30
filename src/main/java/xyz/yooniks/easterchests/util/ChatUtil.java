package xyz.yooniks.easterchests.util;

import org.bukkit.ChatColor;

public class ChatUtil {

    public static String fixColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
