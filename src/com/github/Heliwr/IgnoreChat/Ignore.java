package com.github.Heliwr.IgnoreChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.commandbook.CommandBook;

public class Ignore extends JavaPlugin {
    private Map<String, List<String>> ignoreList = new HashMap<String, List<String>>();
    CommandBook commandbook =  (CommandBook) Bukkit.getServer().getPluginManager().getPlugin("CommandBook");    
    boolean cmdbook = false;
    
    public void onEnable() {
        // Chat listener
        if(commandbook != null) {
        	cmdbook = true;
          }

    	getServer().getPluginManager().registerEvents(new IgnoreListener(this), this);

        // Setup commands
        getCommand("ignore").setExecutor(new IgnoreCommand(this));
        getCommand("ignore-list").setExecutor(new ListCommand(this));
    }

    public Map<String, List<String>> getList() {
        return ignoreList;
    }
}