package com.pirgosth.skyshop.GUIInventory;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pirgosth.skyshop.Load;
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
		if(buy != -1) lore.add("&7Buy: &2" + buy + "&7$");
		if(sell != -1) lore.add("&7Sell: &2" + sell + "&7$");
		lore.add("");
		if(buy != -1) lore.add("&7&lLeft clic to buy");
		if(sell != -1) {
			lore.add("&7&lMiddle clic to sell all");
			lore.add("&7&lRight clic to sell");
		}
		Utility.colorTranslate(lore);
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(lore);
		stack.setItemMeta(meta);
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		switch(event.getClick()) {
			case LEFT:
				this.next = this.buy != -1 ? new BuyMenu(this) : null;
				break;
			case RIGHT:
				this.next = this.sell != -1 ? new SellMenu(this) : null;
				break;
			case MIDDLE:
				this.next = null;
				sellEverything(event);
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
	
	public void sellEverything(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		int amount = Utility.countInInventory(stack, player.getInventory());
		if(amount == 0) {
			player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You have no &4" + name() + " &7to sell."));
			return;
		}
		double price = sell*amount;
		player.getInventory().removeItem(new ItemStack(type(), amount));
		Load.economy.depositPlayer(player, price);
		player.sendMessage(Utility.colorTranslate("&2[SkyShop] &7You sold &2x" + amount + " " + name() + " &7for &2" + price + "$"));
	}
}
