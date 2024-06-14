package alini.cn.at;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class ChatTab implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        getLogger().info("TabCompleteEvent was triggered.");
        String uncomplete = event.getBuffer();
        if (uncomplete.contains("@") && !uncomplete.endsWith(" ")) {
            String at = uncomplete.substring(uncomplete.lastIndexOf("@") + 1);
            List<String> fit = new ArrayList<>();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(at.toLowerCase())) {
                    if (uncomplete.contains(" ")) {
                        fit.add(uncomplete.substring(uncomplete
                                .lastIndexOf(" ") + 1, uncomplete
                                .lastIndexOf("@")) + "@" + p
                                .getName());
                        continue;
                    }
                    fit.add(uncomplete.substring(0, uncomplete
                            .lastIndexOf("@")) + "@" + p
                            .getName());
                }
            }
            event.getCompletions().clear();
            event.getCompletions().addAll(fit);
        }
    }
}