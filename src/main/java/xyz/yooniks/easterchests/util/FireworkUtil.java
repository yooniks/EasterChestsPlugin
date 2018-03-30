package xyz.yooniks.easterchests.util;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;

public class FireworkUtil {

    public static void spawnFirework(Location loc) {
        loc.getWorld().playSound(loc, Sound.FIREWORK_LARGE_BLAST2, 3.1F, 3.1F);
        loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1, 200);
    }
}
