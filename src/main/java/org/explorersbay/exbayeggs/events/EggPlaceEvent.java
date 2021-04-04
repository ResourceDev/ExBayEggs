package org.explorersbay.exbayeggs.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.explorersbay.exbayeggs.Main;
import org.explorersbay.exbayeggs.objects.EggObject;
import org.explorersbay.exbayeggs.objects.PlayerObject;

public class EggPlaceEvent implements Listener {

    Main main;
    public EggPlaceEvent(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEggPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItemInHand();
        PlayerObject playerObject = main.getPlayerHandler().getFromUUID(player.getUniqueId());
        if (playerObject != null) {
            if (playerObject.isInEditMode()) {
                Location location = e.getBlock().getLocation();
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                World world = location.getWorld();
                EggObject eggObject = new EggObject(x,y,z,world);
                main.getEggHandler().addEggObject(eggObject);
                player.getInventory().setItemInMainHand(main.randomHead());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7You have successfully placed an easter egg!"));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEggBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        EggObject eggObject = main.getEggHandler().getByLocation(location);
        if (eggObject != null) {
            if (player.hasPermission("explorersbay.eggs.admin")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7You have successfully broken an easter egg."));
                main.getEggHandler().removeEggObject(eggObject);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7You cannot break easter eggs!"));
            }
        }
    }

}
