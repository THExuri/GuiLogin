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
import org.weiyuping.guilogin.data.PlayerData;
import org.weiyuping.guilogin.gui.Title;
import org.weiyuping.guilogin.utils.MD5Utils;

import static org.weiyuping.guilogin.data.PlayerData.getPlayerPassword;
import static org.weiyuping.guilogin.data.PlayerData.setPlayerPassword;
import static org.weiyuping.guilogin.utils.CleanUtils.cleanTheFirstLine;


public class OnLoginListener implements Listener {
    @EventHandler
    public void onLoginListener(InventoryClickEvent event) {

        ItemStack clicked = event.getCurrentItem();
        Inventory currentInventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (clicked != null) {
            if (event.getInventory().getHolder() instanceof Title.LoginHolder && clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                String password = "null";
                try {
                    password = linePassword(currentInventory);
                } catch (Exception ignored) {
                }
                if (!varifyPlayerPassword(player, password)) {
                    cleanTheFirstLine(currentInventory);
                }
            }
        }
    }
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
    private void encryptExistPassword(Player player, String password) {
        if (PlayerData.getPlayerPassword(player).split("").length < 10) {
            setPlayerPassword(player, password);
        }
    }
    private boolean varifyPlayerPassword(Player player, String password) {
        encryptExistPassword(player, getPlayerPassword(player));
        password = MD5Utils.encrypt(password, player.getName());
        if (!password.equals(getPlayerPassword(player))) {
            player.sendMessage(ChatColor.RED + "密码错误！");
            return false;
        } else {
            PlayerData.setLogin(player, true);
            player.sendMessage(ChatColor.GREEN + "登录成功！");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 1);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 10, 1);
            player.closeInventory();
            return true;
        }
    }
}
