package io.github.pirgosth.skyshop.commands;

import io.github.pirgosth.liberty.core.api.commands.ICommandListener;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommand;
import io.github.pirgosth.liberty.core.commands.CommandParameters;
import io.github.pirgosth.skyshop.SkyShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Commands implements ICommandListener {

	@LibertyCommand(command = "shop")
	public boolean shop(CommandParameters params) {
		CommandSender sender = params.sender;

		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("This command can only be performed by a player !");
			return true;
		}

		if(!sender.hasPermission("skyshop.command.shop")) {
			this.permissionMissing(sender);
			return true;
		}
		Player player = (Player) sender;
		SkyShop.getShop().openInventory(player);
		return true;
	}

	@LibertyCommand(command = "shop.reload")
	public boolean reload(CommandParameters params) {
		CommandSender sender = params.sender;

		if(!sender.hasPermission("skyshop.command.reload")) {
			this.permissionMissing(sender);
			return true;
		}
		SkyShop.getMainConfig().reload();
		try {
			SkyShop.getShop().reload();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.WARNING, e.toString());
			sender.sendMessage(ChatColor.RED + "[SkyShop] Reload performed with error: " + e);
			return true;
		}
		sender.sendMessage(ChatColor.DARK_GREEN + "[SkyShop] " + ChatColor.WHITE + "Plugin reloaded successfully !");
		return true;
	}
	
	public void permissionMissing(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to perform this command"));
	}

}
