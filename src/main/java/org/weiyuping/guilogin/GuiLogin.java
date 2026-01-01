package org.weiyuping.guilogin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.weiyuping.guilogin.commands.GuiLoginCommands;
import org.weiyuping.guilogin.language.I18;
import org.weiyuping.guilogin.listener.*;

public final class GuiLogin extends JavaPlugin {
    private static GuiLogin instance;

    public static GuiLogin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        I18.loadLanguage();
        this.getLogger().info(ChatColor.AQUA + I18.get("plugin_enabled"));
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new NumberPaneClickListener(), this);
        getServer().getPluginManager().registerEvents(new OnLoginListener(), this);
        getServer().getPluginManager().registerEvents(new OnRegisterListener(), this);
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new CustomItemClickListener(), this);

        if (Bukkit.getPluginCommand("guilogin") != null) {
            Bukkit.getPluginCommand("guilogin").setExecutor(new GuiLoginCommands());
            Bukkit.getPluginCommand("guilogin").setTabCompleter(new GuiLoginCommands());
        }
    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.AQUA + I18.get("plugin_disabled"));
    }
}