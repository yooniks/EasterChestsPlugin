package xyz.yooniks.easterchests.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import xyz.yooniks.easterchests.EasterChestsPlugin;
import xyz.yooniks.easterchests.util.ChatUtil;

import java.util.Arrays;

public class ChestCommand extends BukkitCommand {

    private final EasterChestsPlugin plugin;

    public ChestCommand(EasterChestsPlugin plugin) {
        super("easterchest", "Chest command", "",
                Arrays.asList("chest", "wielkanocneskrzynie", "easterchests"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {
        if (cs instanceof Player) {
            final Player p = ((Player) cs);
            if (args.length == 0) {
                if (p.getInventory().firstEmpty() == -1) {
                    p.getWorld().dropItemNaturally(p.getLocation(), plugin.getChestItemStack());
                    p.sendMessage(ChatUtil.fixColor("&cMasz pelny ekwipunek, wyrzucono wielkanocna skrzynie na ziemie!"));
                    return true;
                }
                p.getInventory().addItem(plugin.getChestItemStack());
                p.sendMessage(ChatUtil.fixColor("&cOtrzymales &6wielkanocna skrzynie&c!"));
                return true;
            }
        }
        if (args.length > 1) {
            final Player target = Bukkit.getServer().getPlayerExact(args[0]);
            if (target == null) {
                cs.sendMessage(ChatUtil.fixColor("&cGracz &6" + args[0] + " &cjest offline!"));
                return true;
            }
            cs.sendMessage(ChatUtil.fixColor("&cGracz &6" + args[0] + "&c otrzymal &6wielkanocna skrzynie&c!"));
            if (target.getInventory().firstEmpty() == -1) {
                target.getWorld().dropItemNaturally(target.getLocation(), plugin.getChestItemStack());
                target.sendMessage(ChatUtil.fixColor("&cMasz pelny ekwipunek, wyrzucono wielkanocna skrzynie na ziemie!"));
                return true;
            }
            target.getInventory().addItem(plugin.getChestItemStack());
            target.sendMessage(ChatUtil.fixColor("&cOtrzymales &6wielkanocna skrzynke!"));
        }
        return true;
    }

}