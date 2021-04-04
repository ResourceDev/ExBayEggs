package org.explorersbay.exbayeggs;

import de.leonhard.storage.Yaml;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.explorersbay.exbayeggs.objects.EggObject;
import org.explorersbay.exbayeggs.objects.EggReward;

import java.util.*;

public class EggHandler {

    Main main;
    @Getter List<EggObject> eggObjects = new ArrayList<>();
    @Getter List<EggReward> eggRewards = new ArrayList<>();
    Yaml data;

    public EggHandler(Main main) {
        this.main = main;
        this.data = main.getData();

        Yaml config = new Yaml("config.yml", "plugins/ExBayEggs");
        Set<String> rewards = config.singleLayerKeySet("rewards");

        for (String a : data.singleLayerKeySet("eggs")) {
            int x = data.getInt("eggs." + a + ".x");
            int y = data.getInt("eggs." + a + ".y");
            int z = data.getInt("eggs." + a + ".z");
            World world = Bukkit.getWorld(data.getString("eggs." + a + ".world"));

            EggObject eggObject = new EggObject(x,y,z,world,a);
            eggObjects.add(eggObject);
        }

        for (String r : rewards) {
            List<String> commands = config.getStringList("rewards." + r + ".commands");
            double chance = config.getDouble("rewards." + r + ".chance");
            EggReward reward = new EggReward(commands, chance);
            eggRewards.add(reward);
        }

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            for (EggObject eggObject : eggObjects) {
                Location location = new Location(eggObject.getWorld(), eggObject.getX()+0.5, eggObject.getY(), eggObject.getZ()+0.5);
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 10, 0.2, 0.2, 0.2);
            }
        }, 10L, 10L);
    }

    public void issueRewards(Player player) {
        boolean given = false;

        for (EggReward eggReward : eggRewards) {
            if (!given) {
                given = eggReward.execute(player, false);
            }
        }

        if (!given) {
            int size = eggRewards.size();
            Random random = new Random();
            int randomNo = random.nextInt(size);
            EggReward reward = eggRewards.get(randomNo);
            reward.execute(player, true);
        }
    }

    public void addEggObject(EggObject eggObject) {
        eggObjects.add(eggObject);
    }

    public void removeEggObject(EggObject eggObject) {
        eggObjects.remove(eggObject);
        data.remove(eggObject.getUuid().toString());
    }

    public void addEggReward(EggReward eggReward) {
        eggRewards.add(eggReward);
    }

    public void removeEggReward(EggReward eggReward) {
        eggRewards.add(eggReward);
    }

    public EggObject getByLocation(Location location) {
        for (EggObject eggObject : eggObjects) {
            if (eggObject.getX() == location.getBlockX()) {
                if (eggObject.getY() == location.getBlockY()) {
                    if (eggObject.getZ() == location.getBlockZ()) {
                        if (eggObject.getWorld().equals(location.getWorld())) {
                            return eggObject;
                        }
                    }
                }
            }
        }

        return null;
    }

    public EggObject getByUUID(UUID uuid) {
        for (EggObject eggObject : eggObjects) {
            if (eggObject.getUuid().equals(uuid)) {
                return eggObject;
            }
        }
        return null;
    }

}
