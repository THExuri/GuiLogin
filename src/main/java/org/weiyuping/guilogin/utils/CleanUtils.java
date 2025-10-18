package org.weiyuping.guilogin.utils;

import org.bukkit.inventory.Inventory;

public class CleanUtils {
    public static void cleanTheFirstLine(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, null);
        }
    }
}
