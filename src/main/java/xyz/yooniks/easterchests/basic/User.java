package xyz.yooniks.easterchests.basic;

import lombok.ToString;
import org.bukkit.inventory.Inventory;

@ToString
public class User {

    private Inventory openingInventory;

    public User() {

    }

    public Inventory getOpeningInventory() {
        return openingInventory;
    }

    public void setOpeningInventory(Inventory inv) {
        this.openingInventory = inv;
    }
}
