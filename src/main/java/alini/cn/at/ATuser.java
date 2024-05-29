package alini.cn.at;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static alini.cn.at.At.COOLDOWN_TIME;
import static org.bukkit.Bukkit.getServer;

public class ATuser implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();

        if (message.contains("@")) {
           //检查玩家是否有at.user权限
            if (event.getPlayer().hasPermission("at.admin")) {
                //获取玩家名字
                String playerName = event.getPlayer().getName();
                //获取被at的玩家名字
                String atPlayerName = null;
                Pattern pattern = Pattern.compile("@[\\w]+");
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    atPlayerName = matcher.group().substring(1); // 去掉 @ 符号
                }
                if (atPlayerName != null) {
                    //将原始消息中的@玩家名字替换为蓝色的@玩家名字
                    message = message.replace("@" + atPlayerName, "§b@" + atPlayerName + "§f");
                    //将替换后的消息发送给所有玩家
                    event.setMessage(message);
                    //检查是否有被at的玩家
                    if (event.getPlayer().getServer().getPlayer(atPlayerName) != null) {
                        //管理员权限，不管被at的玩家是否有at.shield权限，都发送消息
                        event.getPlayer().getServer().getPlayer(atPlayerName).sendMessage("§4[管理员] §b[@] §e" + playerName + "§f在聊天中提到了你");

                    }
                }
            }else if (event.getPlayer().hasPermission("at.user")) {
                //获取玩家名字
                String playerName = event.getPlayer().getName();
                if (At.cooldowns.containsKey(playerName)) {
                    long secondsLeft = (At.cooldowns.get(playerName) - System.currentTimeMillis()) / 1000;
                    if (secondsLeft > 0) {
                        // 如果玩家在冷却期内，发送一条消息并返回
                        event.getPlayer().sendMessage("§c[@] 你还需要等待 " + secondsLeft + " 秒才能再次使用@功能。");
                        return;
                    }
                }
                //获取被at的玩家名字
                String atPlayerName = null;
                Pattern pattern = Pattern.compile("@[\\w]+");
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    atPlayerName = matcher.group().substring(1); // 去掉 @ 符号
                }
                if (atPlayerName != null) {
                    //将原始消息中的@玩家名字替换为蓝色的@玩家名字
                    message = message.replace("@" + atPlayerName, "§b@" + atPlayerName + "§f");
                    //将替换后的消息发送给所有玩家
                    event.setMessage(message);
                    //检查是否有被at的玩家
                    if (event.getPlayer().getServer().getPlayer(atPlayerName) != null) {
                        //如果有被at的玩家，检查被at的玩家是否有at.shield权限,如果没有则发送消息
                        At.cooldowns.put(playerName, System.currentTimeMillis() + COOLDOWN_TIME * 1000);
                        if (!event.getPlayer().getServer().getPlayer(atPlayerName).hasPermission("at.shield")) {
                            event.getPlayer().getServer().getPlayer(atPlayerName).sendMessage("§b[@] §e" + playerName + "§f在聊天中提到了你");
                        }
                    }
                }
            } else {
                event.getPlayer().sendMessage("§c[@] 你没有权限使用@功能，缺失 > at.user");
            }
        }
    }
}