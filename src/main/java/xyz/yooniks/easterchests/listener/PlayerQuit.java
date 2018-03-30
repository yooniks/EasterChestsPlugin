package xyz.yooniks.easterchests.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.yooniks.easterchests.basic.util.UserUtils;

public class PlayerQuit implements Listener {

    @EventHandler
    public void handleQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        UserUtils.removeUser(p);
    }
}
