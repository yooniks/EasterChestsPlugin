package xyz.yooniks.easterchests.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.yooniks.easterchests.EasterChestsPlugin;
import xyz.yooniks.easterchests.basic.User;
import xyz.yooniks.easterchests.basic.util.UserUtils;

public class InventoryClose implements Listener {

    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        final Player p = ((Player) e.getPlayer());
        final User u = UserUtils.getOrCreateUser(p);
        if (u.getOpeningInventory() != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.openInventory(u.getOpeningInventory());
                }
            }.runTaskLater(EasterChestsPlugin.getInst(), 10L);
        }
    }
}
