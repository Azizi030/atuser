package alini.cn.at;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ATCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            // 检查命令发送者是否具有 at.admin 或 at.user 权限
            if (sender.hasPermission("at.admin") || sender.hasPermission("at.user")) {
                // 添加所有在线玩家的名字到补全结果列表中
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
            // 根据命令发送者的权限添加子命令到补全结果列表中
            if (sender.hasPermission("at.admin")) {
                completions.add("enable");
                completions.add("disable");
                completions.add("select");
                completions.add("shield");
                completions.add("unshield");
                completions.add("reload");
            } else if (sender.hasPermission("at.user")) {
                completions.add("enable");
                completions.add("disable");
                completions.add("select");
            }
        }
        return completions;
    }
}