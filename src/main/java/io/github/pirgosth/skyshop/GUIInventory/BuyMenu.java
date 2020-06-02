package io.github.pirgosth.skyshop.GUIInventory;

import java.util.Arrays;

import org.bukkit.Material;

public class BuyMenu extends TradeMenu {

	public BuyMenu(TradeButton item) {
		super(item, "Buy&4");
		addButton(new BuyButton("Confirm", Material.LIME_STAINED_GLASS, 4, 4, null, this));
	}

	@Override
	public double calculatePrice() {
		return item.buy() * amount;
	}

	@Override
	public void refresh() {
		addButton(new Button(item.name(), item.type(), 4, 2, Arrays.asList("&7Buy: &4" + calculatePrice() + "&7$"),
				amount, this));
		super.refresh();
	}
}
