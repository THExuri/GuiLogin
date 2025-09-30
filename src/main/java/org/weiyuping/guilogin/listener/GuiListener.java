package org.weiyuping.guilogin.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.weiyuping.guilogin.GuiLogin;
import org.weiyuping.guilogin.logingui.LoginGui;
import org.weiyuping.guilogin.utils.YamlFileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GuiListener implements Listener {
    //储存玩家密码
    public static Map<String, String> playerPasswords = new HashMap<>();
    private Map<Player, List<ClickNumber>> playerInput = new HashMap<>();

    //储存当前输入的密码
    private String Password(List<ClickNumber> input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ClickNumber clickNumber : input) {
            stringBuilder.append(clickNumber.number);
        }
        return stringBuilder.toString();
    }

    private String componentToString(Component component) {
        return net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText().serialize(component);
    }

    //禁止玩家拿出物品
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();

        //禁止玩家拿出物品
        if (!title.equals("登录界面") && !title.equals(ChatColor.AQUA + "注册界面")) {
            return;
        }
        event.setCancelled(true);

        List<ClickNumber> currentInput = playerInput.computeIfAbsent(player, k -> new ArrayList<>());
        //数字盘槽位
        int[] passwordPane = {21, 22, 23, 30, 31, 32, 39, 40, 41, 49};
        //密码显示位置
        int[] displayPlace = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        //获取点击的槽位
        int slot = event.getSlot();
        for (int j : passwordPane) {
            if (slot == j) {
                if (currentInput.size() < 9) {
                    ItemStack clickedItem = inventory.getItem(slot);
                    int actualNumber = 1;
                    if (clickedItem != null) {
                        ItemMeta meta = clickedItem.getItemMeta();
                        if (meta != null && meta.displayName() != null) {
                            String displayName = componentToString(meta.displayName());
                            try {
                                actualNumber = Integer.parseInt(displayName.trim());
                            } catch (NumberFormatException ignored) {
                                actualNumber = clickedItem.getAmount();
                            }
                        } else {
                            actualNumber = clickedItem.getAmount();
                        }
                    }
                    currentInput.add(new ClickNumber(slot, actualNumber));
                    updateDisplay(inventory, displayPlace, currentInput);
                    player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, 10, 1);
                }
            }
            //清空密码
            if (slot == 48) {
                currentInput.clear();
                clearDisplay(inventory, displayPlace);
                return;
            }
        }
        //退出登录
        if (slot == 53) {
            player.kickPlayer("");
        }
        //确认注册与密码
        if (slot == 50) {
            if (title.equals(ChatColor.AQUA + "注册界面")) {
                if (currentInput.size() >= 4) {
                    String password = Password(currentInput);
                    String hashedPassword = YamlFileUtils.hashPassword(password);
                    playerPasswords.put(player.getName(), hashedPassword);
                    GuiLogin plugin = GuiLogin.getInstance();
                    plugin.savePlayerData(player.getName(), hashedPassword);
                    player.sendMessage(ChatColor.GREEN + "注册成功");
                    player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_CHIME, 10, 1);
                    currentInput.clear();
                    player.closeInventory();
                    LoginGui.loginGui(player);
                } else {
                    player.sendMessage(ChatColor.RED + "密码长度不能小于4位");
                }
            } else if (title.equals("登录界面")) {
                String password = Password(currentInput);
                String savedPassword = playerPasswords.get(player.getName());
                if (YamlFileUtils.verifyPassword(password, savedPassword)) {
                    player.sendMessage(ChatColor.GREEN + "登录成功");
                    player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_BELL, 10, 1);
                    currentInput.clear();
                    player.closeInventory();
                } else {
                    player.sendMessage(ChatColor.RED + "密码错误");
                    currentInput.clear();
                    clearDisplay(inventory, displayPlace);
                }
            }
        }
    }

    //玩家退出游戏时清空输入
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerInput.clear();
    }

    private void updateDisplay(Inventory inventory, int[] displayPlace, List<ClickNumber> currentInput) {
        for (int i = 0; i < currentInput.size(); i++) {
            if (i < 9) {
                // 从GUI中获取该位置实际显示的数字
                int actualNumber = currentInput.get(i).number;
                ItemStack item = new ItemStack(Material.valueOf("WHITE_STAINED_GLASS_PANE"), actualNumber);
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.displayName(Component.text(ChatColor.WHITE + String.valueOf(actualNumber)));
                    item.setItemMeta(itemMeta);
                }
                inventory.setItem(displayPlace[i], item);
            }
        }
    }

    //禁止玩家丢弃GUI出物品
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!GuiListener.playerPasswords.containsKey(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    //交互限制
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!GuiListener.playerPasswords.containsKey(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    //清空方法
    private void clearDisplay(Inventory inventory, int[] displayPlace) {
        for (int j : displayPlace) {
            ItemStack item = new ItemStack(Material.valueOf("AIR"));
            inventory.setItem(j, item);
        }
    }
    //获取点击的数字
    private static class ClickNumber {
        public final int slot;
        public final int number;

        public ClickNumber(int slot, int number) {
            this.slot = slot;
            this.number = number;
        }
    }
}
