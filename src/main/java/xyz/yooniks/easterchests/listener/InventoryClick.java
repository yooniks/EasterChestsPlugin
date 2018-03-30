package xyz.yooniks.easterchests.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.yooniks.easterchests.EasterChestsPlugin;

public class InventoryClick implements Listener {

    private final EasterChestsPlugin plugin;

    public InventoryClick(EasterChestsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getInventory() == null || e.getCurrentItem() == null) return;
        plugin.getInventoryUtil().manageEvent(e);
    }
}
