package xyz.yooniks.easterchests.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import xyz.yooniks.easterchests.EasterChestsPlugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class CommandUtil {

    private static CommandMap commandMap = null;

    public static CommandMap getCommandMap() {
        if (commandMap == null) {
            final Field field;
            try {
                field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);
                commandMap = (CommandMap) field.get(Bukkit.getServer());
            } catch (Exception ex) {
                EasterChestsPlugin.getInst().getLogger().log(Level.WARNING, "Error during getting commandMap: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return commandMap;
    }
}
