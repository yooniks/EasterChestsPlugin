package xyz.yooniks.easterchests.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import xyz.yooniks.easterchests.basic.User;
import xyz.yooniks.easterchests.basic.util.UserUtils;

public class PlayerItemDrop implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void dropItem(PlayerDropItemEvent e) {
        final Player p = e.getPlayer();
        final User u = UserUtils.getOrCreateUser(p);
        if (u.getOpeningInventory() == null) return;
        e.setCancelled(true);
    }
}
