package com.github.Heliwr.IgnoreChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

public class Ignore extends JavaPlugin {
    private Map<String, List<String>> ignoreList = new HashMap<String, List<String>>();

    public void onEnable() {
        // Chat listener
    	getServer().getPluginManager().registerEvents(new IgnoreListener(this), this);

        // Setup commands
        getCommand("ignore").setExecutor(new IgnoreCommand(this));
        getCommand("ignore-list").setExecutor(new ListCommand(this));
    }

    public Map<String, List<String>> getList() {
        return ignoreList;
    }
}