package com.github.Heliwr.IgnoreChat;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

class IgnoreListener implements Listener {
    private final Ignore plugin;

    public IgnoreListener(Ignore p) {
        plugin = p;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) return;

        Map<String, List<String>> ignoreList = plugin.getList();
        String p = event.getPlayer().getName();
        Player player = event.getPlayer();
        //        List<String> playerIgnores = ignoreList.get(p);

        for (Iterator<Player> it = event.getRecipients().iterator(); it.hasNext();) {
            Player r = it.next();
            List<String> recipientIgnores = ignoreList.get(r.getName());
//            boolean playerIgnoresRecipient = playerIgnores != null && playerIgnores.contains(r.getName());
            boolean recipientIgnoresPlayer = recipientIgnores != null && (recipientIgnores.contains(p) || (recipientIgnores.contains("group.default") && player.hasPermission("group.default")));

            if (recipientIgnoresPlayer) {
                it.remove();
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) return;

		String message = event.getMessage();
        Map<String, List<String>> ignoreList = plugin.getList();
        String p = event.getPlayer().getName();
		Player player = event.getPlayer();

		if (message.toLowerCase().startsWith("/me ")) {
			String s = message.substring(message.indexOf(" ")).trim();
			
			for (Player r : plugin.getServer().getOnlinePlayers()) {
	            List<String> recipientIgnores = ignoreList.get(r.getName());
	            boolean recipientIgnoresPlayer = recipientIgnores != null && (recipientIgnores.contains(p) || (recipientIgnores.contains("group.default") && player.hasPermission("group.default")));
	            
	            if (!recipientIgnoresPlayer) {
	            	r.sendMessage("* " + player.getName() + " " + s);
	            }
			}
			event.setCancelled(true);
		}
		
		
		if (message.toLowerCase().startsWith("/msg ") || message.toLowerCase().startsWith("/tell ")) {
			if (!message.contains(" ")) {
				player.sendMessage("ErrMsgFormat");
				event.setCancelled(true);
				return;
			} else {
				message = message.substring(message.indexOf(" ")).trim();
			}
			
			String name = message.substring(0, message.indexOf(" "));
			List<Player> recipients;
			Player r;

			if (name.substring(0, 1).equalsIgnoreCase("@")) {
				r = plugin.getServer().getPlayerExact(name.substring(1));
			} else {
				recipients = plugin.getServer().matchPlayer(name);
				if (recipients.size() > 1) {
					player.sendMessage(ChatColor.RED + "Too Many Players Found");
					event.setCancelled(true);
					return;
				} else if(recipients.size() == 0) {
					player.sendMessage(ChatColor.RED + "Player" + name + "Not Found");
					event.setCancelled(true);
					return;
				}

				r = recipients.get(0);
			}	
            
			List<String> recipientIgnores = ignoreList.get(r.getName());
			boolean recipientIgnoresPlayer = recipientIgnores != null && (recipientIgnores.contains(p) || (recipientIgnores.contains("group.default") && player.hasPermission("group.default")));

            if (recipientIgnoresPlayer) {
				player.sendMessage(ChatColor.RED + "Player" + name + "is ignoring you.");
            	event.setCancelled(true);
            	return;
            }
		}
    }
}