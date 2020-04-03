package com.pirgosth.skyshop;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	
	public boolean reload(CommandSender sender, String[] args) {
		if(args.length > 1) {
			sender.sendMessage(ChatColor.DARK_RED + "Too many arguments !");
			return true;
		}
		Load.config.reload();
		try {
			Load.shop.reload();
		}
		catch(Exception e) {
			Bukkit.getLogger().log(Level.WARNING, e.toString());
			sender.sendMessage(ChatColor.RED + "[SkyShop] Reload performed with error: " + e.toString());
			return true;
		}
		sender.sendMessage(ChatColor.DARK_GREEN + "[SkyShop] " + ChatColor.WHITE + "Plugin reloaded successfully !");
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(args.length >= 1) {
			switch(args[0].toLowerCase()) {
				case "reload":
					return reload(sender, args);
				default:
					return false;
			}
		}
		if(sender instanceof ConsoleCommandSender) {
			return true;
		}
		Player player = (Player) sender;
		Load.shop.openInventory(player);
		return true;
	}

}
