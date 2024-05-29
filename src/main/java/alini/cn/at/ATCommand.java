package alini.cn.at;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;



import static alini.cn.at.At.COOLDOWN_TIME;
import static org.bukkit.Bukkit.getServer;

public class ATCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 如果玩家输入的是/at,则输出命令帮助


        if (args.length == 0) {
            if (sender.hasPermission("at.admin")) {
                //输出命令帮助
                sender.sendMessage("§b======[@]命令帮助========");
                sender.sendMessage("§b[@] /at - 输出命令帮助");
                sender.sendMessage("§b[@] /at <玩家名字> - @玩家");
                sender.sendMessage("§b[@] /at all - @所有人");
                sender.sendMessage("§b[@] /at enable - 开启@免打扰");
                sender.sendMessage("§b[@] /at disable - 关闭@免打扰");
                sender.sendMessage("§b[@] /at select - 查询@免打扰状态");
                sender.sendMessage("§b[@] /at shield <玩家名字> - 给玩家添加at.shield权限");
                sender.sendMessage("§b[@] /at unshield <玩家名字> - 给玩家移除at.shield权限");
                sender.sendMessage("§b[@] /at reload - 重载配置文件");
                sender.sendMessage("§b======[@]作者：24Zi======");
                return true;
            } else if (sender.hasPermission("at.user")) {
                //输出命令帮助
                sender.sendMessage("§b======[@]命令帮助========");
                sender.sendMessage("§b[@] /at - 输出命令帮助");
                sender.sendMessage("§b[@] /at <玩家名字> - 向玩家发送at消息");
                sender.sendMessage("§b[@] /at enable - 开启@免打扰");
                sender.sendMessage("§b[@] /at disable - 关闭@免打扰");
                sender.sendMessage("§b[@] /at select - 查询@免打扰状态");
                sender.sendMessage("§b======[@]作者：24Zi======");
                return true;
            } else {
                // 如果没有权限，输出一行日志
                sender.sendMessage("§c你没有at.user权限");
                return true;
            }
        } else if (args.length == 1) {
            // 如果玩家输入的是/at reload,且拥有at.admin权限，则重载配置文件
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("at.admin")) {
                At.getPlugin(At.class).reloadConfig();
                FileConfiguration config = At.getPlugin(At.class).getConfig();
                // 更新 COOLDOWN_TIME 的值
                At.COOLDOWN_TIME = config.getInt("cooldown_time", 5);
                sender.sendMessage("§b[@] §f配置文件已重新加载， 冷却等待已更新为§e" + At.COOLDOWN_TIME + "§f秒");
                return true;
            } else if (args[0].equalsIgnoreCase("enable") && sender.hasPermission("at.user")) {
                //输出一行日志
                sender.sendMessage("§b[@] §f你已开启@免打扰");
                //给玩家添加at.shield权限
                if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                    // LuckPerms 插件已经被加载
                    //不使用LuckPerms的API，直接使用luckperms的命令
                    getServer().dispatchCommand(getServer().getConsoleSender(), "lp user " + sender.getName() + " permission set at.shield true");
                    return true;
                } else if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
                    //直接使用manuaddp命令添加权限，不需要使用GroupManager的API
                    getServer().dispatchCommand(getServer().getConsoleSender(), "manuaddp " + sender.getName() + " at.shield");
                    return true;
                } else {
                    // 没有找到 LuckPerms 或 GroupManager 插件
                    // 使用 Bukkit 的方法来添加权限
                    sender.addAttachment(At.getPlugin(At.class), "at.shield", true);
                    //向服务器控制台输出一行日志
                    getServer().getConsoleSender().sendMessage("§4[警告] §b[@] §f未找到LuckPerms或GroupManager插件，已使用Bukkit方法添加权限");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("disable") && sender.hasPermission("at.user")) {
                // 如果玩家输入的是/at disable,且拥有at.user权限，则关闭@免打扰
                //输出一行日志
                sender.sendMessage("§b[@] §f你已关闭@免打扰");
                //给玩家移除at.shield权限
                if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                    // LuckPerms 插件已经被加载
                    //不使用LuckPerms的API，直接使用luckperms的命令
                    getServer().dispatchCommand(getServer().getConsoleSender(), "lp user " + sender.getName() + " permission unset at.shield");
                    return true;
                } else if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
                    //直接使用manudelp命令移除权限，不需要使用GroupManager的API
                    getServer().dispatchCommand(getServer().getConsoleSender(), "manudelp " + sender.getName() + " at.shield");
                    return true;
                } else {
                    // 没有找到 LuckPerms 或 GroupManager 插件
                    // 使用 Bukkit 的方法来移除权限
                    sender.addAttachment(At.getPlugin(At.class), "at.shield", false);
                    //向服务器控制台输出一行日志
                    getServer().getConsoleSender().sendMessage("§4[警告] §b[@] §f未找到LuckPerms或GroupManager插件，已使用Bukkit方法移除权限");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("select") && sender.hasPermission("at.user")) {
                // 如果玩家输入的是/at select,且拥有at.user权限，则查询@免打扰状态
                //输出一行日志
                sender.sendMessage("§b[@] §f你的@免打扰状态为" + (sender.hasPermission("at.shield") ? "§a开启" : "§c关闭"));
                return true;
            } else {
                // 如果玩家输入的是/at <玩家名字>,则向玩家发送at消息
                if (sender.hasPermission("at.admin")) {
                    if (args[0].equalsIgnoreCase("all")) {
                        if (sender.hasPermission("at.all")) {
                            //获取玩家名字
                            String playerName = sender.getName();
                            //获取所有在线玩家
                            for (Player player : getServer().getOnlinePlayers()) {
                                //将消息发送给所有玩家
                                player.sendMessage("§b[@] §e" + playerName + "§f悄悄地§b@§f了所有人");
                            }
                            return true;
                        }else {
                            sender.sendMessage("§b[@] §c你没有at.all权限");
                            return true;
                        }
                    }
                    //获取玩家名字
                    String playerName = sender.getName();
                    //获取被at的玩家名字
                    String atPlayerName = args[0];
                    //将消息发送给被at的玩家
                    if (sender.getServer().getPlayer(atPlayerName) != null) {
                        sender.getServer().getPlayer(atPlayerName).sendMessage("§4[管理员] §b[@] §e" + playerName + "§f悄悄地§b@§f了你");
                    }else {
                        sender.sendMessage("§4[管理员] §b[@] §c玩家不在线");
                    }
                    return true;
                } else if (sender.hasPermission("at.user")) {
                    if (args[0].equalsIgnoreCase("all")) {
                        if (sender.hasPermission("at.all")) {
                            //获取玩家名字
                            String playerName = sender.getName();
                            //获取所有在线玩家
                            for (Player player : getServer().getOnlinePlayers()) {
                                //将消息发送给所有玩家
                                player.sendMessage("§b[@] §e" + playerName + "§f悄悄地§b@§f了所有人");
                            }
                            return true;
                        }else {
                            sender.sendMessage("§b[@] §c你没有at.all权限");
                            return true;
                        }
                    }
                    //获取玩家名字
                    String playerName = sender.getName();
                    if (At.cooldowns.containsKey(playerName)) {
                        long secondsLeft = (At.cooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                        if (secondsLeft > 0) {
                            // 如果玩家在冷却期内，发送一条消息并返回
                            sender.sendMessage("§c你还需要等待 " + secondsLeft + " 秒才能再次使用 @ 功能。");
                            return true;
                        }
                    }
                    //获取被at的玩家名字
                    String atPlayerName = args[0];
                    //将消息发送给被at的玩家
                    if (sender.getServer().getPlayer(atPlayerName) != null) {
                        //检查被at的玩家是否有at.shield权限
                        At.cooldowns.put(playerName, System.currentTimeMillis() + COOLDOWN_TIME * 1000);
                        if (!sender.getServer().getPlayer(atPlayerName).hasPermission("at.shield")) {
                            sender.getServer().getPlayer(atPlayerName).sendMessage("§b[@] §e" + playerName + "§f悄悄地§b@§f了你");
                            return true;
                        }
                    }else {
                        sender.sendMessage("§b[@] §c玩家不在线");
                        return true;
                    }
                }
            }
        }else if (args.length == 2) {
            // 如果玩家输入的是/at shield <玩家名字>,则给玩家添加at.shield权限
            if (args[0].equalsIgnoreCase("shield") && sender.hasPermission("at.admin")) {
                //获取参数中的玩家名字
                String atPlayerName = args[1];
                //给玩家添加at.shield权限
                if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                    // LuckPerms 插件已经被加载
                    //不使用LuckPerms的API，直接使用luckperms的命令
                    getServer().dispatchCommand(getServer().getConsoleSender(), "lp user " + atPlayerName + " permission set at.shield true");
                    return true;
                } else if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
                    //使用manuaddp命令添加权限，不需要使用GroupManager的API
                    getServer().dispatchCommand(getServer().getConsoleSender(), "manuaddp " + atPlayerName + " at.shield");
                    return true;
                } else {
                    // 没有找到 LuckPerms 或 GroupManager 插件
                    // 使用 Bukkit 的方法来添加权限
                    Player atPlayer = getServer().getPlayer(atPlayerName);
                    if (atPlayer != null) {
                        atPlayer.addAttachment(At.getPlugin(At.class), "at.shield", true);
                    }
                    //向服务器控制台输出一行日志
                    getServer().getConsoleSender().sendMessage("§4[警告] §b[@] §f未找到LuckPerms或GroupManager插件，已使用Bukkit方法添加权限");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("unshield") && sender.hasPermission("at.admin")){
                // 如果玩家输入的是/at unshield <玩家名字>,则给玩家移除at.shield权限
                //获取玩家名字
                String playerName = sender.getName();
                //获取被at的玩家名字
                String atPlayerName = args[1];
                //给玩家移除at.shield权限
                if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                    // LuckPerms 插件已经被加载
                    // 不使用LuckPerms的API，直接使用luckperms的命令
                    getServer().dispatchCommand(getServer().getConsoleSender(), "lp user " + atPlayerName + " permission unset at.shield");
                    return true;
                } else if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
                    //使用manudelp命令移除权限，不需要使用GroupManager的API
                    getServer().dispatchCommand(getServer().getConsoleSender(), "manudelp " + atPlayerName + " at.shield");
                    return true;
                } else {
                    // 没有找到 LuckPerms 或 GroupManager 插件
                    // 使用 Bukkit 的方法来移除权限
                    Player atPlayer = getServer().getPlayer(atPlayerName);
                    if (atPlayer != null) {
                        atPlayer.addAttachment(At.getPlugin(At.class), "at.shield", false);
                    }
                    //向服务器控制台输出一行日志
                    getServer().getConsoleSender().sendMessage("§4[警告] §b[@] §f未找到LuckPerms或GroupManager插件，已使用Bukkit方法移除权限");
                    return true;
                }
            }
        }else {
            //如果玩家输入的命令不符合要求，输出一行日志
            sender.sendMessage("§c[@] 请输入/at查看命令帮助");
            return true;
        }
        return false;
    }
}