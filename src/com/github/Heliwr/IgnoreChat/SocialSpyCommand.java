package com.github.Heliwr.IgnoreChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialSpyCommand implements CommandExecutor{
    private final Ignore plugin;
    public SocialSpyCommand(final Ignore p) {
        plugin = p;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player == null) {
			sender.sendMessage("This command can only be run by a player");
		} else if(player.hasPermission("ignore.socialspy")) {
			toggleSocialSpy(player);
		} else return false;
		return true;
    }
    
	public void toggleSocialSpy(Player player) {
	    if(plugin.SocialSpy.containsKey(player)) {
	        if(plugin.SocialSpy.get(player)) {
	            plugin.SocialSpy.put(player, false);
	            player.sendMessage("Social Spy disabled");
	        } else {
	            plugin.SocialSpy.put(player, true);
	            player.sendMessage("Social Spy enabled");
	        }
	    } else {
	        plugin.SocialSpy.put(player, true);
	        player.sendMessage("Social Spy enabled");
	    }
	}    
}
