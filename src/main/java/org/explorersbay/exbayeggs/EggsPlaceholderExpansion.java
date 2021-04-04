package org.explorersbay.exbayeggs;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.explorersbay.exbayeggs.objects.PlayerObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EggsPlaceholderExpansion extends PlaceholderExpansion {
    Main main;
    public EggsPlaceholderExpansion(Main main) {
        this.main = main;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "explorerseggs";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ResourcesMCM";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        // %example_placeholder1%
        if(identifier.equals("collected")){
            UUID uuid = player.getUniqueId();
            PlayerObject playerObject = main.getPlayerHandler().getFromUUID(uuid);

            if (playerObject != null) {
                return String.valueOf(playerObject.getClaimedList().size());
            } else {
                return "0";
            }
        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }
}
