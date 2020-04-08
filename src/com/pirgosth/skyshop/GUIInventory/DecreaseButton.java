package com.pirgosth.skyshop.GUIInventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DecreaseButton extends Button{
	private TradeMenu parent;
	private int amount;
	public DecreaseButton(Material type, int x, int y, List<String> lore, TradeMenu parent, int amount) {
		super("Decrease by " + amount, type, x, y, lore, amount, parent);
		this.parent = parent;
		this.amount = amount;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(parent.canInteract((Player)event.getWhoClicked())) {
			parent.reduce(amount);
		}
	}
}
