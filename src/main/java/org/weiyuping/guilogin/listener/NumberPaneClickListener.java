package org.weiyuping.guilogin.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.weiyuping.guilogin.gui.Title;
import org.weiyuping.guilogin.language.I18;


public class NumberPaneClickListener implements Listener {

    @EventHandler
    public void onNumberPaneClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory currentInventory = event.getClickedInventory();
        ItemStack clicked = event.getCurrentItem();

        if (event.getInventory().getHolder() instanceof Title.RegisterHolder || event.getInventory().getHolder() instanceof Title.LoginHolder) {
            if (clicked != null) {
                event.setCancelled(true);
                if (clicked.getType() == Material.WHITE_STAINED_GLASS_PANE) {
                    int slot = currentInventory.firstEmpty();
                    if (slot >= 0 && slot <9) {
                        currentInventory.setItem(slot, clicked);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 10, 1);
                    } else {
                        player.sendMessage(I18.get("password_toolong"));
                    }
                }
            }
        }
    }
}