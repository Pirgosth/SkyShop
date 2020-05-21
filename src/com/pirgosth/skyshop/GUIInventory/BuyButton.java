package com.pirgosth.skyshop.GUIInventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.pirgosth.skyshop.Load;
import com.pirgosth.skyshop.Utility;

import net.milkbowl.vault.economy.EconomyResponse;

public class BuyButton extends Button{
	BuyMenu parent;

	public BuyButton(String name, Material type, int x, int y, List<String> lore, BuyMenu parent) {
		super(name, type, x, y, lore, 1, parent);
		this.parent = parent;
	}
	
	@Override
	public void onClick(InventoryClickEvent event) {
		int amount = parent.amount();
		double price = parent.calculatePrice();
		String name = parent.item().stack().getItemMeta().getDisplayName();
		Material type = parent.item().stack().getType();
		Player player = (Player) event.getWhoClicked();
		if(!Utility.canFitInInventory(new ItemStack(type, amount), player.getInventory())){
			player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You don't have enought free space in your inventory to perform this."));
			return;
		}
		if(Load.economy.withdrawPlayer(player, price).type == EconomyResponse.ResponseType.SUCCESS) {
			player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You purchase &2x" + amount + " " + name + " &7for &2" + price + "$"));
			player.getInventory().addItem(new ItemStack(type, amount));
		}
		else {
			player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You need &4" + (price-Load.economy.getBalance(player)) + "$ &7to buy this."));
		}
	}
}
