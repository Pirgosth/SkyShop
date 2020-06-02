package io.github.pirgosth.skyshop.GUIInventory;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class TradeMenu extends Menu {
	protected int amount;
	protected TradeButton item;
	private HashMap<Player, Long> times;

	public TradeMenu(TradeButton item, String prefix) {
		super(prefix + " " + ChatColor.stripColor(item.name()), 6, item.parent().holder(), item.parent());
		this.item = item;
		amount = 1;
		times = new HashMap<Player, Long>();
		refresh();
		addButton(new ChangeInvButton("Back", Material.ACACIA_DOOR, 4, 5, null, this, item.parent()));
	}

	public abstract double calculatePrice();

	public void refresh() {
//		addButton(new Button(item.stack().getItemMeta().getDisplayName(), item.stack().getType(), 4, 2, Arrays.asList("Price: " + calculatePrice()), amount, this));
		if (amount < 64) {
			addButton(new IncreaseButton(Material.LIME_STAINED_GLASS_PANE, 6, 2, null, this, 1));
		} else {
			removeButton(6, 2);
		}
		if (amount < 55) {
			addButton(new IncreaseButton(Material.LIME_STAINED_GLASS_PANE, 7, 2, null, this, 10));
		} else {
			removeButton(7, 2);
		}
		if (amount > 1) {
			addButton(new DecreaseButton(Material.RED_STAINED_GLASS_PANE, 2, 2, null, this, 1));
		} else {
			removeButton(2, 2);
		}
		if (amount > 10) {
			addButton(new DecreaseButton(Material.RED_STAINED_GLASS_PANE, 1, 2, null, this, 10));
		} else {
			removeButton(1, 2);
		}
		if (amount != 1) {
			addButton(new SetButton(Material.RED_STAINED_GLASS_PANE, 0, 2, null, this, 1));
		} else {
			removeButton(0, 2);
		}
		if (amount != 64) {
			addButton(new SetButton(Material.LIME_STAINED_GLASS_PANE, 8, 2, null, this, 64));
		} else {
			removeButton(8, 2);
		}
	}

	public void add(int amount) {
		this.amount = this.amount + amount > 64 ? 64 : this.amount + amount;
		refresh();
	}

	public void reduce(int amount) {
		this.amount = this.amount - amount < 1 ? 1 : this.amount - amount;
		refresh();
	}

	public void set(int amount) {
		this.amount = amount < 1 ? 1 : amount;
		refresh();
	}

	public int amount() {
		return amount;
	}

	public boolean canInteract(Player player) {
		if (times.get(player) == null) {
			times.put(player, System.currentTimeMillis());
			return true;
		}
		long lastTime = times.get(player);
		if (System.currentTimeMillis() - lastTime > 250) {
			times.put(player, System.currentTimeMillis());
			return true;
		}
		return false;
	}

	public TradeButton item() {
		return item;
	}
}
