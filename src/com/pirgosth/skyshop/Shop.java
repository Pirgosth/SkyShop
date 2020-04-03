package com.pirgosth.skyshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shop implements InventoryHolder{
	private ArrayList<Inventory>inv;
	private Inventory mainInv;
	
	public Shop() throws Exception{
		try {
			reload();
		}
		catch(Exception e) {
			loadDefautShop();
			throw e;
		}
	}

	public void reload() throws Exception{
		inv = new ArrayList<Inventory>();
		inv.add(Bukkit.createInventory(this, 9*6, "Shop"));
		mainInv = inv.get(0);
		try {
			for(ShopCategory cat: Load.config.getCategories()) {
				Bukkit.getConsoleSender().sendMessage("Loading category: " + cat.name() + " ...");
				mainInv.setItem(cat.index(), createGUIItem(cat, 1));
				Inventory tmp = Bukkit.createInventory(this, 9*6, cat.name());
				inv.add(tmp);
			}
		}
		catch(Exception e) {
			Bukkit.getLogger().log(Level.WARNING, "Loading Default Config ...");
			loadDefautShop();
			throw e;
		}
	}
	
	private void loadDefautShop() {
		inv = new ArrayList<Inventory>(Arrays.asList(Bukkit.createInventory(this, 3*9, "Default")));
		mainInv = inv.get(0);
	}
	
	private ItemStack createGUIItem(String name, Material material, int amount, ArrayList<String> description) {
		ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(description);
        itemStack.setItemMeta(meta);
        return itemStack;
	}
	
	private ItemStack createGUIItem(ShopCategory cat, int amount) {
		return createGUIItem(cat.name(), cat.material(), amount, cat.description());
	}
	
	private ItemStack createGUIItem(ShopItem item, int amount) {
        return createGUIItem(item.name(), item.material(), amount, item.description());
    }
	
	@Override
	public Inventory getInventory() {
		return mainInv;
	}
	
	public void openInventory(Player player) {
		if(mainInv == null) {
			Bukkit.getLogger().log(Level.SEVERE, "MainInv is null !");
		}
		player.openInventory(mainInv);
	}
	
	public void buy(Player player, int slot) {
		
	}
}
