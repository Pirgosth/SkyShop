package io.github.pirgosth.skyshop;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;

public class SkyShop extends JavaPlugin {
	@Getter
	private static JavaPlugin instance = null;
	@Getter
	private static Config mainConfig = null;
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
		return (economy != null);
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
		}
		getCommand("shop").setExecutor(new Commands());
		getCommand("shop").setTabCompleter(new AutoCompletion());
		getServer().getPluginManager().registerEvents(new EventListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Plugin is shutting down ...");
		mainConfig.save();
	}
}
