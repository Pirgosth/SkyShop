package io.github.pirgosth.skyshop;

import io.github.pirgosth.skyshop.GUIInventory.ChangeInvButton;
import io.github.pirgosth.skyshop.GUIInventory.Menu;
import io.github.pirgosth.skyshop.GUIInventory.TradeButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Config implements IConfig {
	private FileConfiguration config;

	@Override
	public void load() {
		SkyShop.getInstance().saveDefaultConfig();
		config = SkyShop.getInstance().getConfig();
		cleanConfig();
		save();
	}

	@Override
	public void reload() {
		SkyShop.getInstance().saveDefaultConfig();
		SkyShop.getInstance().reloadConfig();
		config = SkyShop.getInstance().getConfig();
		// cleanConfig();
		save();
	}

	@Override
	public void save() {
		SkyShop.getInstance().saveConfig();
	}

	@Override
	public void cleanConfig() {
		ArrayList<String> worlds = getActiveWorlds();
		ArrayList<String> common = new ArrayList<>(worlds);
		common.retainAll(Utility.getWorldNames());
		config.set("enabled-worlds", common);
		if (common.size() != worlds.size()) {
			worlds.removeAll(common);
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.GOLD + "Old worlds: " + worlds + " have been removed from configuration.");
		}
	}

	public static boolean doesWorldExist(String world) {
		for (World w : Bukkit.getWorlds()) {
			if (w.getName().equalsIgnoreCase(world)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<String> getActiveWorlds() {
		List<String> worlds = config.getStringList("enabled-worlds");
		return new ArrayList<>(worlds);
	}

	@Override
	public boolean addActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if (!worlds.contains(world)) {
			worlds.add(world);
			config.set("enabled-worlds", worlds);

			return true;
		}
		return false;
	}

	@Override
	public boolean delActiveWorld(String world) {
		ArrayList<String> worlds = getActiveWorlds();
		if (worlds.contains(world)) {
			worlds.remove(world);
			config.set("enabled-worlds", worlds);
			return true;
		}
		return false;
	}

	public void addCategory(String name, String description, Material material, int x, int y) throws Exception {
		String catNode = String.format("shop.%s", name);

		if(config.getConfigurationSection(catNode) != null) {
			throw new Exception(String.format("Category name %s already exists.", name));
		}

		for(String cat : Objects.requireNonNull(config.getConfigurationSection("shop")).getKeys(false)) {
			int catX = config.getInt(String.format("shop.%s.x", cat), Integer.MAX_VALUE);
			int catY = config.getInt(String.format("shop.%s.y", cat), Integer.MAX_VALUE);
			if(catX == x && catY == y) {
				throw new Exception(String.format("Category %s already exists at (%s;%s).", cat, x, y));
			}
		}

		config.set(String.format("%s.name", catNode), name);
		config.set(String.format("%s.description", catNode), List.of(description));
		config.set(String.format("%s.material", catNode), material.toString());
		config.set(String.format("%s.x", catNode), x);
		config.set(String.format("%s.y", catNode), y);
		save();
		SkyShop.getShop().reload();
	}

	public ChangeInvButton getCategory(String cat, Menu parent) throws Exception {
		String node = "shop." + cat;
			String name = config.getString(node + ".name", null);
		if (name == null) {
			throw new Exception("Missing name node in: " + cat);
		}
		int x = config.getInt(node + ".x");
		if (x == Integer.MAX_VALUE) {
			throw new Exception("Missing x node in: " + cat);
		}
		int y = config.getInt(node + ".y", Integer.MAX_VALUE);
		if (y == Integer.MAX_VALUE) {
			throw new Exception("Missing y node in: " + cat);
		}
		String materialName = config.getString(node + ".material", null);
		if (materialName == null) {
			throw new Exception("Missing material node in: " + cat);
		}
		Material material = Utility.getMaterial(materialName);
		ArrayList<String> description = new ArrayList<>(config.getStringList(node + ".description"));
		Utility.colorTranslate(description);
		Menu menu = new Menu(Utility.colorTranslate(name), 6, parent.holder(), parent);
		ConfigurationSection itemsSection = config.getConfigurationSection("shop." + cat + ".items");
		ArrayList<String> items = itemsSection == null ? new ArrayList<>()
				: new ArrayList<>(itemsSection.getKeys(false));
		for (String item : items) {
			getItem(cat, item, menu);
		}
		menu.addButton(new ChangeInvButton("Back", Material.ACACIA_DOOR, 4, 5, null, menu, menu.parent()));
		return new ChangeInvButton(Utility.colorTranslate(name), material, x, y, description, menu.parent(), menu);
	}

	@Override
	public void removeCategory(int x, int y) throws Exception {
		for(String cat : Objects.requireNonNull(config.getConfigurationSection("shop")).getKeys(false)) {
			int catX = config.getInt(String.format("shop.%s.x", cat), Integer.MAX_VALUE);
			int catY = config.getInt(String.format("shop.%s.y", cat), Integer.MAX_VALUE);
			if(catX == x && catY == y) {
				config.set(String.format("shop.%s", cat), null);
				save();
				SkyShop.getShop().reload();
				return;
			}
		}

		throw new Exception(String.format("There's no category at (%s;%s).", x, y));
	}

	public void getItem(String cat, String item, Menu parent) throws Exception {
		String node = "shop." + cat + ".items." + item;
		String name = config.getString(node + ".name", null);
		if (name == null) {
			throw new Exception("Missing node name in category: " + cat + ", item: " + item);
		}
		int x = config.getInt(node + ".x", Integer.MAX_VALUE);
		if (x == Integer.MAX_VALUE) {
			throw new Exception("Missing node x in category: " + cat + ", item: " + item);
		}
		int y = config.getInt(node + ".y", Integer.MAX_VALUE);
		if (y == Integer.MAX_VALUE) {
			throw new Exception("Missing node y in category: " + cat + ", item: " + item);
		}
		String materialName = config.getString(node + ".material", null);
		if (materialName == null) {
			throw new Exception("Missing node material in category: " + cat + ", item: " + item);
		}
		Material material = Utility.getMaterial(materialName);
		double buy = config.getDouble(node + ".buy", Double.POSITIVE_INFINITY);
		if (buy == Double.POSITIVE_INFINITY) {
			throw new Exception("Missing node buy in category: " + cat + ", item: " + item);
		}
		double sell = config.getDouble(node + ".sell", Double.POSITIVE_INFINITY);
		if (sell == Double.POSITIVE_INFINITY) {
			throw new Exception("Missing node sell in category: " + cat + ", item: " + item);
		}
		parent.addButton(new TradeButton(Utility.colorTranslate(name), material, x, y, buy, sell, parent));
	}

	public Menu loadShop() throws Exception {
		Menu shop = new Menu("Shop", 3, SkyShop.getShop());
		ConfigurationSection catSection = config.getConfigurationSection("shop");
		if (catSection == null) {
			throw new Exception("Missing node shop in config.yml !");
		}
		for (String cat : catSection.getKeys(false)) {
			ChangeInvButton tmp = getCategory(cat, shop);
			shop.addButton(tmp);
		}
		return shop;
	}
}
