package org.weiyuping.guilogin.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.weiyuping.guilogin.GuiLogin;
import org.weiyuping.guilogin.gui.Title;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.weiyuping.guilogin.data.PlayerData.isLogin;
import static org.weiyuping.guilogin.data.PlayerData.isRegistered;


public class LoginListener implements Listener {
    public static Map<UUID, Boolean> playerIsValidating = new HashMap<>();

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
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isLogin(player)) {
                    return;
                }
                boolean isValidating = playerIsValidating.getOrDefault(player.getUniqueId(), false);
                if (!isValidating) {
                    if (!isRegistered(player)) {
                        player.openInventory(new Title.RegisterHolder().getInventory());
                    } else {
                        player.openInventory(new Title.LoginHolder().getInventory());
                    }
                }
            }
        }.runTaskLater(GuiLogin.getInstance(), delay);
    }
}
