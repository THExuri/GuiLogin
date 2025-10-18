package org.weiyuping.guilogin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.weiyuping.guilogin.gui.Title;

import static org.weiyuping.guilogin.data.PlayerData.*;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!isRegistered(player)) {
            event.getPlayer().openInventory(new Title.RegisterHolder().getInventory());
        } else {
            event.getPlayer().openInventory(new Title.LoginHolder().getInventory());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isLogin(player)) {
            setLogin(player, false);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!isRegistered(player) || !isLogin(player)) {
            event.setCancelled(true);
        }
    }
}
