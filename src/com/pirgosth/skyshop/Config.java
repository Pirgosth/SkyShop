package com.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.pirgosth.skyshop.GUIInventory.ChangeInvButton;
import com.pirgosth.skyshop.GUIInventory.Menu;
import com.pirgosth.skyshop.GUIInventory.TradeButton;

public class Config {
	private JavaPlugin plugin;
	private FileConfiguration config;
	
	public Config(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void load() {
		plugin.saveDefaultConfig();
		config = plugin.getConfig();
		cleanConfig();
		save();
	}
	
	public void reload(){
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		config = plugin.getConfig();
		//cleanConfig();
		save();
	}
	
	public void save() {
		plugin.saveConfig();
	}

	public void cleanConfig() {
		ArrayList<String> worlds = getActiveWorlds();
		ArrayList<String> common = new ArrayList<String>(worlds);
		common.retainAll(Utility.getWorldNames());
		config.set("enabled-worlds", common);
		if(common.size() != worlds.size()) {
			worlds.removeAll(common);
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Old worlds: " + worlds.toString() + " have been removed from configuration.");
		}
	}
	
	public static boolean doesWorldExist(String world) {
		for(World w : Bukkit.getWorlds()) {
			if(w.getName().equalsIgnoreCase(world)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getActiveWorlds(){
		List<String> worlds = config.getStringList("enabled-worlds");
		if(worlds == null) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(worlds);
	}
	
	public boolean addActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if(!worlds.contains(world)) {
			worlds.add(world);
			config.set("enabled-worlds", worlds);
			
			return true;
		}
		return false;
	}
	
	public boolean delActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if(worlds.contains(world)) {
			worlds.remove(world);
			config.set("enabled-worlds", worlds);
			return true;
		}
		return false;
	}
	
	public ChangeInvButton getCategory(String cat, Menu parent) throws Exception{
		String node = "shop." + cat;
		String name = config.getString(node + ".name", null);
		if(name == null) {
			throw new Exception("Missing name node in: " + cat);
		}
		int x = config.getInt(node + ".x");
		if(x == Integer.MAX_VALUE) {
			throw new Exception("Missing x node in: " + cat);
		}
		int y = config.getInt(node + ".y", Integer.MAX_VALUE);
		if(y == Integer.MAX_VALUE) {
			throw new Exception("Missing y node in: " + cat);
		}
		String materialName = config.getString(node + ".material", null);
		if(materialName == null) {
			throw new Exception("Missing material node in: " + cat);
		}
		Material material = null;
		try {
			material = Utility.getMaterial(materialName);
		}
		catch(Exception e) {
			throw e;
		}
		ArrayList<String> description = new ArrayList<String>(config.getStringList(node + ".description"));
		Utility.colorTranslate(description);
		Menu menu = new Menu(Utility.colorTranslate(name), 6, parent.holder(), parent);
		ConfigurationSection itemsSection = config.getConfigurationSection("shop." + cat + ".items");
		ArrayList<String> items = itemsSection == null ? new ArrayList<String>() : 
			new ArrayList<String>(itemsSection.getKeys(false));
		for(String item: items) {
			try {
				getItem(cat, item, menu);
			}
			catch(Exception e) {
				throw e;
			}
		}
		menu.addButton(new ChangeInvButton("Back", Material.ACACIA_DOOR, 4, 5, null, menu, menu.parent()));
		return new ChangeInvButton(Utility.colorTranslate(name), material, x, y, description, menu.parent(), menu);
	}
	
	public void getItem(String cat, String item, Menu parent) throws Exception{
		String node = "shop." + cat + ".items." + item;
		String name = config.getString(node + ".name", null);
		if(name == null) {
			throw new Exception("Missing node name in category: " + cat + ", item: " + item);
		}
		int x = config.getInt(node + ".x", Integer.MAX_VALUE);
		if(x == Integer.MAX_VALUE) {
			throw new Exception("Missing node x in category: " + cat + ", item: " + item);
		}
		int y = config.getInt(node + ".y", Integer.MAX_VALUE);
		if(y == Integer.MAX_VALUE) {
			throw new Exception("Missing node y in category: " + cat + ", item: " + item);
		}
		String materialName = config.getString(node + ".material", null);
		if(materialName == null) {
			throw new Exception("Missing node material in category: " + cat + ", item: " + item);
		}
		Material material = null;
		try {
			material = Utility.getMaterial(materialName);
		}
		catch(Exception e) {
			throw e;
		}
		double buy = config.getDouble(node + ".buy", Double.POSITIVE_INFINITY);
		if(buy == Double.POSITIVE_INFINITY) {
			throw new Exception("Missing node buy in category: " + cat + ", item: " + item);
		}
		double sell = config.getDouble(node + ".sell", Double.POSITIVE_INFINITY);
		if(sell == Double.POSITIVE_INFINITY) {
			throw new Exception("Missing node sell in category: " + cat + ", item: " + item);
		}
		parent.addButton(new TradeButton(Utility.colorTranslate(name), material, x, y, buy, sell, parent));
	}
	
	public Menu getShop() throws Exception{
		Menu shop = new Menu("Shop", 3, Load.shop);
		ConfigurationSection catSection = config.getConfigurationSection("shop");
		if(catSection == null) {
			throw new Exception("Missing node shop in config.yml !");
		}
		for(String cat: catSection.getKeys(false)) {
			ChangeInvButton tmp = getCategory(cat, shop);
			shop.addButton(tmp);
		}
		return shop;
	}
}
