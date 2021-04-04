package org.explorersbay.exbayeggs.objects;

import de.leonhard.storage.Yaml;
import lombok.Getter;
import org.bukkit.World;

import java.util.UUID;

public class EggObject {

    @Getter int x;
    @Getter int y;
    @Getter int z;
    @Getter World world;
    @Getter UUID uuid;
    @Getter Yaml data = new Yaml("data", "plugins/ExBayEggs");

    public EggObject(int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.uuid = UUID.randomUUID();

        data.set("eggs."+uuid.toString()+".x", x);
        data.set("eggs."+uuid.toString()+".y", y);
        data.set("eggs."+uuid.toString()+".z", z);
        data.set("eggs."+uuid.toString()+".world", world.getName());
    }

    public EggObject(int x, int y, int z, World world, String uuid) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.uuid = UUID.fromString(uuid);
    }

}
