package org.explorersbay.exbayeggs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.explorersbay.exbayeggs.commands.EditModeCommand;
import org.explorersbay.exbayeggs.events.EggInteractEvent;
import org.explorersbay.exbayeggs.events.EggPlaceEvent;
import org.explorersbay.exbayeggs.events.EggPlayerRegisterEvents;
import org.explorersbay.exbayeggs.objects.EggObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class Main extends JavaPlugin {

    Yaml config;
    @Getter Yaml data;
    @Getter PlayerHandler playerHandler;
    @Getter EggHandler eggHandler;
    @Getter public List<String> base64 = new ArrayList<>();
    EggsPlaceholderExpansion expansion;

    @Override
    public void onEnable() {

        // Plugin startup logic
        File configFile = new File(getDataFolder(), "config.yml");
        config = LightningBuilder.fromFile(configFile).addInputStream(getResource("config.yml")).setConfigSettings(ConfigSettings.PRESERVE_COMMENTS).setReloadSettings(ReloadSettings.INTELLIGENT).setDataType(DataType.SORTED).createYaml();
        File dataFile = new File(getDataFolder(), "data.yml");
        data = LightningBuilder.fromFile(dataFile).addInputStream(getResource("data.yml")).setConfigSettings(ConfigSettings.PRESERVE_COMMENTS).setReloadSettings(ReloadSettings.INTELLIGENT).setDataType(DataType.SORTED).createYaml();

        //Gathering all the base64 heads from the config.
        for (String base64s : config.getStringList("skulls")) {
            base64.add(base64s);
        }

        this.eggHandler = new EggHandler(this);
        this.playerHandler = new PlayerHandler(this);

        //Register all associated events.
        EggPlayerRegisterEvents eggPlayerRegisterEvents = new EggPlayerRegisterEvents(this);
        getServer().getPluginManager().registerEvents(eggPlayerRegisterEvents, this);
        EggPlaceEvent eggPlaceEvent = new EggPlaceEvent(this);
        getServer().getPluginManager().registerEvents(eggPlaceEvent, this);
        EggInteractEvent eggInteractEvent = new EggInteractEvent(this);
        getServer().getPluginManager().registerEvents(eggInteractEvent, this);

        //Registering Commands
        EditModeCommand command = new EditModeCommand(this);
        getCommand("eggeditmode").setExecutor(command);
        getCommand("eggeditmode").setTabCompleter(command);

        expansion = new EggsPlaceholderExpansion(this);
        expansion.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ItemStack randomHead() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        int size = base64.size();
        Random random = new Random();
        int select = random.nextInt(size);
        String texture = base64.get(select);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eEaster Egg"));
        head.setItemMeta(meta);
        return head;
    }
}
