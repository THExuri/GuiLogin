package org.weiyuping.guilogin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.weiyuping.guilogin.gui.Title;

import static org.weiyuping.guilogin.data.PlayerData.isLogin;

public class ItemListener implements Listener {
    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Title.RegisterHolder || event.getInventory().getHolder() instanceof Title.LoginHolder) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isLogin(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!isLogin(player)) {
            event.setCancelled(true);
        }
    }
}
