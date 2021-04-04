package org.explorersbay.exbayeggs.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.explorersbay.exbayeggs.Main;
import org.explorersbay.exbayeggs.objects.EggObject;
import org.explorersbay.exbayeggs.objects.PlayerObject;

import java.util.List;

public class EggInteractEvent implements Listener {

    Main main;
    public EggInteractEvent(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void eggInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PlayerObject playerObject = main.getPlayerHandler().getFromUUID(player.getUniqueId());
        Block block = e.getClickedBlock();
        if (block != null) {
            Location location = block.getLocation();
            if (playerObject != null) {
                EggObject eggObject = main.getEggHandler().getByLocation(location);
                if (eggObject != null) {
                    List<EggObject> claimed = playerObject.getClaimed();
                    if (!claimed.contains(eggObject)) {
                        playerObject.addClaimedEgg(eggObject);
                        main.getEggHandler().issueRewards(player);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7Congrats! You have claimed an egg!"));
                    }
                }
            }
        }
    }
}
