package org.weiyuping.guilogin.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.weiyuping.guilogin.data.PlayerData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;


public  class GuiLoginCommands implements CommandExecutor, TabCompleter {

    private static final List<String> COMMANDS = List.of("set");

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("用法: /guilogin <set> [player] [password]");
            return false;
        }
        Player player = Bukkit.getPlayer(args[1]);
        String password = PlayerData.getPlayerPassword(args[2]);
        if (player != null) {
            if (!PlayerData.playerPasswordExist(String.valueOf(player))) {
                sender.sendMessage("玩家" + player + "不存在");
                return false;
            }
            if (password.length() < 6) {
                sender.sendMessage("密码长度不能少于6个字符");
                return false;
            }
            if (password.contains("0")) {
                sender.sendMessage("密码中不能含有0");
                return false;
            }
            PlayerData.setPlayerPassword(player, password);
            sender.sendMessage("成功为玩家" + player + "设置新密码");
            return true;
        }
        return false;
    }
    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("guilogin")) {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], COMMANDS, commands);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    commands.add(player.getName());
                }
            }else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                return commands;
            }
        }
        return commands;
    }
}
