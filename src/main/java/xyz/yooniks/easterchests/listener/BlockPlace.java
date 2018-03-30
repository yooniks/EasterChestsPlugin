package xyz.yooniks.easterchests.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.easterchests.EasterChestsPlugin;
import xyz.yooniks.easterchests.util.InventoryUtil;

public class BlockPlace implements Listener {

    private final InventoryUtil inventoryUtil;

    private final EasterChestsPlugin plugin;

    public BlockPlace(EasterChestsPlugin plugin) {
        this.inventoryUtil = plugin.getInventoryUtil();
        this.plugin = plugin;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        final ItemStack item = e.getItemInHand();

        if (item == null || item.getType() != plugin.getChestItemStack().getType()) return;
        if (!item.hasItemMeta() || item.getItemMeta().getDisplayName() == null) return;

        final Player p = e.getPlayer();
        final String name = item.getItemMeta().getDisplayName();
        if (name.equalsIgnoreCase(plugin.getChestItemStack().getItemMeta().getDisplayName())) {

            if (!e.isCancelled()) e.setCancelled(true);

            inventoryUtil.openSelectInventory(p);
            return;
        }
    }
}
