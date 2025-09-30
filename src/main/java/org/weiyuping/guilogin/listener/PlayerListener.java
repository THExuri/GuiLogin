package org.weiyuping.guilogin.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.weiyuping.guilogin.GuiLogin;
import org.weiyuping.guilogin.logingui.LoginGui;


public class PlayerListener implements Listener {
    //监听玩家进入游戏事件
    private Player player;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        player = event.getPlayer();
        LoginGui.loginGui(player);
    }

    //未注册或登录取消关闭GUI
    @EventHandler
    public void onPlayerRegister(InventoryCloseEvent event) {
        player = (Player) event.getPlayer();
        String inventoryTitle = event.getView().getTitle();
        if (inventoryTitle.equals("登录界面") || inventoryTitle.equals(ChatColor.AQUA + "注册界面")) {
            Bukkit.getScheduler().runTaskLater(
                    GuiLogin.getInstance(),
                    () -> LoginGui.loginGui(player),
                    1L
            );
        }
    }

    //未登录或注册取消地狱门传送
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            if (!GuiListener.playerPasswords.containsKey(player.getName())) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(
                        GuiLogin.getInstance(),
                        () -> LoginGui.loginGui(player),
                        1L
                );
            }
        }
    }

    //未登录或注册取消玩家移动
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!GuiListener.playerPasswords.containsKey(player.getName())) {
            event.setCancelled(true);
        }
    }
}
