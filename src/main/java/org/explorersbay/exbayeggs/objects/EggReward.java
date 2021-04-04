package org.explorersbay.exbayeggs.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class EggReward {

    List<String> commands;
    double chance;

    public EggReward(List<String> commands, double chance) {
        this.commands = commands;
        this.chance = chance;
    }

    public boolean execute(Player player, boolean chanceOverride) {

        if (!chanceOverride) {
            Random random = new Random();
            int randomcompare = random.nextInt();

            if (randomcompare >= chance) {
                for (String c : commands) {
                    c = c.replace("%player%", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
                }
                return true;
            }
        }

        else {
            for (String c : commands) {
                c = c.replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            }
            return true;
        }

        return false;
    }

}
