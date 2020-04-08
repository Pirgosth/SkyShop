package com.pirgosth.skyshop.GUIInventory;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import com.pirgosth.skyshop.Utility;

public class TradeButton extends ChangeInvButton{
	private double buy;
	private double sell;

	public TradeButton(String name, Material type, int x, int y, double buy, double sell, Menu parent) {
		super(name, type, x, y, null, parent, null);
		this.buy = buy;
		this.sell = sell;
		loadLore();
	}
	
	private void loadLore() {
		ArrayList<String> lore = new ArrayList<String>();
		if(buy != 0) lore.add("&7Buy: &2" + buy + "&7$");
		if(sell != 0) lore.add("&7Sell: &2" + sell + "&7$");
		lore.add("");
		if(buy != 0) lore.add("&7&lLeft clic to buy");
		if(sell != 0) lore.add("&7&lRight clic to sell");
		Utility.colorTranslate(lore);
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(lore);
		stack.setItemMeta(meta);
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		switch(event.getClick()) {
			case LEFT:
				next = buy != 0 ? new BuyMenu(this) : null;
				break;
			case RIGHT:
				next = sell != 0 ? new SellMenu(this) : null;
				break;
			case MIDDLE:
				break;
			default:
				break;
		}
		super.onClick(event);
	}

	public double buy() {
		return buy;
	}
	
	public double sell() {
		return sell;
	}
	
}
