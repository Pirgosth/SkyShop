package com.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
	
	public ShopCategory getCategory(String cat) throws Exception{
		String node = "shop." + cat;
		String name = config.getString(node + ".name", null);
		if(name == null) {
			throw new Exception("Missing name node in: " + cat);
		}
		int x = config.getInt(node + ".x", Integer.MAX_VALUE);
		if(x == Integer.MAX_VALUE) {
			throw new Exception("Missing x node in: " + cat);
		}
		int y = config.getInt(node + ".y", Integer.MAX_VALUE);
		if(y == Integer.MAX_VALUE) {
			throw new Exception("Missing y node in: " + cat);
		}
		String material = config.getString(node + ".material", null);
		if(material == null) {
			throw new Exception("Missing material node in: " + cat);
		}
		ArrayList<String> description = new ArrayList<String>(config.getStringList(node + ".description"));
		Utility.colorTranslate(description);
		return new ShopCategory(Utility.colorTranslate(name), x, y, material, description);
	}
	
	public ShopItem getItem(String cat, String item) throws Exception{
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
		String material = config.getString(node + ".material", null);
		if(material == null) {
			throw new Exception("Missing node material in category: " + cat + ", item: " + item);
		}
		ArrayList<String> description = new ArrayList<String>(config.getStringList(node + ".description"));
		Utility.colorTranslate(description);
		return new ShopItem(Utility.colorTranslate(name), x, y, material, description);
	}
	
	public ArrayList<ShopCategory> getCategories() throws Exception{
		ArrayList<ShopCategory> categories = new ArrayList<ShopCategory>();
		ConfigurationSection catSection = config.getConfigurationSection("shop");
		if(catSection == null) {
			throw new Exception("Missing node shop in config.yml !");
		}
		for(String cat: catSection.getKeys(false)) {
			ConfigurationSection itemsSection = config.getConfigurationSection("shop." + cat + ".items");
			ArrayList<String> items = itemsSection == null ? new ArrayList<String>() : 
				new ArrayList<String>(itemsSection.getKeys(false));
			ShopCategory tmp = getCategory(cat);
			for(String item: items) {
				try {
					tmp.addItem(getItem(cat, item));
				}
				catch(Exception e) {
					throw e;
				}
			}
			categories.add(tmp);
		}
		return categories;
	}
}
