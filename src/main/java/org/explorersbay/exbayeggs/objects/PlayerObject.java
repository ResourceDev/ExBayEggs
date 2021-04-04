package org.explorersbay.exbayeggs.objects;

import de.leonhard.storage.Yaml;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.explorersbay.exbayeggs.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerObject {

    @Getter @Setter HashMap<Integer, ItemStack> inventory = new HashMap<>();
    @Getter @Setter ItemStack offhand;
    @Getter @Setter UUID uuid;
    @Getter @Setter Player player;
    @Getter @Setter String name;
    @Getter @Setter public boolean isInEditMode = false;
    @Getter @Setter List<EggObject> claimed = new ArrayList<>();
    @Getter @Setter List<String> claimedList = new ArrayList<>();
    @Getter @Setter Yaml config;
    Main main;

    public PlayerObject(Player player, UUID uuid, String name, Main main) {
        this.main = main;
        this.player = player;
        this.uuid = uuid;
        this.name = name;
        this.config = new Yaml(uuid.toString(), "plugins/ExBayEggs/data");

        if (this.config.contains("claimed")) {
            List<String> claimedL = this.config.getStringList("claimed");
            for (String s : claimedL) {
                UUID eggUUID = UUID.fromString(s);
                if (eggUUID != null) {
                    EggObject eggObject = main.getEggHandler().getByUUID(eggUUID);
                    if (eggObject != null) {
                        claimed.add(eggObject);
                        claimedList.add(eggObject.toString());
                    }
                }
            }
        }

        Inventory inv = player.getInventory();
        this.offhand = player.getInventory().getItemInOffHand();

        for (int a = 0; a<=36; a++) {
            ItemStack item = inv.getItem(a);
            if (item != null) {
                inventory.put(a, item);
            }
        }
    }

    public void toggleEdit() {
        if (this.isInEditMode == false) {
            this.isInEditMode = true;
        } else {
            this.isInEditMode = false;
        }
    }

    public void loadInventory() {
        Inventory inv = player.getInventory();
        for (int a : inventory.keySet()) {
            inv.setItem(a, inventory.get(a));
        }
    }

    public void updateInventory(Player player) {
        inventory.clear();
        Inventory inv = player.getInventory();
        this.offhand = player.getInventory().getItemInOffHand();

        for (int a = 0; a<=36; a++) {
            ItemStack item = inv.getItem(a);
            if (item != null) {
                inventory.put(a, item);
            }
        }
    }

    public void addClaimedEgg(EggObject eggObject) {
        claimed.add(eggObject);
        UUID uuid = eggObject.getUuid();
        claimedList.add(uuid.toString());
        saveData();
    }

    public void saveData() {
        config.set("claimed", claimedList);
    }
}
