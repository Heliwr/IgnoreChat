package com.github.Heliwr.IgnoreChat;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

class IgnoreCommand implements CommandExecutor {
    private final Ignore plugin;
    public IgnoreCommand(final Ignore p) {
        plugin = p;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player) || args.length != 1) return false;

        Player player = (Player) sender;
        Player query = plugin.getServer().getPlayer(args[0]);        	
        String qn = args[0];
        
        if(!qn.startsWith("group.")) {
            if(query == null) {
                sender.sendMessage("[IgnoreChat] Player not found");
                return false;
            } else if (player == query || !player.hasPermission("ignore.use") || query.hasPermission("ignore.block")) {
                sender.sendMessage("[IgnoreChat] Forbidden");
                return false;
            }        	
            qn = query.getName();
        }
        
        String pn = player.getName();
        Map<String, List<String>> ignoreList = plugin.getList();

        if(!ignoreList.containsKey(pn)) ignoreList.put(pn, new ArrayList<String>());

        List<String> list = ignoreList.get(pn);
        if(list.contains(qn)) {
            list.remove(qn);
            sender.sendMessage("[IgnoreChat] No longer ignoring "+qn);
        } else {
            list.add(qn);
            sender.sendMessage("[IgnoreChat] Ignoring "+qn);
        }
        return true;
    }
}