package org.weiyuping.guilogin.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static org.weiyuping.guilogin.gui.Item.guiItem;

public class Title {
    public abstract static class GuiHolder implements InventoryHolder {
        private final Inventory inventory;

        public GuiHolder(String title) {
            this.inventory = Bukkit.createInventory(this, 54, title);
            guiItem(this.inventory);
        }

        @Override
        public @NotNull Inventory getInventory() {
            return this.inventory;
        }
    }

    //注册Gui
    public static class RegisterHolder extends GuiHolder {
        public RegisterHolder() {
            super(ChatColor.AQUA + "注册界面");
        }
    }

    //登录Gui
    public static class LoginHolder extends GuiHolder {
        public LoginHolder() {
            super("登录界面");
        }
    }
}