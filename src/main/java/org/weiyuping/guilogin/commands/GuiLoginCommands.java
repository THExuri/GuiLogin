package org.weiyuping.guilogin.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.weiyuping.guilogin.data.PlayerData;
import org.weiyuping.guilogin.language.I18;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;


public  class GuiLoginCommands implements CommandExecutor, TabCompleter {

    private static final List<String> COMMANDS = List.of("set");

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(I18.get("command_usage"));
            return true;
        }
        Player player = Bukkit.getPlayer(args[1]);
        String password = PlayerData.getPlayerPassword(args[2]);
        if (player != null) {
            if (!PlayerData.playerPasswordExist(String.valueOf(player))) {
                sender.sendMessage(I18.get("player_not_exist"));
                return false;
            }
            if (password.length() < 6) {
                sender.sendMessage(I18.get("password_too_short"));
                return false;
            }
            if (password.contains("0")) {
                sender.sendMessage(I18.get("password_no_zero"));
                return false;
            }
            PlayerData.setPlayerPassword(player, password);
            sender.sendMessage(I18.get("password_set_success"));
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
