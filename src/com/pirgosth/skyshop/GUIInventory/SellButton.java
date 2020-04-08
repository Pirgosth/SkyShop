package com.pirgosth.skyshop.GUIInventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.pirgosth.skyshop.Load;
import com.pirgosth.skyshop.Utility;

public class SellButton extends Button {
	TradeMenu parent;

	public SellButton(String name, Material type, int x, int y, List<String> lore, TradeMenu parent) {
		super(name, type, x, y, lore, 1, parent);
		this.parent = parent;
	}
	
	@Override
	public void onClick(InventoryClickEvent event) {
		int amount = parent.amount();
		double price = parent.calculatePrice();
		String name = parent.item().name();
		Material type = parent.item().type();
		Player player = (Player) event.getWhoClicked();
		int playerAmount = Utility.countInInventory(new ItemStack(type, amount), player.getInventory());
		if(playerAmount < amount){
			player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You need &4x" + (amount-playerAmount) + " &r" + name + " &7to perform this."));
			return;
		}
		else {
			player.getInventory().removeItem(new ItemStack(type, amount));
			Load.economy.depositPlayer(player, price);
			player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You sold &2x" + amount + " " + name + " &7for &2" + price + "$"));
		}
	}
}
