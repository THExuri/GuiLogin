package org.weiyuping.guilogin;

import org.weiyuping.guilogin.listener.GuiListener;
import org.weiyuping.guilogin.listener.PlayerListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class GuiLogin extends JavaPlugin {
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        File dataFile = new File(getDataFolder(), "passworddata.yml");
        if (!dataFile.exists()) {
            getDataFolder().mkdirs();
            saveResource("passworddata.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);

        loadPlayerPasswords();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        savePlayerData();
    }

    public void loadPlayerPasswords() {
        ConfigurationSection section = dataConfig.getConfigurationSection("playerPasswords");
        if (section != null) {
            for (String playerName : section.getKeys(false)) {
                String hashedPassword = section.getString(playerName);
                if (hashedPassword != null) {
                    GuiListener.playerPasswords.put(playerName, hashedPassword);
                }
            }
        }
    }

    private void savePlayerData() {
        try {
            dataConfig.set("playerPasswords", null);
            for (Map.Entry<String, String> entry : GuiListener.playerPasswords.entrySet()) {
                dataConfig.set("playerPasswords." + entry.getKey(), entry.getValue());
            }
                File dataFile = new File(getDataFolder(), "passworddata.yml");
                dataConfig.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
