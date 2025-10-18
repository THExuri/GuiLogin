package org.weiyuping.guilogin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.weiyuping.guilogin.listener.*;

public final class GuiLogin extends JavaPlugin {
    private static GuiLogin instance;

    public static GuiLogin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info(ChatColor.AQUA +"GuiLogin 启动成功");
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new NumberPaneClickListener(), this);
        getServer().getPluginManager().registerEvents(new OnLoginListener(), this);
        getServer().getPluginManager().registerEvents(new OnRegisterListener(), this);
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new CustomItemClickListener(), this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.AQUA +"GuiLogin 已停止");
    }
}