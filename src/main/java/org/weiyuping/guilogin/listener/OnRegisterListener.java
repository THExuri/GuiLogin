package org.weiyuping.guilogin.listener;

import org.bukkit.ChatColor;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.weiyuping.guilogin.data.PlayerData.*;
import static org.weiyuping.guilogin.utils.CleanUtils.cleanTheFirstLine;


public class OnRegisterListener implements Listener {

    private static Map<UUID, String> tempPlayerPassword = new HashMap<>();

    @EventHandler
    public void onRegisterListener(InventoryClickEvent event) {

        ItemStack clicked = event.getCurrentItem();
        Inventory currentInventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (clicked != null) {
            if (event.getInventory().getHolder() instanceof Title.RegisterHolder && clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                String password = "null";
                try {
                    password = linePassword(currentInventory);
                } catch (Exception ignored) {
                }
                if (password.equals("null") || password.isEmpty()) {
                    player.sendMessage(ChatColor.RED + I18.get("register_empty"));
                    return;
                }
                if (tempPlayerPassword.get(player.getUniqueId()) == null) {
                    tempPlayerPassword.put(player.getUniqueId(), password);
                    cleanTheFirstLine(currentInventory);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + I18.get("password_reinput"));
                } else {
                    if (!(password.equals(tempPlayerPassword.get(player.getUniqueId())))) {
                        player.sendMessage(ChatColor.RED + I18.get("register_mismatch"));
                        cleanTheFirstLine(currentInventory);
                    } else {
                        setLogin(player, true);
                        setPlayerPassword(player, password);
                        player.sendMessage(ChatColor.GREEN + I18.get("register_success"));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 1);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 10, 1);
                        player.closeInventory();
                    }
                }
            }
        }
    }
    //首行玻璃板密码获取
    private String linePassword(Inventory inventory) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) {
                stringBuilder.append(itemStack.getAmount());
            }
        }
        return stringBuilder.toString();
    }
}