package io.github.pirgosth.skyshop.GUIInventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChangeInvButton extends Button {
	protected Menu next;

	public ChangeInvButton(String name, Material type, int x, int y, List<String> lore, Menu parent, Menu next) {
		super(name, type, x, y, lore, 1, parent);
		this.next = next;
	}

	public Menu getMenu() {
		return next;
	}

	public void setMenu(Menu menu) {
		next = menu;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (next != null) {
			next.open(player);
		}
	}
}
