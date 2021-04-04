package org.explorersbay.exbayeggs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.explorersbay.exbayeggs.objects.PlayerObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerHandler {

    Main main;
    List<PlayerObject> playerObjects = new ArrayList<>();

    public PlayerHandler(Main main) {
        this.main = main;

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerObject playerObject = getFromUUID(player.getUniqueId());
            if (playerObject == null) {
                PlayerObject pPlayer = new PlayerObject(player, player.getUniqueId(), player.getName(), main);
                addPlayer(pPlayer);
            }
        }
    }

    public void addPlayer(PlayerObject playerObject) {
        playerObjects.add(playerObject);
    }

    public void removePlayer(PlayerObject playerObject) {
        playerObjects.remove(playerObject);
    }

    public PlayerObject getFromUUID(UUID uuid) {

        for (PlayerObject playerObject : playerObjects) {
            if (uuid.equals(playerObject.getUuid())) {
                return playerObject;
            }
        }

        return null;
    }

    public PlayerObject getFromName(String name) {

        for (PlayerObject playerObject : playerObjects) {
            if (name.equalsIgnoreCase(playerObject.getName())) {
                return playerObject;
            }
        }

        return null;
    }

}
