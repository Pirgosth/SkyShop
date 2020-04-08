package com.pirgosth.skyshop.GUIInventory;

import java.util.Arrays;

import org.bukkit.Material;

public class SellMenu extends TradeMenu{
	public SellMenu(TradeButton item) {
		super(item, "Sell&2");
		addButton(new SellButton("Confirm", Material.LIME_STAINED_GLASS, 4, 4, null, this));
	}
	
	@Override
	public double calculatePrice() {
		return item.sell()*amount;
	}
	
	@Override
	public void refresh(){
		addButton(new Button(item.name(), item.type(), 4, 2, Arrays.asList("&7Sell: &2" + calculatePrice() +"&7$"), amount, this));
		super.refresh();
	}
}
