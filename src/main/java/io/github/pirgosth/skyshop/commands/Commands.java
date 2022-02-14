package io.github.pirgosth.skyshop.commands;

import io.github.pirgosth.liberty.core.api.commands.CommandExecutor;
import io.github.pirgosth.liberty.core.api.commands.ICommandListener;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommand;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandExecutor;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandPermission;
import io.github.pirgosth.liberty.core.commands.CommandParameters;
import io.github.pirgosth.skyshop.SkyShop;
import io.github.pirgosth.skyshop.models.SkyConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Commands implements ICommandListener {

	@LibertyCommand(command = "shop")
	@LibertyCommandPermission(permission = "skyshop.command.shop")
	@LibertyCommandExecutor(executor = CommandExecutor.ENTITY)
	public boolean shop(CommandParameters params) {
		Player player = (Player) params.sender;
		if(!SkyConfig.getConfiguration().isWorldEnabled(player.getWorld().getName())) return true;
		SkyShop.getShop().openInventory(player);
		return true;
	}

	@LibertyCommand(command = "shop.reload")
	@LibertyCommandPermission(permission = "skyshop.command.reload")
	public boolean reload(CommandParameters params) {
		CommandSender sender = params.sender;
		SkyShop.getMainConfig().reload();
		try {
			SkyConfig.reloadConfiguration();
			SkyShop.getShop().reload();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.WARNING, e.toString());
			sender.sendMessage(ChatColor.RED + "[SkyShop] Reload performed with error: " + e);
			return true;
		}
		sender.sendMessage(ChatColor.DARK_GREEN + "[SkyShop] " + ChatColor.WHITE + "Plugin reloaded successfully !");
		return true;
	}
}
