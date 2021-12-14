package io.github.pirgosth.skyshop;

import io.github.pirgosth.liberty.core.LibertyCore;
import io.github.pirgosth.liberty.core.api.LibertyCoreAPI;
import io.github.pirgosth.skyshop.commands.AdminCommands;
import io.github.pirgosth.skyshop.commands.Commands;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class SkyShop extends JavaPlugin {
	@Getter
	private static JavaPlugin instance = null;
	@Getter
	private static IConfig mainConfig = null;
	@Getter
	private static Shop shop = null;
	@Getter
	private static Economy economy = null;

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return true;
	}

	@Override
	public void onEnable() {
		instance = this;
		mainConfig = new Config();
		shop = new Shop();
		Bukkit.getConsoleSender().sendMessage("Plugin is loading ...");
		if (!setupEconomy()) {
			Bukkit.getLogger().log(Level.SEVERE,
					String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		mainConfig.load();
		try {
			shop.load();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.WARNING, e.toString());
			e.printStackTrace();
		}

		LibertyCoreAPI coreAPI = LibertyCore.getInstance();
		coreAPI.getCommandRegister().register(this, new Commands());
		coreAPI.getCommandRegister().register(this, new AdminCommands());
//		getCommand("shop").setTabCompleter(new AutoCompletion());
		getServer().getPluginManager().registerEvents(new EventListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Plugin is shutting down ...");
		mainConfig.save();
	}
}
