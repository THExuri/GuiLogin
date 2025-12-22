package org.weiyuping.guilogin.listener;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.weiyuping.guilogin.GuiLogin;
import org.weiyuping.guilogin.gui.Title;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.weiyuping.guilogin.data.PlayerData.isLogin;
import static org.weiyuping.guilogin.data.PlayerData.isRegistered;


public class LoginListener implements Listener {
    public static Map<UUID, Boolean> playerIsValidating = new HashMap<>();

    long delay = 1L;
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isRegistered(player)) {
                    player.openInventory(new Title.RegisterHolder().getInventory());
                } else {
                    if (!isLogin(player)) {
                        player.openInventory(new Title.LoginHolder().getInventory());
                    }
                }
            }
        }.runTaskLater(GuiLogin.getInstance(), delay);
        FileConfiguration config = GuiLogin.getInstance().getConfig();
        if (config.getBoolean("enableLoginPoint")) {
            String worldName = config.getString("loginPoint.world");
            double x = config.getDouble("loginPoint.x");
            double y = config.getDouble("loginPoint.y");
            double z = config.getDouble("loginPoint.z");
            if (worldName != null) {
                World world = GuiLogin.getInstance().getServer().getWorld(worldName);
                Location location = new Location(world, x, y, z);
                player.teleport(location);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isLogin(player)) {
                    return;
                }
                boolean isValidating = playerIsValidating.getOrDefault(player.getUniqueId(), false);
                if (!isValidating) {
                    if (!isRegistered(player)) {
                        player.openInventory(new Title.RegisterHolder().getInventory());
                    } else {
                        player.openInventory(new Title.LoginHolder().getInventory());
                    }
                }
            }
        }.runTaskLater(GuiLogin.getInstance(), delay);
    }
    public void initializeLoginPoint() {
        FileConfiguration config = GuiLogin.getInstance().getConfig();
        if (config.getBoolean("enableLoginPoint") && !config.contains("loginPoint.x")) {

            World world = GuiLogin.getInstance().getServer().getWorlds().get(0);
            Location location = world.getSpawnLocation();

            config.set("loginPoint.world", world.getName());
            config.set("loginPoint.x", location.getX());
            config.set("loginPoint.y", location.getY());
            config.set("loginPoint.z", location.getZ());

            GuiLogin.getInstance().saveConfig();
            GuiLogin.getInstance().getLogger().info("已自动设置登录点为世界出生点: " + location);
        }
    }
    public LoginListener() {
        initializeLoginPoint();
    }
}
