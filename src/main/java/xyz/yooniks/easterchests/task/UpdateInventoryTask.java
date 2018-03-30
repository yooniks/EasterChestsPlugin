package xyz.yooniks.easterchests.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.yooniks.easterchests.EasterChestsPlugin;
import xyz.yooniks.easterchests.OpenType;
import xyz.yooniks.easterchests.basic.User;
import xyz.yooniks.easterchests.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UpdateInventoryTask extends BukkitRunnable {

    private final Inventory inv;
    private final User user;
    private final Player player;
    private final OpenType type;
    private final List<ItemStack> drops = new ArrayList<>();
    private final List<Integer> centerSlots;

    private int count = 10;

    public UpdateInventoryTask(Inventory inv, User user, Player player, OpenType type, Integer... centerSlots) {
        this.inv = inv;
        this.user = user;
        this.player = player;
        this.centerSlots = Arrays.asList(centerSlots);
        this.type = type;

        for (int slot : this.centerSlots) {
            final ItemStack randomItem = this.getRandomItem();
            this.inv.setItem(slot, randomItem);
        }

        final ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        final ItemMeta im = glass.getItemMeta();
        im.setDisplayName(ChatUtil.fixColor("&8# &aTrwa otwieranie.."));
        glass.setItemMeta(im);
        for (int i = 0; i < this.inv.getSize(); i++) {
            if (this.inv.getItem(i) == null)
                this.inv.setItem(i, glass);
        }
        this.user.setOpeningInventory(this.inv);
        this.player.openInventory(this.inv);

        final ItemStack toRemove = this.player.getItemInHand().clone();
        toRemove.setAmount(type.getRows());
        this.player.getInventory().removeItem(toRemove);
        this.runTaskTimer(EasterChestsPlugin.getInst(), 0L, 15L);
    }

    @Override
    public void run() {
        if (!this.player.isOnline() || this.user == null || this.user.getOpeningInventory() == null) {
            this.cancel();
            return;
        }
        this.count--;
        if (count < 10) {
            for (int slot : centerSlots) {
                final ItemStack randomItem = this.getRandomItem();
                inv.setItem(slot, randomItem);
                if (count <= 0)
                    this.drops.add(randomItem);
                player.updateInventory();
            }
        }
        if (count <= 0) {
            Bukkit.getServer().broadcastMessage(ChatUtil.fixColor(
                    EasterChestsPlugin.getInst().getConfiguration().getOpenMessage()
                            .replace("{PLAYER}", player.getName())
                            .replace("{TYPE}", type.name())
                            .replace("{ITEMS}", StringUtil.itemsToString(this.drops))));
            for (ItemStack is : this.drops) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), is);
                    continue;
                }
                player.getInventory().addItem(is);
            }
            user.setOpeningInventory(null);
            player.closeInventory();
            FireworkUtil.spawnFirework(player.getLocation());
            this.cancel();
            return;
        }
    }

    private ItemStack getRandomItem() {
        final List<ItemStack> items = DropUtil.getDrops();
        final int size = items.size();
        final Random rand = RandomUtil.RANDOM_INSTANCE;
        final int randomInt = rand.nextInt(size);
        return items.get(randomInt);
    }
}
