package org.explorersbay.exbayeggs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.explorersbay.exbayeggs.Main;
import org.explorersbay.exbayeggs.objects.PlayerObject;

public class EggPlayerRegisterEvents implements Listener {

    Main main;
    public EggPlayerRegisterEvents(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerObject playerObject = new PlayerObject(player, player.getUniqueId(), player.getName(), main);
        main.getPlayerHandler().addPlayer(playerObject);
    }

}
