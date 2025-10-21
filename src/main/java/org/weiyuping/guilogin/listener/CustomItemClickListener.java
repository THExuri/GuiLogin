package org.weiyuping.guilogin.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.weiyuping.guilogin.gui.Title;

import static org.weiyuping.guilogin.utils.CleanUtils.cleanTheFirstLine;


public class CustomItemClickListener implements Listener {
    @EventHandler
    public void onCustomItemClickListener(InventoryClickEvent event) {


        ItemStack clicked = event.getCurrentItem();
        Inventory currentInventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getHolder() instanceof Title.RegisterHolder || event.getInventory().getHolder() instanceof Title.LoginHolder) {
            if (clicked != null) {
                if (clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
                    cleanTheFirstLine(currentInventory);
                } else if (clicked.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                    player.closeInventory();
                    player.kickPlayer(ChatColor.AQUA + "请重新登录！");
                }
            }
        }
        if (event.getInventory().getHolder() instanceof Title.RegisterHolder) {
            if (clicked != null) {
                if (clicked.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                    player.sendMessage(ChatColor.RED + "你尚未注册！");
                }
            }
        }
    }
}
