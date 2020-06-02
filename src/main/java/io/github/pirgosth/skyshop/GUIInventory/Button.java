package io.github.pirgosth.skyshop.GUIInventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.pirgosth.skyshop.Utility;

public class Button {
	private int index;
	protected ItemStack stack;
	protected Menu parent;

	public Button(String name, Material type, int x, int y, List<String> lore, int amount, Menu parent) {
		Utility.colorTranslate(lore);
		stack = new ItemStack(type);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Utility.colorTranslate(name));
		meta.setLore(lore);
		stack.setItemMeta(meta);
		stack.setAmount(amount);
		getIndex(x, y);
		this.parent = parent;
	}

	private void getIndex(int x, int y) {
		try {
			index = Utility.getIndex(x, y);
		} catch (Exception e) {
			index = 0;
			// Add error in reload Logger (TODO, Invalid position)
		}
	}

	public int index() {
		return index;
	}

	public String name() {
		return stack.getItemMeta().getDisplayName();
	}

	public Material type() {
		return stack.getType();
	}

	public ItemStack stack() {
		return stack;
	}

	public Menu parent() {
		return parent;
	}

	public void onClick(InventoryClickEvent event) {
	}
}
