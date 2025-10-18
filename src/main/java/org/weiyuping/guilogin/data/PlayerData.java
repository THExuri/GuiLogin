package org.weiyuping.guilogin.data;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.weiyuping.guilogin.utils.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    private static Map<String, Boolean> loggedPlayers = new HashMap<>();
    //设置玩家密码
    public static void setPlayerPassword(Player player, String password) {
        setPlayerPassword(player.getName(), password);
    }

    public static void setPlayerPassword(String playerName, String password) {
        password = MD5Utils.encrypt(password, playerName);
        File file = new File("plugins/GuiLogin/playerData/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("password", password);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //玩家密码是否存在
    public static boolean isRegistered(Player player) {
        return playerPasswordExist(player.getName());
    }

    public static boolean playerPasswordExist(String playerName) {
        File file = new File("plugins/GuiLogin/playerData/", playerName + ".yml");
        return file.exists();
    }

    //获取玩家密码
    public static String getPlayerPassword(Player player) {
        return getPlayerPassword(player.getName());
    }

    public static String getPlayerPassword(String playerName) {
        File file = new File("plugins/GuiLogin/playerData/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getString("password");
    }
    //检测是否登录
    public static boolean isLogin(Player player) {
        return loggedPlayers.getOrDefault(player.getName(), false);
    }
    public static void setLogin(Player player , boolean isLogin) {
        loggedPlayers.put(player.getName(), isLogin);
    }
}

