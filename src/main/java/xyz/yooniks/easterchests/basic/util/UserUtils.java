package xyz.yooniks.easterchests.basic.util;

import org.bukkit.entity.Player;
import xyz.yooniks.easterchests.basic.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserUtils {

    private final static Map<UUID, User> users = new HashMap<>();

    public static User getOrCreateUser(Player player) {
        final UUID uuid = player.getUniqueId();
        if (!users.containsKey(uuid))
            users.put(uuid, new User());
        return users.get(uuid);
    }

    public static void removeUser(Player player) {
        if (users.containsKey(player.getUniqueId()))
            users.remove(player.getUniqueId());
    }
}
