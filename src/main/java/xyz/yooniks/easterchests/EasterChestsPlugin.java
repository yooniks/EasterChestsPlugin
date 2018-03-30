package xyz.yooniks.easterchests;

import org.bukkit.Material;
import org.bukkit.command.CommandMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.diorite.config.ConfigManager;
import xyz.yooniks.easterchests.command.ChestCommand;
import xyz.yooniks.easterchests.data.EasterChestsConfig;
import xyz.yooniks.easterchests.listener.*;
import xyz.yooniks.easterchests.util.ChatUtil;
import xyz.yooniks.easterchests.util.CommandUtil;
import xyz.yooniks.easterchests.util.DropUtil;
import xyz.yooniks.easterchests.util.InventoryUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public final class EasterChestsPlugin extends JavaPlugin {

    private final InventoryUtil inventoryUtil;
    private final EasterChestsConfig configuration;

    private ItemStack chestItemStack;

    public EasterChestsPlugin() {
        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdirs();
        final File configFile = new File(this.getDataFolder(), "config.yml");
        this.configuration = ConfigManager.createInstance(EasterChestsConfig.class);
        this.configuration.bindFile(configFile);

        if (!configFile.exists())
            this.configuration.save();
        this.configuration.load();

        this.inventoryUtil = new InventoryUtil(this.configuration);
    }

    public static EasterChestsPlugin getInst() {
        return getPlugin(EasterChestsPlugin.class);
    }

    @Override
    public void onEnable() {
        final CommandMap commandMap = CommandUtil.getCommandMap();
        commandMap.register("easterchest", new ChestCommand(this));

        initChestItemStack();
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new BlockPlace(this), this);
        pm.registerEvents(new InventoryClick(this), this);
        pm.registerEvents(new InventoryClose(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new PlayerItemDrop(), this);

        new DropUtil(this.configuration);
    }

    @Override
    public void onDisable() {
    }

    public InventoryUtil getInventoryUtil() {
        return inventoryUtil;
    }

    public EasterChestsConfig getConfiguration() {
        return configuration;
    }

    private void initChestItemStack() {
        this.chestItemStack = new ItemStack(Material.ENDER_CHEST);
        final ItemMeta im = this.chestItemStack.getItemMeta();
        im.setDisplayName(ChatUtil.fixColor(this.configuration.getChestName()));
        final List<String> lore = new LinkedList<>();
        this.configuration.getChestLore().forEach(string -> lore.add(ChatUtil.fixColor(string)));
        im.setLore(lore);
        this.chestItemStack.setItemMeta(im);
    }

    public ItemStack getChestItemStack() {
        return chestItemStack;
    }
}
