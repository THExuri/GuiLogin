package org.weiyuping.guilogin.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    public static void guiItem(Inventory inventory) {
        ItemStack numberPane = new ItemStack(Material.valueOf("WHITE_STAINED_GLASS_PANE"));
        int[] numberSlots = {21, 22, 23, 30, 31, 32, 39, 40, 41, 49};
        int amount = 0;
        for (int j : numberSlots) {
            numberPane.setAmount(++amount);
            ItemMeta itemMeta = numberPane.getItemMeta();
            itemMeta.displayName(Component.text(amount));
            numberPane.setItemMeta(itemMeta);
            inventory.setItem(j, numberPane);
        }
        ItemStack extraPane = new ItemStack(customItem(Material.valueOf("BLACK_STAINED_GLASS_PANE"), " "));
        for (int i = 9; i < 53; i++) {
            boolean isNumberSlots = false;
            for (int j : numberSlots) {
                if (i == j) {
                    isNumberSlots = true;
                    break;
                }
            }
            if (!isNumberSlots && i != 48 && i != 50 && i != 45) {
                inventory.setItem(i, extraPane);
            }
        }
        inventory.setItem(50, customItem(Material.valueOf("LIME_STAINED_GLASS_PANE"), ChatColor.BLUE + "确认"));
        inventory.setItem(48, customItem(Material.valueOf("RED_STAINED_GLASS_PANE"), ChatColor.YELLOW + "清空"));
        inventory.setItem(53, customItem(Material.valueOf("ORANGE_STAINED_GLASS_PANE"), ChatColor.RED + "退出"));
    }

    public static ItemStack customItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }
}
