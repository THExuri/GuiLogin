package org.weiyuping.guilogin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import static org.weiyuping.guilogin.data.PlayerData.*;

public class JoinListener implements Listener {


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
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!isLogin(player) || !isLogin(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!isLogin(player) || !isLogin(player)) {
            event.setCancelled(true);
        }
    }
}
