package alini.cn.at;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Objects;
import java.io.IOException;
import java.io.File;

public final class At extends JavaPlugin {

    public static HashMap<String, Long> cooldowns = new HashMap<>();



    static int COOLDOWN_TIME;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // 输出一行日志，表示插件已经加载
        // 注册事件监听器,事件监听器为ATuser
        getServer().getPluginManager().registerEvents(new ATuser(), this);
        // 注册命令
        Objects.requireNonNull(getCommand("at")).setExecutor(new ATCommand());
        // 注册Tab补全
        this.getCommand("at").setTabCompleter(new ATCommandTabCompleter());
        // 加载配置文件
        // 获取配置文件
        File configFile = new File(getDataFolder(), "config.yml");

        // 检查配置文件是否存在
        if (!configFile.exists()) {
            // 如果不存在，创建新的配置文件
            this.saveDefaultConfig();
        }

        // 加载配置文件
        FileConfiguration config = this.getConfig();

        // 检查配置文件中是否有 "cooldown_time" 这个键
        if (!config.contains("cooldown_time")) {
            // 如果没有，创建一个新的配置节并设置默认值
            config.createSection("cooldown_time");
            config.set("cooldown_time", 5);
            // 保存配置文件
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        COOLDOWN_TIME = config.getInt("cooldown_time", 5);

        //注册配置文件

        getLogger().info("§a[@] 插件加载完成");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("§c[@] 插件已卸载");
    }

}