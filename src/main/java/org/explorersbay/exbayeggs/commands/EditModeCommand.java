package org.explorersbay.exbayeggs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.explorersbay.exbayeggs.Main;
import org.explorersbay.exbayeggs.objects.PlayerObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EditModeCommand implements CommandExecutor, TabCompleter {

    Main main;
    public EditModeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1) {
            if (sender instanceof Player) {
                if (args[0].equalsIgnoreCase("toggle")) {
                    Player player = (Player) sender;
                    if (player.hasPermission("explorersbay.eggs.admin")) {
                        PlayerObject playerObject = main.getPlayerHandler().getFromUUID(player.getUniqueId());
                        if (playerObject != null) {

                            if (!playerObject.isInEditMode()) {
                                playerObject.updateInventory(player);
                                player.getInventory().clear();
                                player.getInventory().setItemInMainHand(main.randomHead());
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7You have just entered edit mode."));
                            }

                            if (playerObject.isInEditMode()) {
                                player.getInventory().clear();
                                playerObject.loadInventory();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7You have just exited edit mode."));
                            }

                            playerObject.toggleEdit();
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7Command usage: &e/eggeditmode toggle"));
                }
            } else {
                System.out.println("Consoles cannot send this command.");
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEXPLORERS&f&lBAY &8» &7Command usage: &e/eggeditmode toggle"));
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
