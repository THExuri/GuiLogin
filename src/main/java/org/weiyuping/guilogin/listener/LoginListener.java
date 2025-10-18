package org.weiyuping.guilogin.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.weiyuping.guilogin.GuiLogin;
import org.weiyuping.guilogin.gui.Title;

import static org.weiyuping.guilogin.data.PlayerData.isLogin;
import static org.weiyuping.guilogin.data.PlayerData.isRegistered;


public class LoginListener implements Listener {

    long delay = 1L;

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isRegistered(player)) {
                    player.openInventory(new Title.RegisterHolder().getInventory());
                } else {
                    // 密码存在 还需要判断是否已登录，不然会无限打开登录GUI
                    if (!isLogin(player)) {
                        player.openInventory(new Title.LoginHolder().getInventory());
                    }
                }
            }
        }.runTaskLater(GuiLogin.getInstance(), delay);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!isRegistered(player)) {
            event.getPlayer().openInventory(new Title.RegisterHolder().getInventory());
        } else {
            if (!isLogin(player)) {
                event.getPlayer().openInventory(new Title.LoginHolder().getInventory());
            }
        }
    }
}
