package io.github.pirgosth.skyshop.GUIInventory;

import java.util.List;

import io.github.pirgosth.liberty.core.api.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.github.pirgosth.skyshop.SkyShop;
import io.github.pirgosth.skyshop.Utility;
import net.milkbowl.vault.economy.EconomyResponse;

public class BuyButton extends Button {
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
		if (!Utility.canFitInInventory(new ItemStack(type, amount), player.getInventory())) {
			ChatUtils.sendColorMessage(player,"&2[SkyShop] &7You don't have enough free space in your inventory to perform this.");
			return;
		}
		if (SkyShop.getEconomy().withdrawPlayer(player, price).type == EconomyResponse.ResponseType.SUCCESS) {
			ChatUtils.sendColorMessage(player, String.format("&2[SkyShop] &7You purchase &2x%s %s &7for &2%s$", amount, name, price));
			player.getInventory().addItem(Utility.getLegalItemStack(new ItemStack(type, amount)).toArray(new ItemStack[0]));
		} else {
			player.sendMessage(Utility.colorTranslate(
					"&2[SkyShop] &7You need &4" + (price - SkyShop.getEconomy().getBalance(player)) + "$ &7to buy this."));
		}
	}
}
