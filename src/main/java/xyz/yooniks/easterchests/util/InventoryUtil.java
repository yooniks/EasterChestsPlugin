package xyz.yooniks.easterchests.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.yooniks.easterchests.EasterChestsPlugin;
import xyz.yooniks.easterchests.OpenType;
import xyz.yooniks.easterchests.basic.User;
import xyz.yooniks.easterchests.basic.util.UserUtils;
import xyz.yooniks.easterchests.data.EasterChestsConfig;
import xyz.yooniks.easterchests.task.UpdateInventoryTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryUtil {

    private final String OPEN_INVENTORY_NAME;
    private final Inventory SELECT_INVENTORY;

    private final EasterChestsConfig config;

    public InventoryUtil(EasterChestsConfig config) {
        this.SELECT_INVENTORY = Bukkit.getServer().createInventory(
                null, config.getSelectInventorySize(), config.getSelectInventoryName());
        this.OPEN_INVENTORY_NAME = "Otwieranie skrzynki...";
        loadSelectInventory();
        this.config = config;
    }

    public void manageEvent(InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equalsIgnoreCase(this.SELECT_INVENTORY.getTitle())
                && !e.getInventory().getTitle().equalsIgnoreCase(this.OPEN_INVENTORY_NAME)) return;

        e.setCancelled(true);
        int amount = 0;
        final Player player = (Player) e.getWhoClicked();
        for (ItemStack is : player.getInventory().getContents()) {
            if (is == null || is.getType() != Material.ENDER_CHEST) continue;
            if (is.hasItemMeta()
                    && is.getItemMeta().getDisplayName().equalsIgnoreCase(
                    EasterChestsPlugin.getInst().getChestItemStack().getItemMeta().getDisplayName())) {
                amount += is.getAmount();
                continue;
            }
        }
        if (amount == 0) {
            player.sendMessage(ChatUtil.fixColor("&cNie masz skrzyni w ekwipunku!"));
            return;
        }
        final String title = e.getInventory().getTitle();
        if (title.equalsIgnoreCase(this.SELECT_INVENTORY.getTitle())) {
            final ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem.getItemMeta() == null
                    || clickedItem.getItemMeta().getDisplayName() == null) return;
            final String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            final String[] split = itemName.split("-"); //this is a little bit idiotic
            if (split.length < 1) return;
            if (!IntegerUtil.isInteger(split[1])) {
                System.out.println("\"" + split[1] + "\" is not integer!");
                return;
            }
            final int ID = Integer.parseInt(split[1]);
            final OpenType type = OpenType.getByID(ID);
            if (amount < type.getRows()) {
                player.sendMessage(ChatUtil.fixColor("&cNie masz tyle skrzynek!"));
                player.closeInventory();
                return;
            }
            final User user = UserUtils.getOrCreateUser(player);
            player.closeInventory();
            this.openOpeningInventory(user, player, type);
            return;
        }
    }

    private void loadSelectInventory() {
        final List<Integer> slots = Arrays.asList(4, 13, 22, 31, 40, 49); //slots in select's inventory

        int id = 0; //super match system XD
        for (int slot : slots) {
            id++;
            this.SELECT_INVENTORY.setItem(slot, getItemSelect(OpenType.getByID(id)));
        }
        final ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        final ItemMeta im = glass.getItemMeta();
        im.setDisplayName(ChatUtil.fixColor("&8#"));
        for (int i = 0; i < this.SELECT_INVENTORY.getSize(); i++) {
            if (this.SELECT_INVENTORY.getItem(i) == null)
                this.SELECT_INVENTORY.setItem(i, glass);
        }
    }

    public void openSelectInventory(Player p) {
        p.openInventory(this.SELECT_INVENTORY);
    }

    private ItemStack getItemSelect(OpenType type) {
        final ItemStack select = new ItemStack(Material.PAPER, 1);
        final ItemMeta im = select.getItemMeta();
        im.setDisplayName(ChatUtil.fixColor("&cOtworz: &6" + type.getName()));
        select.setItemMeta(im);
        return select;
    }

    private void openOpeningInventory(User user, Player player, OpenType type) {
        final List<Integer> slots = new ArrayList<>();//Arrays.asList(4,13,22,31,40,49);
        //again super system match, this is not my good side XD
        if (type.getRows() == 1) {
            slots.add(4);
        } else if (type.getRows() == 2) {
            slots.add(4);
            slots.add(13);
        } else if (type.getRows() == 3) {
            slots.add(4);
            slots.add(13);
            slots.add(22);
        } else if (type.getRows() == 4) {
            slots.add(4);
            slots.add(13);
            slots.add(22);
            slots.add(31);
        } else if (type.getRows() == 5) {
            slots.add(4);
            slots.add(13);
            slots.add(22);
            slots.add(31);
            slots.add(40);
        } else if (type.getRows() == 6) {
            slots.add(4);
            slots.add(13);
            slots.add(22);
            slots.add(31);
            slots.add(40);
            slots.add(49);
        }
        final Inventory inv = Bukkit.getServer().createInventory(
                null, type.getSlots(), OPEN_INVENTORY_NAME);
        new UpdateInventoryTask(inv, user, player, type, slots.toArray(new Integer[]{})); //i think it's idiotic XD
    }
}
