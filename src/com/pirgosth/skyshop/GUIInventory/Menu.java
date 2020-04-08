package com.pirgosth.skyshop.GUIInventory;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.pirgosth.skyshop.Utility;

public class Menu {
	protected Menu parent;
	protected Inventory inv = null;
	protected HashMap<Integer, Button> buttons;
	protected InventoryHolder holder;
	
	public Menu(String name, int y, InventoryHolder holder, Menu parent) {
		inv = Bukkit.createInventory(holder, 9*y, Utility.colorTranslate(name));
		buttons = new HashMap<>();
		this.holder = holder;
		this.parent = parent == null ? this : parent;
	}
	
	public Menu(String name, int y, InventoryHolder holder) {
		this(name, y, holder, null);
	}
	
	public void addButton(Button button) {
		buttons.put(button.index(), button);
		inv.setItem(button.index(), button.stack());
	}
	
	public void removeButton(int x, int y) {
		int index = -1;
		try {
			index = Utility.getIndex(x, y);
		}
		catch(Exception e) {
			Bukkit.getLogger().log(Level.WARNING, e.getStackTrace().toString());
			return;
		}
		buttons.put(index, null);
		inv.setItem(index, null);
	}
	
	public Button getButton(int index) {
		return buttons.get(index);
	}
	
	public void open(HumanEntity entity) {
		entity.openInventory(inv);
	}
	
	public Menu parent() {
		return parent;
	}
	
	public InventoryHolder holder() {
		return holder;
	}
	
	public Inventory inventory() {
		return inv;
	}
}
