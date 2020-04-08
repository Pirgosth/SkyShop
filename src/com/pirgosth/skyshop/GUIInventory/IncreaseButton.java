package com.pirgosth.skyshop.GUIInventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class IncreaseButton extends Button{
	private TradeMenu parent;
	private int amount;
	public IncreaseButton(Material type, int x, int y, List<String> lore, TradeMenu parent, int amount) {
		super("Increase by " + amount, type, x, y, lore, amount, parent);
		this.parent = parent;
		this.amount = amount;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(parent.canInteract((Player)event.getWhoClicked())) {
			parent.add(amount);
		}
	}
}
