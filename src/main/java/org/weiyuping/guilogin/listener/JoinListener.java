package org.weiyuping.guilogin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import static org.weiyuping.guilogin.data.PlayerData.*;

public class JoinListener implements Listener {
    public static void cancelIfNotLoggedIn(Cancellable event) {
        if (event instanceof PlayerEvent) {
            Player player = ((PlayerEvent) event).getPlayer();
            if (!isRegistered(player) || !isLogin(player)) {
                event.setCancelled(true);
            }
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
        cancelIfNotLoggedIn(event);

    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        cancelIfNotLoggedIn(event);

    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        cancelIfNotLoggedIn(event);
    }
}
