package org.weiyuping.guilogin.listener;

import org.weiyuping.guilogin.logingui.LoginGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    //监听玩家进入游戏事件
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LoginGui.loginGui(player);
    }
    @EventHandler
    public void onPlayerRegister(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String title = event.getView().getTitle();
        if (title.equals(ChatColor.AQUA + "注册界面") || title.equals("登录界面")) {
            if (!GuiListener.playerPasswords.containsKey(player.getName())) {
                Bukkit.getScheduler().runTaskLater(
                        Bukkit.getPluginManager().getPlugin("GuiLogin"),
                        () -> {
                            LoginGui.loginGui(player);
                        },
                        20
                );
            }
        }
    }
}
