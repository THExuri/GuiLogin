package org.weiyuping.guilogin.logingui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.weiyuping.guilogin.GuiLogin;

import static org.weiyuping.guilogin.listener.GuiListener.playerPasswords;


public class LoginGui {
    public static void loginGui(Player player) {
        GuiLogin plugin = (GuiLogin) Bukkit.getPluginManager().getPlugin("GuiLogin");
        if (plugin != null) {
            //获取延迟时间
            int delayTime = plugin.getConfig().getInt("delayTime");
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                String title = isRegistered(player.getName()) ? "登录界面" : ChatColor.AQUA + "注册界面";
                //为玩家生成一个GUI登录界面
                Inventory inventory = Bukkit.createInventory(player, 54, Component.text(title));
                //设置index(数字盘)槽位物品,数量及名称
                ItemStack numberPane = new ItemStack(Material.valueOf("WHITE_STAINED_GLASS_PANE"));
                int amount = 1;
                int[] index = {21, 22, 23, 30, 31, 32, 39, 40, 41, 49};
                for (int j : index) {
                    ItemMeta numberPaneMeta = numberPane.getItemMeta();
                    numberPaneMeta.displayName(Component.text(ChatColor.WHITE + String.valueOf(amount)));
                    numberPane.setItemMeta(numberPaneMeta);
                    inventory.setItem(j, numberPane);
                    numberPane.setAmount(amount + 1);
                    amount++;
                }
                //设置其他填充槽位物品
                ItemStack paddingPane = new ItemStack(createNamedItem(Material.valueOf("GRAY_STAINED_GLASS_PANE"), " "));
                for (int i = 9; i < 53; i++) {
                    boolean isInIndex = false;
                    for (int j : index) {
                        if (i == j) {
                            isInIndex = true;
                            break;
                        }
                    }
                    if (!isInIndex && i != 48 && i != 50) {
                        inventory.setItem(i, paddingPane);
                    }
                }
                //设置其他槽位物品
                inventory.setItem(50, createNamedItem(Material.valueOf("LIME_STAINED_GLASS_PANE"), ChatColor.BLUE + "确认"));
                inventory.setItem(48, createNamedItem(Material.valueOf("RED_STAINED_GLASS_PANE"), ChatColor.YELLOW + "清空"));
                inventory.setItem(53, createNamedItem(Material.valueOf("ORANGE_STAINED_GLASS_PANE"), ChatColor.RED + "退出"));
                //为玩家打开GUI
                player.openInventory(inventory);
            }, delayTime);
        }
    }

    //自定义方法
    public static ItemStack createNamedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }
    private static boolean isRegistered(String playerName) {
        return playerPasswords.containsKey(playerName);
    }
}
